package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main thread: " + Thread.currentThread().getName());
        Thread thread = new Thread(() -> {
            System.out.println("first thread: " + Thread.currentThread().getName());
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        Thread myThread = new MyThread("dinesh");
        myThread.start();

        Thread myRunnable = new Thread(new MyRunnable());
        myRunnable.start();

        new Thread(() -> {
            System.out.println("anonymous class thread: " + Thread.currentThread().getName());
        }).start();

        Thread newThread = new Thread(new MyRunnable() {
            @Override
            public void run() {
                System.out.println("new runnable way" + Thread.currentThread().getName());
            }
        });
        newThread.start();

        Thread newThreadRunnable = new Thread(() -> System.out.println("new runnable way" + Thread.currentThread().getName()));
        newThreadRunnable.start();

        System.out.println("From main thread");
    }
}