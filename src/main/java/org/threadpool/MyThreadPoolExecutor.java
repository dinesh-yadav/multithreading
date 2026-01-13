package org.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MyThreadPoolExecutor {

    private final BlockingTaskQueue taskQueue;
    private final List<WorkerThread> workers;
    private volatile boolean isShutdown = false;

    public MyThreadPoolExecutor(int poolSize, int queueCapacity) {
        taskQueue = new BlockingTaskQueue(queueCapacity);
        workers = new ArrayList<>();

        for (int i = 0; i < poolSize; i++) {
            WorkerThread worker = new WorkerThread(taskQueue);
            worker.start();
            workers.add(worker);
        }
    }

    public void execute(Runnable task) throws InterruptedException {
        if (isShutdown) {
            throw new IllegalStateException("ThreadPool is shutdown");
        }
        taskQueue.put(task);
    }

    public <V> MyFuture<V> submit(Callable<V> callable) throws InterruptedException {
        if (isShutdown) {
            throw new IllegalStateException("ThreadPool is shutdown");
        }

        MyFuture<V> future = new MyFuture<>();

        Runnable wrappedTask = () -> {
            try {
                V result = callable.call();
                future.complete(result);
            } catch (Throwable t) {
                future.completeExceptionally(t);
            }
        };

        taskQueue.put(wrappedTask);
        return future;
    }

    public MyFuture<?> submit(Runnable runnable) throws InterruptedException {
        return submit(() -> {
            runnable.run();
            return null;
        });
    }

    public void shutdown() {
        isShutdown = true;
        for (WorkerThread worker : workers) {
            worker.shutdown();
        }
    }
}
