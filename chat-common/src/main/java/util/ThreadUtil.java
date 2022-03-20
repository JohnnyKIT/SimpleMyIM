package util;

import lombok.Data;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @className: ThreadUtil
 * @description: 线程相关工具类
 * @author: situjunjie
 * @date: 2022/3/20
 **/
public class ThreadUtil {

    /**
     * CPU核数
     */
    public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 线程保活时间(s)
     */
    public static final int THREAD_KEEP_ALIVE = 120;

    /**
     * 核心线程数量
     */
    public static final int CORE_THREAD_COUNT = 0;

    /**
     * 最大线程数量
     */
    public static final int MAX_THREAD_COUNT = 8;

    /**
     * 阻塞队列
     */
    public static final BlockingQueue BLOCKING_QUEUE = new ArrayBlockingQueue(100);

    /**
     * 定制线程工厂类
     */
    @Data
    public static class CustomThreadFactory implements ThreadFactory {
        private String threadTag;

        public static final AtomicInteger poolNumber = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(threadTag+"-"+poolNumber);
            thread.setDaemon(false);
            return thread;
        }
        public CustomThreadFactory(String threadTag) {
            this.threadTag = threadTag;
        }
    }

    /**
     * 单例懒汉式混合型线程池
     */
    private static class MIX_THREADPOOL_HOLDER{
        private static ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
                0,
                Integer.valueOf((int) Math.floor(MAX_THREAD_COUNT*1.5)),
                THREAD_KEEP_ALIVE,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100),
                new CustomThreadFactory("MIX")
        );
        static {
            Runtime.getRuntime().addShutdownHook(
                    new Thread(()->{
                        EXECUTOR.shutdown();
                    })
            );
        }
    }

    public static Executor getMixExecutor(){
        return MIX_THREADPOOL_HOLDER.EXECUTOR;
    }


}
