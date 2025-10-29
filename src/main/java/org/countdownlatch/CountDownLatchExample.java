package org.countdownlatch;

import java.util.concurrent.*;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        int numOfServices = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(numOfServices);
        //countDownLatch blocks the main thread
        CountDownLatch latch = new CountDownLatch(numOfServices);
        executorService.submit(new DependentService(latch));
        executorService.submit(new DependentService(latch));
        executorService.submit(new DependentService(latch));
        latch.await();

        System.out.println("Main");
        executorService.shutdown();
//        executorService.shutdownNow();

        //countdown latch with simple threads
        CountDownLatch latch1 = new CountDownLatch(numOfServices);

        for (int i = 0; i < numOfServices; i++) {
            new Thread(new DependentServiceThread(latch1)).start();
        }
//        latch is not reusable, so need to create new.
        latch1.await();
//        This will wait for 6 secs only then execute the main thread
//        then rest of the threads will run.
//        latch1.await(6000, TimeUnit.MILLISECONDS);

        System.out.println("Main");

    }
}

class DependentService implements Callable<String> {
    private final CountDownLatch latch;

    public DependentService(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public String call() throws InterruptedException {
        try {
            System.out.println(Thread.currentThread().getName() + " is started!!");
            Thread.sleep(2000);
        } finally {
            latch.countDown();
        }
        return "ok";
    }
}

class DependentServiceThread implements Runnable {
    private final CountDownLatch latch;

    public DependentServiceThread(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " is started!!");
            Thread.sleep(2000);
        } catch(InterruptedException e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            latch.countDown();
        }
    }
}
