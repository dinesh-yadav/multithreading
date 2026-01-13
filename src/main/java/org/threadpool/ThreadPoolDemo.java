package org.threadpool;

public class ThreadPoolDemo {

    public static void main(String[] args) throws Exception {

        MyThreadPoolExecutor executor = new MyThreadPoolExecutor(3, 10);

        MyFuture<Integer> future = executor.submit(() -> {
            Thread.sleep(1000);
            return 42;
        });

        executor.execute(() -> System.out.println("Runnable task executed"));

        System.out.println("Result from Callable: " + future.get());

        executor.shutdown();
    }
}

