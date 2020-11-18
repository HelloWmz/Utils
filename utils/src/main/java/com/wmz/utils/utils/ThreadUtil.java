package com.wmz.utils.utils;

import java.util.concurrent.*;

/**
 * 线程池的使用
 * /**
 * 初始化线程池和Handler
 * <p>
 * private void initThreadHandler(){
 * executorService=ThreadUtil.newDynamicSingleThreadedExecutor(new AlivcEditThread());
 *
 * }
 * <p>
 * public static class AlivcEditThread implements ThreadFactory {
 *
 * @Override public Thread newThread(Runnable r) {
 * Thread thread = new Thread(r);
 * thread.setName("AlivcEdit Thread");
 * return thread;
 * }
 * }
 * executorService.execute(Runnable runnable);
 */


public class ThreadUtil {

    private static int corePoolSize = 1;
    private static int maximumPoolSize = 1;
    private static int keepAliveTime = 60;

    public static ExecutorService newDynamicSingleThreadedExecutor(ThreadFactory threadFactory) {
        ThreadPoolExecutor mExecutorService = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        mExecutorService.allowCoreThreadTimeOut(true);

        return mExecutorService;
    }


}
