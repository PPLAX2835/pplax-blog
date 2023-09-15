package xyz.pplax.auth.threadpoll;

import xyz.pplax.core.util.threadpoll.ExecutorUtils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 返回登录信息的线程池
 */
public class WriteLoginInfoExecutor {
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            ExecutorUtils.getCorePollSize(1),
            ExecutorUtils.getCorePollSize(5),
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());

    public static ThreadPoolExecutor getInstance() {
        return executor;
    }

}
