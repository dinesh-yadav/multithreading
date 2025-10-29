package org.volatileexample;

class SharedObj {
    private volatile boolean flag = false;

    public void setFlagTrue() {
        flag = true;
    }

    public boolean getFlag() {
        while (!flag) {
            // do nothing
        }
        System.out.println(Thread.currentThread().getName() + " is getting flag.");
        return flag;
    }

}

public class VolatileExample {
    public static void main(String[] args) {
        SharedObj sharedObj = new SharedObj();
        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sharedObj.setFlagTrue();
            System.out.println(Thread.currentThread().getName() + " set flag to true");
        });
        Thread reader = new Thread(() -> sharedObj.getFlag());
        writer.start();
        reader.start();
    }

}
