package org.mythreadpool;

import java.util.concurrent.BlockingQueue;

public class WorkerThread extends Thread {

    private final BlockingQueue<Runnable> taskQueue;
    private volatile boolean running = true;

    public WorkerThread(BlockingQueue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (running || !taskQueue.isEmpty()) {
            try {
                Runnable task = taskQueue.take(); // blocks
                task.run();
            } catch (InterruptedException e) {
                if (!running) {
                    break;
                }
            }
        }
    }

    public void shutdown() {
        running = false;
        interrupt();
    }
}

