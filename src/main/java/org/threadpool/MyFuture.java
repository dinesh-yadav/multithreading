package org.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MyFuture<V> implements Future<V> {

    private V result;
    private Throwable exception;
    private boolean isDone = false;
    private boolean isCancelled = false;

    synchronized void complete(V value) {
        if (isCancelled) return;
        this.result = value;
        this.isDone = true;
        notifyAll();
    }

    synchronized void completeExceptionally(Throwable t) {
        if (isCancelled) return;
        this.exception = t;
        this.isDone = true;
        notifyAll();
    }

    @Override
    public synchronized boolean cancel(boolean mayInterruptIfRunning) {
        if (isDone) return false;
        isCancelled = true;
        isDone = true;
        notifyAll();
        return true;
    }

    @Override
    public synchronized boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public synchronized boolean isDone() {
        return isDone;
    }

    @Override
    public synchronized V get() throws InterruptedException, ExecutionException {
        while (!isDone) {
            wait();
        }
        if (exception != null) {
            throw new ExecutionException(exception);
        }
        return result;
    }

    @Override
    public V get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {

        long end = System.currentTimeMillis() + unit.toMillis(timeout);
        synchronized (this) {
            while (!isDone && System.currentTimeMillis() < end) {
                wait(end - System.currentTimeMillis());
            }
            if (!isDone) {
                throw new TimeoutException();
            }
            if (exception != null) {
                throw new ExecutionException(exception);
            }
            return result;
        }
    }
}

