package org.cyclicbarrier;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierExample {
        public static void main(String[] args) throws InterruptedException {
            int numOfServices = 3;
            ExecutorService executorService = Executors.newFixedThreadPool(numOfServices);
            //countDownLatch blocks the main thread but
            // barrier doesn't block the main thread.
            CyclicBarrier barrier = new CyclicBarrier(numOfServices);
            executorService.submit(new DependentService(barrier));
            executorService.submit(new DependentService(barrier));
            executorService.submit(new DependentService(barrier));

            System.out.println("Main");
            executorService.shutdown();
//            barrier can be reused after resetting.
            barrier.reset();
            System.out.println(barrier.getNumberWaiting());
            System.out.println(barrier.getParties());

//        executorService.shutdownNow();

            //countdown latch with simple threads
//            CountDownLatch latch1 = new CountDownLatch(numOfServices);
//
//            for (int i = 0; i < numOfServices; i++) {
//                new Thread(new org.countdownlatch.DependentServiceThread(latch1)).start();
//            }
////        latch is not reusable, so need to create new.
//            latch1.await();
////        This will wait for 6 secs only then execute the main thread
////        then rest of the threads will run.
////        latch1.await(6000, TimeUnit.MILLISECONDS);
//
//            System.out.println("Main");

        }
    }

class DependentService implements Callable<String> {
    private final CyclicBarrier barrier;

    public DependentService(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public String call() throws Exception{
        System.out.println(Thread.currentThread().getName() + " is started!!");
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + " is waiting for barrier.");
        barrier.await();
        return "ok";
    }
}

//class DependentServiceThread implements Runnable {
//    private final CountDownLatch latch;
//
//    public DependentServiceThread(CountDownLatch latch) {
//        this.latch = latch;
//    }
//
//    @Override
//    public void run() {
//        try {
//            System.out.println(Thread.currentThread().getName() + " is started!!");
//            Thread.sleep(2000);
//        } catch(InterruptedException e) {
//            System.out.println("Exception: " + e.getMessage());
//        } finally {
//            latch.countDown();
//        }
//    }
//}


