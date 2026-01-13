package org.threadpool;
public class WorkerThread extends Thread {

    private final BlockingTaskQueue taskQueue;
    private volatile boolean running = true;

    public WorkerThread(BlockingTaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (running || !Thread.currentThread().isInterrupted()) {
            try {
                Runnable task = taskQueue.take();
                task.run();
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void shutdown() {
        running = false;
        interrupt();
    }
}

