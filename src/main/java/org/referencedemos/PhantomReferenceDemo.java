package org.referencedemos;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * A PhantomReference is used for post-mortem cleanup because you can't access the object
 * via get() (it always returns null). The reference is enqueued after the object has been
 * finalized and its memory reclaimed, signaling that the cleanup can now occur safely.
 */
public class PhantomReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        // 1. Setup the Queue
        // Reference queues, to which registered reference
        // objects are appended by the garbage collector after the
        // appropriate reachability changes are detected.
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

        // 2. Create the Object and Phantom Reference
        Object cleanupTarget = new Object() {
            protected void finalize() {
                System.out.println("Finalize called on cleanupTarget.");
            }
        };

        PhantomReference<Object> phantomRef = new PhantomReference<>(cleanupTarget, referenceQueue);

        // 3. Remove Strong Reference
        System.out.println("1. Phantom Reference (Initial get()): " + phantomRef.get()); // Always null
        cleanupTarget = null;
        System.out.println("2. Strong reference removed. Calling GC...");

        // 4. Request GC
        System.gc();
        Thread.sleep(60000); // Give GC time to run and finalize/enqueue

        // 5. Check the Queue
        // The 'remove' call will block until a reference is available in the queue.
        System.out.println("3. Checking ReferenceQueue for enqueued reference...");
        if (referenceQueue.poll() == phantomRef) {
            System.out.println("   => SUCCESS: Phantom reference found in queue. Safe to perform cleanup.");
        } else {
            System.out.println("   => FAIL: Phantom reference not yet enqueued.");
        }
    }
}
