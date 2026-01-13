package org.mythreadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MyThreadPoolExecutor {

    private final BlockingQueue<Runnable> taskQueue;
    private final List<WorkerThread> workers;
    private volatile boolean isShutdown = false;

    public MyThreadPoolExecutor(int poolSize, int queueCapacity) {
        this.taskQueue = new LinkedBlockingQueue<>(queueCapacity);
        this.workers = new ArrayList<>();

        for (int i = 0; i < poolSize; i++) {
            WorkerThread worker = new WorkerThread(taskQueue);
            worker.start();
            workers.add(worker);
        }
    }

    // Runnable without result
    public void execute(Runnable task) {
        if (isShutdown) {
            throw new RejectedExecutionException("ThreadPool is shutdown");
        }
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Callable with result
    public <V> Future<V> submit(Callable<V> callable) {
        if (isShutdown) {
            throw new RejectedExecutionException("ThreadPool is shutdown");
        }

        FutureTask<V> futureTask = new FutureTask<>(callable);

        try {
            taskQueue.put(futureTask);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return futureTask;
    }

    // Runnable with Future
    public Future<?> submit(Runnable runnable) {
        return submit(Executors.callable(runnable));
    }

    public void shutdown() {
        isShutdown = true;
        for (WorkerThread worker : workers) {
            worker.shutdown();
        }
    }
}

