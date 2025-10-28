package org.lock.messy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import static org.lock.messy.Main.EOF;

public class Main {
    public static final String EOF = "EOF";
    public static final String producer = "####";
    public static final String consumer1 = "****";
    public static final String consumer2 = "@@@@";
    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        ReentrantLock bufferLock = new ReentrantLock();
        Producer myProducer = new Producer(buffer, producer, bufferLock);
        Consumer myConsumer1 = new Consumer(buffer, consumer1, bufferLock);
        Consumer myConsumer2 = new Consumer(buffer, consumer2, bufferLock);
        new Thread(myProducer).start();
        new Thread(myConsumer1).start();
        new Thread(myConsumer2).start();

    }
}

class Producer implements Runnable {
    private List<String> buffer;
    private String producer;
    private ReentrantLock bufferLock;

    public Producer(List<String> buffer, String producer, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.producer = producer;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num: nums) {
            try {
                System.out.println(producer + "adding number " + num);
                bufferLock.lock();
                buffer.add(num);
                bufferLock.unlock();

                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(producer + "adding EOF ");
        bufferLock.lock();
        buffer.add("EOF");
        bufferLock.unlock();

    }
}

class Consumer implements Runnable {
    private List<String> buffer;
    private String consumer;
    private ReentrantLock bufferLock;

    public Consumer(List<String> buffer, String consumer, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.consumer = consumer;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        while(true) {
            bufferLock.lock();
            if (buffer.isEmpty()) {
                bufferLock.unlock();
                continue;
            }

            if (buffer.get(0).equals(EOF)) {
                System.out.println(consumer + " exiting");
                bufferLock.unlock();
                break;
            } else {
                System.out.println(consumer + " removed " + buffer.remove(0));
            }
            bufferLock.unlock();

        }
    }
}

