package org.collection;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapExample {
    public static void main(String[] args) {
        WeakReference<Student> weakReference = new WeakReference<>(new Student(1, "Ram"));
        System.out.println("weakReference: " + weakReference.get());
        // if keys are weak referenced then weak hash map values
        //  will be garbage collected.
        WeakHashMap<String, Image> imageCache = new WeakHashMap<>();
        loadCache(imageCache);
        System.out.println(imageCache);
        System.gc();
        simulateApplicationRunning();
        System.out.println("Cache after running (some entries may be cleared): " + imageCache);

        // if gc runs, it will remove the weak reference objects.
        System.out.println("weakReference after gc: " + weakReference.get());
    }

    public static void loadCache(Map<String, Image> imageCache) {
        // here keys variable will be garbage collected after
        // running this function, so keys will be weak referenced.
        String k1 = new String("img1");
        String k2 = new String("img2");
        imageCache.put(k1, new Image("Image 1"));
        imageCache.put(k2, new Image("Image 2"));
    }


    private static void simulateApplicationRunning() {
        try {
            System.out.println("Simulating application running...");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Image {
    private String name;

    public Image(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
