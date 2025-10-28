package org.deadlock.deadlockfix;

class Pen {
    public synchronized void writeWithPenAndPaper(Paper paper) {
        System.out.println(Thread.currentThread().getName() + " writing with Pen" + this +
                " and waiting for lock on paper  " + paper);
        paper.finishWriting();
    }

    public synchronized void finishWriting() {
        System.out.println("finished writing with pen: " + this);
    }
}

class Paper {
    public synchronized void writeWithPaperAndPen(Pen pen) {
        System.out.println(Thread.currentThread().getName() + " writing with Pen" + this +
                " and waiting for lock on pen  " + pen);
        pen.finishWriting();
    }

    public synchronized void finishWriting() {
        System.out.println("finished writing on paper " + this);
    }
}

class Task1 implements Runnable {
    private Pen pen;
    private Paper paper;

    public Task1(Pen pen, Paper paper) {
        this.pen = pen;
        this.paper = paper;
    }

    @Override
    public void run() {
        pen.writeWithPenAndPaper(paper);
    }
}

class Task2 implements Runnable {
    private Pen pen;
    private Paper paper;

    public Task2(Pen pen, Paper paper) {
        this.pen = pen;
        this.paper = paper;
    }

    @Override
    public void run() {
        synchronized(pen) {
            paper.writeWithPaperAndPen(pen);
        }
    }
}

/**
 * for fixing deadlock, acquire the locks in same order for all the required
 * resources.
 */
public class DeadLockFix {
    public static void main(String[] args) {
        Pen pen = new Pen();
        Paper paper = new Paper();

        Thread thread1 = new Thread(new Task1(pen, paper));
        Thread thread2 = new Thread(new Task2(pen, paper));

        thread1.start();
        thread2.start();

    }
}
