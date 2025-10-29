package org.volatileexample;

import java.util.concurrent.atomic.AtomicInteger;

class SharedCounter {
    // here with volatile also, counter value will be 2000
    // after running both the threads. So atomic variables are used. which will make sure
    // that only one thread will use the counter.
   // private volatile int counter = 0;
    private AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        counter.incrementAndGet();
//        counter++;
    }

    public int getCounter() {
        return counter.get();
//        return counter;
    }

}

public class AtomicExample {
    public static void main(String[] args) throws InterruptedException {
        SharedCounter sharedCounter = new SharedCounter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sharedCounter.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sharedCounter.increment();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("main counter:" + sharedCounter.getCounter());
    }
}
