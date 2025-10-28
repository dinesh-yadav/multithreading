package org.example;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("my runnable thread: " + Thread.currentThread().getName());
    }
}
