package org.threadpool;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingTaskQueue {

    private final Queue<Runnable> queue = new LinkedList<>();
    private final int capacity;

    public BlockingTaskQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(Runnable task) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.offer(task);
        notifyAll();
    }

    public synchronized Runnable take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        Runnable task = queue.poll();
        notifyAll();
        return task;
    }
}

