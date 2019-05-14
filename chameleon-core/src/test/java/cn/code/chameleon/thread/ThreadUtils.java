package cn.code.chameleon.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liumingyu
 * @date 2019-01-28 9:00 PM
 */
public class ThreadUtils {
    public ThreadUtils() {
    }

    public static ExecutorService createPoolWithFixQueue(int coreThreadSize, int maxThreadSize, int queueSize, String threadNameFormat) {
        ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat(threadNameFormat).setDaemon(true).build();
        ArrayBlockingQueue queue = new ArrayBlockingQueue(queueSize);
        return new ThreadPoolExecutor(coreThreadSize, maxThreadSize, 60L, TimeUnit.SECONDS, queue, threadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    public static ExecutorService createPoolWithFixQueue(int coreThreadSize, int maxThreadSize, int queueSize, String threadNameFormat, RejectedExecutionHandler rejectedExecutionHandler) {
        ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat(threadNameFormat).setDaemon(true).build();
        ArrayBlockingQueue queue = new ArrayBlockingQueue(queueSize);
        return new ThreadPoolExecutor(coreThreadSize, maxThreadSize, 60L, TimeUnit.SECONDS, queue, threadFactory, rejectedExecutionHandler);
    }
}