package org.referencedemos;

import java.lang.ref.WeakReference;

/**
 * A WeakReference does not prevent GC.
 * If the object is only reachable via weak references, it will be collected in the next GC cycle.
 */
public class WeakReferenceDemo {
    public static void main(String[] args) {
        // Create the strong reference
        String data = new String("Cache Data");

        // Create the Weak Reference
        WeakReference<String> weakRef = new WeakReference<>(data);

        // Check the value through the weak reference
        System.out.println("1. Weak Reference (Initial): " + weakRef.get()); // Cache Data

        // Eliminate the strong reference
        data = null;

        // The object is now only weakly reachable.
        System.out.println("2. Strong reference removed.");

        // Request GC: the object should be collected *almost immediately*.
        System.gc();

        // The object is now collected, and the weak reference should return null.
        System.out.println("3. Weak Reference (After GC): " + weakRef.get()); // null
    }
}
