package org.referencedemos;

// The object being referenced
class Employee {
    String name = "Alice";
    public String toString() { return name; }
    protected void finalize() { System.out.println("Employee object collected."); }
}

/**
 * This is the standard reference you use every day.
 * It prevents the object from being garbage collected.
 */
public class StrongReferenceDemo {
    public static void main(String[] args) {
        // **Strong Reference:** The object cannot be collected.
        Employee strongRef = new Employee();
        System.out.println("1. Strong Reference: " + strongRef.name);

        // Removing the strong reference (making the object eligible for GC)
        strongRef = null;

        // Requesting GC - the object *is* now eligible, but there's no guarantee when it will run.
        System.gc();
        System.out.println("2. After setting strongRef to null and calling GC. " +
                "Check console for 'Employee object collected.' (Timing is not guaranteed).");
    }
}
