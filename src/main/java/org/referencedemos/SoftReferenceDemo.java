package org.referencedemos;

import java.lang.ref.SoftReference;

/**
 * A SoftReference does not prevent GC,
 * but the object will only be collected if the JVM is running low on memory.
 */
public class SoftReferenceDemo {
    public static void main(String[] args) {
        // Create the strong reference
        Object heavyObject = new Object();

        // Create the Soft Reference
        SoftReference<Object> softRef = new SoftReference<>(heavyObject);

        // Eliminate the strong reference
        heavyObject = null;

        // At this point, the object is only softly reachable.
        System.out.println("1. Soft Reference (Before GC): " + softRef.get()); // Not null

        // Triggering GC might not collect it, as memory is likely sufficient.
        System.gc();

        // **To force collection (hypothetically):**
        // In a real-world scenario, you'd need to allocate a HUGE amount of memory
        // to force the JVM to clear the soft reference due to low memory.

        // Check the reference: it might still be there.
        System.out.println("2. Soft Reference (After GC, likely still present): " + softRef.get());
    }
}
