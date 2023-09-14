package xyz.pplax.file.utils;

import xyz.pplax.core.util.threadpoll.ExecutorUtils;
import xyz.pplax.file.dto.FileEntityDTO;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 文件上传线程池相关
 */
public class UploadFileExecutor {
    private static final int QUEUE_CAPACITY = 100;
    private final static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            ExecutorUtils.getCorePollSize(1),
            ExecutorUtils.getCorePollSize(1),
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(QUEUE_CAPACITY)
    );
    private static final ExecutorCompletionService<FileEntityDTO> completionService =
            new ExecutorCompletionService<>(executor, new LinkedBlockingQueue<>(QUEUE_CAPACITY));

    public static ThreadPoolExecutor getInstance() {
        return executor;
    }

}
