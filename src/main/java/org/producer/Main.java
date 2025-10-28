package org.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.producer.Main.EOF;

public class Main {
    public static final String EOF = "EOF";
    public static final String producer = "####";
    public static final String consumer1 = "****";
    public static final String consumer2 = "@@@@";
    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        Producer myProducer = new Producer(buffer, producer);
        Consumer myConsumer1 = new Consumer(buffer, consumer1);
        Consumer myConsumer2 = new Consumer(buffer, consumer2);
        new Thread(myProducer).start();
        new Thread(myConsumer1).start();
        new Thread(myConsumer2).start();

    }
}

class Producer implements Runnable {
    private List<String> buffer;
    private String producer;

    public Producer(List<String> buffer, String producer) {
        this.buffer = buffer;
        this.producer = producer;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num: nums) {
            try {
                System.out.println(producer + "adding number " + num);
                synchronized(buffer) {
                    buffer.add(num);
                }

                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(producer + "adding EOF ");
        synchronized(buffer) {
            buffer.add("EOF");
        }
    }
}

class Consumer implements Runnable {
    private List<String> buffer;
    private String consumer;

    public Consumer(List<String> buffer, String consumer) {
        this.buffer = buffer;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while(true) {
            synchronized(buffer) {
                if (buffer.isEmpty()) {
                    continue;
                }

                if (buffer.get(0).equals(EOF)) {
                    System.out.println(consumer + " exiting");
                    break;
                } else {
                    System.out.println(consumer + " removed " + buffer.remove(0));
                }
            }
        }
    }
}
