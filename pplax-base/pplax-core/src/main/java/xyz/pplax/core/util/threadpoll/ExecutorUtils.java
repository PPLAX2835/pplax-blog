package xyz.pplax.core.util.threadpoll;

/**
 * 线程池
 */
public class ExecutorUtils {

    public static int getCorePollSize(int additionalProcessorsNum) {
        return Runtime.getRuntime().availableProcessors() + additionalProcessorsNum;
    }
}
