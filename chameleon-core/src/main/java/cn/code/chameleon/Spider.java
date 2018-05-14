package cn.code.chameleon;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.downloader.Downloader;
import cn.code.chameleon.downloader.HttpClientDownloader;
import cn.code.chameleon.monitor.SpiderListener;
import cn.code.chameleon.pipeline.CollectorPipeline;
import cn.code.chameleon.pipeline.ConsolePipeline;
import cn.code.chameleon.pipeline.Pipeline;
import cn.code.chameleon.pipeline.ResultsPipeline;
import cn.code.chameleon.processor.PageProcessor;
import cn.code.chameleon.robots.RobotsServer;
import cn.code.chameleon.scheduler.QueueScheduler;
import cn.code.chameleon.scheduler.Scheduler;
import cn.code.chameleon.thread.CountableThreadPool;
import cn.code.chameleon.utils.UrlUtils;
import cn.code.chameleon.utils.WMCollectionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liumingyu
 * @create 2018-04-04 上午10:12
 */
public class Spider implements Runnable, Task {

    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    protected Downloader downloader;

    protected List<Pipeline> pipelines = new ArrayList<>();

    protected List<Request> startRequests;

    protected PageProcessor pageProcessor;

    protected Site site;

    protected String uuid;

    protected Scheduler scheduler = new QueueScheduler();

    protected CountableThreadPool threadPool;

    protected ExecutorService executorService;

    protected int threadNum = 5;

    protected AtomicInteger stat = new AtomicInteger(STAT_INIT);

    protected boolean exitWhenComplete = true;

    protected final static int STAT_INIT = 0;

    protected final static int STAT_RUNNING = 1;

    protected final static int STAT_STOPPED = 2;

    protected boolean spawnUrl = true;

    protected boolean destroyWhenExit = true;

    protected boolean ignoreUA = true;

    private ReentrantLock newUrlLock = new ReentrantLock();

    private Condition newUrlCondition = newUrlLock.newCondition();

    private final AtomicLong pageCount = new AtomicLong(0);

    private Date startTime;

    private int emptySleepTime = 30000;

    private List<SpiderListener> spiderListeners;

    private RobotsServer robotsServer = new RobotsServer();

    public Spider(PageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
        this.site = pageProcessor.getSite();
    }

    public static Spider create(PageProcessor pageProcessor) {
        return new Spider(pageProcessor);
    }

    protected void initComponent() {
        if (downloader == null) {
            downloader = new HttpClientDownloader();
        }
        downloader.setThread(threadNum);
        if (pipelines.isEmpty()) {
            pipelines.add(new ConsolePipeline());
        }
        if (threadPool == null || threadPool.isShutdown()) {
            if (executorService != null && !executorService.isShutdown()) {
                threadPool = new CountableThreadPool(threadNum, executorService);
            } else {
                threadPool = new CountableThreadPool(threadNum);
            }
        }
        if (startRequests != null) {
            for (Request request : startRequests) {
                addRequest(request);
            }
            startRequests.clear();
        }
        startTime = new Date();
    }

    public Spider startUrls(List<String> startUrls) {
        checkIfRunning();
        this.startRequests = UrlUtils.convertToRequests(startUrls);
        return this;
    }

    public Spider addUrls(String... urls) {
        for (String url : urls) {
            addRequest(new Request(url));
        }
        signalNewUrl();
        return this;
    }

    public Spider startRequests(List<Request> requests) {
        this.startRequests = requests;
        return this;
    }

    public Spider setUUID(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public Spider setScheduler(Scheduler scheduler) {
        checkIfRunning();
        Scheduler oldScheduler = this.scheduler;
        this.scheduler = scheduler;
        if (oldScheduler != null) {
            Request request;
            while ((request = oldScheduler.poll(this)) != null) {
                this.scheduler.push(request, this);
            }
        }
        return this;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public Spider pipeline(Pipeline pipeline) {
        return addPipeline(pipeline);
    }

    public Spider addPipeline(Pipeline pipeline) {
        checkIfRunning();
        this.pipelines.add(pipeline);
        return this;
    }

    public Spider setPipelines(List<Pipeline> pipelines) {
        checkIfRunning();
        this.pipelines = pipelines;
        return this;
    }

    public Spider clearPipeline() {
        this.pipelines = new ArrayList<>();
        return this;
    }

    public List<SpiderListener> getSpiderListeners() {
        return spiderListeners;
    }

    public Spider setSpiderListeners(List<SpiderListener> spiderListeners) {
        this.spiderListeners = spiderListeners;
        return this;
    }

    public Spider addSpiderListener(SpiderListener spiderListener) {
        if (this.spiderListeners == null) {
            spiderListeners = new ArrayList<>();
        }
        this.spiderListeners.add(spiderListener);
        return this;
    }

    public Spider download(Downloader downloader) {
        return setDownload(downloader);
    }

    public Spider setDownload(Downloader downloader) {
        checkIfRunning();
        this.downloader = downloader;
        return this;
    }

    public Spider thread(int threadNum) {
        checkIfRunning();
        this.threadNum = threadNum;
        if (threadNum < 0) {
            throw new IllegalArgumentException("The number of threads can not be negative");
        }
        return this;
    }

    public Spider thread(ExecutorService executorService, int threadNum) {
        checkIfRunning();
        this.threadNum = threadNum;
        if (threadNum < 0) {

            throw new IllegalArgumentException("The number of threads can not be negative");
        }
        this.executorService = executorService;
        return this;
    }

    public boolean isExitWhenComplete() {
        return exitWhenComplete;
    }

    public Spider setExitWhenComplete(boolean exitWhenComplete) {
        this.exitWhenComplete = exitWhenComplete;
        return this;
    }

    public boolean isSpawnUrl() {
        return spawnUrl;
    }

    public Spider setSpawnUrl(boolean spawnUrl) {
        this.spawnUrl = spawnUrl;
        return this;
    }

    public boolean isIgnoreUA() {
        return ignoreUA;
    }

    public Spider setIgnoreUA(boolean ignoreUA) {
        this.ignoreUA = ignoreUA;
        robotsServer.setIgnoreUA(ignoreUA);
        return this;
    }

    public boolean isDestroyWhenExit() {
        return destroyWhenExit;
    }

    public Spider setDestroyWhenExit(boolean destroyWhenExit) {
        this.destroyWhenExit = destroyWhenExit;
        return this;
    }

    public Spider setEmptySleepTime(int emptySleepTime) {
        this.emptySleepTime = emptySleepTime;
        return this;
    }

    public long getPageCount() {
        return pageCount.get();
    }

    public int getThreadAlive() {
        if (threadPool == null) {
            return 0;
        }
        return threadPool.getThreadAlive();
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public Status getStatus() {
        return Status.getStatusByValue(stat.get());
    }

    protected CollectorPipeline getCollectionPipeline() {
        return new ResultsPipeline();
    }

    public <T> List<T> getAll(Collection<String> urls) {
        destroyWhenExit = false;
        spawnUrl = false;
        if (startRequests != null) {
            startRequests.clear();
        }
        for (Request request : UrlUtils.convertToRequests(urls)) {
            addRequest(request);
        }
        CollectorPipeline collectorPipeline = getCollectionPipeline();
        pipelines.add(collectorPipeline);
        run();
        spawnUrl = true;
        destroyWhenExit = true;
        return collectorPipeline.getCollected();
    }

    public <T> T get(String url) {
        List<String> urls = WMCollectionUtils.newArrayList(url);
        List<T> results = getAll(urls);
        if (results != null && !results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }

    protected void checkIfRunning() {
        if (stat.get() == STAT_RUNNING) {
            throw new IllegalStateException("Spider is already running!");
        }
    }

    private void checkRunningStat() {
        while (true) {
            int statNow = stat.get();
            if (statNow == STAT_RUNNING) {
                throw new IllegalStateException("Spider is already running!");
            }
            if (stat.compareAndSet(statNow, STAT_RUNNING)) {
                break;
            }
        }
    }

    protected void extractAndAddRequests(Page page, boolean spawnUrl) {
        if (spawnUrl && CollectionUtils.isNotEmpty(page.getTargetRequests())) {
            for (Request request : page.getTargetRequests()) {
                addRequest(request);
            }
        }
    }

    private void addRequest(Request request) {
        if (site.getDomain() == null && request != null && request.getUrl() != null) {
            site.setDomain(UrlUtils.getDomain(request.getUrl()));
        }
        scheduler.push(request, this);
    }

    public Spider addRequests(Request... requests) {
        for (Request request : requests) {
            addRequest(request);
        }
        signalNewUrl();
        return this;
    }

    private void processRequest(Request request) {
        if (!robotsServer.allow(request, this)) {
            LOGGER.warn("The current site is not allowed to crawl the url: {}", request.getUrl());
            return;
        }
        Page page = downloader.download(request, this);
        if (page.isDownloadSuccess()) {
            onDownloadSuccess(request, page);
        } else {
            onDownloadFail(request);
        }
    }

    private void onDownloadSuccess(Request request, Page page) {
        if (site.getAcceptStatusCode().contains(page.getStatusCode())) {
            pageProcessor.process(page);
            extractAndAddRequests(page, spawnUrl);
            if (!page.getResults().isJump()) {
                for (Pipeline pipeline : pipelines) {
                    pipeline.process(page.getResults(), this);
                }
            }
        } else {
            LOGGER.warn("page status code error, page: {}, code: {}", request.getUrl(), page.getStatusCode());
        }
        sleep(site.getSleepTime());
        return;
    }

    private void onDownloadFail(Request request) {
        if (site.getCycleRetryTimes() == 0) {
            sleep(site.getSleepTime());
        } else {
            doCycleRetry(request);
        }
    }

    private void doCycleRetry(Request request) {
        Object cycleTriedTimesObject = request.getExtra(Request.CYCLE_TRIED_TIMES);
        if (cycleTriedTimesObject == null) {
            addRequest(SerializationUtils.clone(request).setPriority(0).putExtra(Request.CYCLE_TRIED_TIMES, 1));
        } else {
            int cycleTriedTimes = (Integer) cycleTriedTimesObject;
            cycleTriedTimes++;
            if (cycleTriedTimes < site.getCycleRetryTimes()) {
                addRequest(SerializationUtils.clone(request).setPriority(0).putExtra(Request.CYCLE_TRIED_TIMES, cycleTriedTimes));
            }
        }
        sleep(site.getRetrySleepTime());
    }

    protected void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ie) {
            LOGGER.error("The current thread interrupted when sleep ", ie);
        }
    }

    public void runAsync() {
        Thread thread = new Thread(this);
        thread.setDaemon(false);
        thread.start();
    }

    public void stop() {
        if (stat.compareAndSet(STAT_RUNNING, STAT_STOPPED)) {
            LOGGER.info("Spider " + getUUID() + " stop success!");
        } else {
            LOGGER.warn("Spider " + getUUID() + " stop fail!");
        }
    }

    public void close() {
        destroyEach(downloader);
        destroyEach(pageProcessor);
        destroyEach(scheduler);
        for (Pipeline pipeline : pipelines) {
            destroyEach(pipeline);
        }
        threadPool.shutdown();
    }

    private void destroyEach(Object object) {
        if (object instanceof Closeable) {
            try {
                ((Closeable) object).close();
            } catch (IOException e) {
                LOGGER.error("destroy object error: ", e);
            }
        }
    }

    private void waitNewUrl() {
        newUrlLock.lock();
        try {
            if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
                return;
            }
            newUrlCondition.await(emptySleepTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.warn("waitNewUrl - interrupted, error = {}", e);
        } finally {
            newUrlLock.unlock();
        }
    }

    private void signalNewUrl() {
        try {
            newUrlLock.lock();
            newUrlCondition.signalAll();
        } finally {
            newUrlLock.unlock();
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public String getUUID() {
        if (uuid != null) {
            return uuid;
        }
        if (site != null) {
            return site.getDomain();
        }
        uuid = UUID.randomUUID().toString();
        return uuid;
    }

    protected void onSuccess(Request request) {
        if (CollectionUtils.isNotEmpty(spiderListeners)) {
            for (SpiderListener spiderListener : spiderListeners) {
                spiderListener.onSuccess(request);
            }
        }
    }

    protected void onError(Request request) {
        if (CollectionUtils.isNotEmpty(spiderListeners)) {
            for (SpiderListener spiderListener : spiderListeners) {
                spiderListener.onError(request);
            }
        }
    }

    public enum Status {
        Init(0), Running(1), Stopped(2);

        private int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Status getStatusByValue(int value) {
            for (Status status : Status.values()) {
                if (value == status.getValue()) {
                    return status;
                }
            }
            return Init;
        }
    }

    public void test(String... urls) {
        initComponent();
        if (urls.length > 0) {
            for (String url : urls) {
                processRequest(new Request(url));
            }
        }
    }

    public void start() {
        runAsync();
    }

    @Override
    public void run() {
        checkRunningStat();
        initComponent();
        LOGGER.info("Spider {} start!", getUUID());
        while (!Thread.currentThread().isInterrupted() && stat.get() == STAT_RUNNING) {
            final Request request = scheduler.poll(this);
            if (request == null) {
                if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
                    break;
                }
                waitNewUrl();
            } else {
                threadPool.execute(() -> {
                    try {
                        processRequest(request);
                        onSuccess(request);
                    } catch (Exception e) {
                        onError(request);
                        LOGGER.error("process request: " + request + " error ", e);
                    } finally {
                        pageCount.incrementAndGet();
                        signalNewUrl();
                    }
                });
            }
        }
        stat.set(STAT_STOPPED);
        if (destroyWhenExit) {
            close();
        }
        LOGGER.info("Spider {} closed! {} pages downloaded~", getUUID(), pageCount);
    }

}
