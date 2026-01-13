package org.executorService;

import java.util.concurrent.*;

public class ThreadPoolExecutorServiceExample {
    public static void main(String[] args) {
        System.out.println("This is a placeholder for ThreadPoolExecutorServiceExample.");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4,
                10, java.util.concurrent.TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10));

        threadPoolExecutor.submit(() -> {
            System.out.println("Task is running in thread: " + Thread.currentThread().getName());
        });

        threadPoolExecutor.submit(() -> {
            System.out.println("Another task is running in thread: " + Thread.currentThread().getName());
        });

        //pass callable tasks and runnable tasks to the executor
        Callable<String> callableTask = () -> {
            return "Callable Task Result from thread: " + Thread.currentThread().getName();
        };
        Future<String> future = threadPoolExecutor.submit(callableTask);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        threadPoolExecutor.shutdown();
    }
}
