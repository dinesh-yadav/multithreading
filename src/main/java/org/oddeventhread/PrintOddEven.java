package org.oddeventhread;

public class PrintOddEven {
    private volatile int num = 0;
    private int limit;

    public PrintOddEven(int limit) {
        this.limit = limit;
    }

    public synchronized void printEven() {
       while (num <= limit) {
           if (num%2 == 0) {
               System.out.print(num + " ");
               num++;
               notify();
           } else {
               try {
                   wait();
               } catch (InterruptedException ie) {
                   System.out.println(ie.getMessage());
               }
           }
       }
    }

    public synchronized void printOdd() {
        while (num <= limit) {
            if (num%2 == 1) {
                System.out.print(num + " ");
                num++;
                notify();
            } else {
                try {
                    wait();
                } catch (InterruptedException ie) {
                    System.out.println(ie.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int limit = 20;
        PrintOddEven printOddEven = new PrintOddEven(limit);
        Thread oddThread = new Thread(printOddEven::printOdd);
        Thread evenThread = new Thread(printOddEven::printEven);

        evenThread.start();
        oddThread.start();


    }
}
