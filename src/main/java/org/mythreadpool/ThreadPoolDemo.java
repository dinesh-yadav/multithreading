package org.mythreadpool;

import java.util.concurrent.Future;

public class ThreadPoolDemo {

    public static void main(String[] args) throws Exception {

        MyThreadPoolExecutor executor = new MyThreadPoolExecutor(3, 10);

        Future<Integer> future = executor.submit(() -> {
            Thread.sleep(1000);
            return 100;
        });

        executor.execute(() -> {
            System.out.println("Runnable executed by " + Thread.currentThread().getName());
        });

        System.out.println("Callable result: " + future.get());

        executor.shutdown();
    }
}

