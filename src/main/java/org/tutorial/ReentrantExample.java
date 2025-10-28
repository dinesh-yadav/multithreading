package org.tutorial;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExample {
    private final Lock lock= new ReentrantLock();
//    private final Lock lock1= new ReentrantLock(true); // boolean fair
    public void outerMethod() {
        lock.lock();
        try {
            System.out.println("in outerMethod");
            innerMethod();
        } finally {
            lock.unlock();
        }
    }

    public void innerMethod() {
        // here this is reentrant lock, and same thread is trying to
        // lock again, so it will be permissible.
        // and deadlock will not happen.
        lock.lock();
        // there is another lock which is lockInterruptibly
        //lock.lockInterruptibly();
        try {
            System.out.println("in innerMethod");
        } finally {
            // in case of reentrant lock, there will be a count
            // maintained which will keep track of number of times lock
            // happened.
            // so here lock will not be fully unlocked and just
            // count will be decreased by 1.
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantExample reentrantExample = new ReentrantExample();
        reentrantExample.outerMethod();
    }
}
