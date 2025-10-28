package org.example;

public class MyThread extends Thread{
    private String name;

    public MyThread(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("thread name: " + currentThread().getName());
    }
}
