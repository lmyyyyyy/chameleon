package cn.code.chameleon.scheduler;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.scheduler.component.DuplicateRemover;
import cn.code.chameleon.thread.ThreadProvider;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liumingyu
 * @create 2018-04-17 下午9:02
 */
public class FileCachQueueSchduler extends DuplicateRemoveScheduler implements MonitorableScheduler, Closeable {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final String DEFAULT_PATH = "/data/chameleon/cache/";

    private String filePath = System.getProperty("java.io.tmpdir");

    private String fileUrlAllName = ".urls.txt";

    private String fileCursor = ".cursor.txt";

    private Task task;

    private PrintWriter fileUrlWriter;

    private PrintWriter fileCursorWriter;

    private AtomicInteger cursor = new AtomicInteger();

    private AtomicBoolean inited = new AtomicBoolean(false);

    private BlockingQueue<Request> queue;

    private Set<String> urls;

    private ScheduledExecutorService flushThreadPool;

    public FileCachQueueSchduler() {
        this.filePath = DEFAULT_PATH;
        initDuplicateRemover();
    }

    public FileCachQueueSchduler(String path) {
        if (path == null || "".equalsIgnoreCase(path)) {
            this.filePath = DEFAULT_PATH;
        }
        if (!path.endsWith("/") && !path.endsWith("\\")) {
            path += "/";
        }
        this.filePath = path;
        initDuplicateRemover();
    }

    private void initDuplicateRemover() {
        setDuplicateRemover(new DuplicateRemover() {
            @Override
            public boolean isDuplicate(Request request, Task task) {
                if (!inited.get()) {
                    init(task);
                }
                return !urls.add(request.getUrl());
            }

            @Override
            public void resetDuplicate(Task task) {
                urls.clear();
            }

            @Override
            public int getDuplicateCounts(Task task) {
                return urls.size();
            }
        });
    }

    private void init(Task task) {
        this.task = task;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        readFile();
        initWriter();
        initFlushThread();
        inited.set(true);
        LOGGER.info("init cache scheduler success");
    }

    private void initFlushThread() {
        flushThreadPool = ThreadProvider.createSchedulePoolWithFixQueue(1, "chameleon-scheduler-%d");
        flushThreadPool.scheduleAtFixedRate(() -> flush(), 10, 10, TimeUnit.SECONDS);
    }

    private void flush() {
        LOGGER.info("{} delay time task start!", Thread.currentThread().getName());

        fileCursorWriter.flush();
        fileUrlWriter.flush();

        LOGGER.info("{} delay time task end!", Thread.currentThread().getName());
    }

    private void initWriter() {
        try {
            fileUrlWriter = new PrintWriter(new FileWriter(getFileName(fileUrlAllName), true));
            fileCursorWriter = new PrintWriter(new FileWriter(getFileName(fileCursor), false));
        } catch (IOException e) {
            throw new RuntimeException("init cache scheduler error ", e);
        }
    }

    private void readFile() {
        try {
            queue = new LinkedBlockingQueue<>();
            urls = new LinkedHashSet<>();
            readCursorFile();
            readUrlFile();
        } catch (FileNotFoundException e) {
            LOGGER.warn("init cache file " + getFileName(fileUrlAllName));
        } catch (IOException e) {
            LOGGER.error("init cache file failed ", e);
        }
    }

    private void readUrlFile() throws IOException {
        String line;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(getFileName(fileUrlAllName)));
            int lineReaded = 0;
            while ((line = bufferedReader.readLine()) != null) {
                urls.add(line.trim());
                lineReaded++;
                if (lineReaded > cursor.get()) {
                    queue.add(new Request(line));
                }
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }

    private void readCursorFile() throws IOException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(getFileName(fileCursor)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                cursor = new AtomicInteger(NumberUtils.toInt(line));
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }

    private String getFileName(String filename) {
        return filePath + task.getUUID() + filename;
    }

    @Override
    protected void pushWhenNoDuplicate(Request request, Task task) {
        if (!inited.get()) {
            init(task);
        }
        queue.add(request);
        LOGGER.info("{} write url : {} into cache file", Thread.currentThread().getName(), request.getUrl());
        fileUrlWriter.println(request.getUrl());
    }

    @Override
    public int getLeftRequestsCount(Task task) {
        return queue.size();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return getDuplicateRemover().getDuplicateCounts(task);
    }

    @Override
    public Request poll(Task task) {
        if (!inited.get()) {
            init(task);
        }
        fileCursorWriter.println(cursor.incrementAndGet());
        LOGGER.info("{} write cursor : {} into cache file", Thread.currentThread().getName(), cursor.get());
        return queue.poll();
    }

    @Override
    public Request peek(Task task) {
        return queue.peek();
    }

    @Override
    public void close() throws IOException {
        flushThreadPool.shutdown();
        fileUrlWriter.close();
        fileCursorWriter.close();
    }
}
