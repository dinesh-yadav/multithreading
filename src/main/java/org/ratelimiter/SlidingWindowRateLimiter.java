package org.ratelimiter;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class SlidingWindowRateLimiter {

    private final ConcurrentLinkedDeque<Long> timestamps;
    private final int capacity; // Maximum allowed requests
    private final long windowSizeMillis; // Time window in milliseconds
    private final ReentrantLock lock;

    public SlidingWindowRateLimiter(int capacity, long windowSize, TimeUnit unit) {
        if (capacity <= 0 || windowSize <= 0) {
            throw new IllegalArgumentException("Capacity and window size must be positive.");
        }
        this.capacity = capacity;
        this.windowSizeMillis = unit.toMillis(windowSize);
        this.timestamps = new ConcurrentLinkedDeque<>();
        this.lock = new ReentrantLock();
    }

    public boolean tryAcquire() {
        lock.lock();
        try {
            long currentTime = System.currentTimeMillis();

            // Remove expired timestamps from the window
            while (!timestamps.isEmpty() && timestamps.peekFirst() < currentTime - windowSizeMillis) {
                timestamps.removeFirst();
            }

            // Check if the current request count exceeds the capacity
            if (timestamps.size() < capacity) {
                timestamps.addLast(currentTime);
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Allow 5 requests in a 10-second window
        SlidingWindowRateLimiter rateLimiter = new SlidingWindowRateLimiter(5, 10, TimeUnit.SECONDS);

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000); // Simulate requests
            if (rateLimiter.tryAcquire()) {
                System.out.println("Request " + (i + 1) + ": ALLOWED");
            } else {
                System.out.println("Request " + (i + 1) + ": DENIED");
            }
        }

        System.out.println("\nWaiting for window to slide...");
        Thread.sleep(5000); // Wait for some timestamps to expire

        for (int i = 10; i < 15; i++) {
            Thread.sleep(500); // Simulate more requests
            if (rateLimiter.tryAcquire()) {
                System.out.println("Request " + (i + 1) + ": ALLOWED");
            } else {
                System.out.println("Request " + (i + 1) + ": DENIED");
            }
        }
    }
}
