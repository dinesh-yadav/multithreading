package org.collection;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class IdentityHashMapExample {
    public static void main(String[] args) {
        String key1 = new String("key");
        String key2 = new String("key");
        Map<String, Integer> map = new HashMap<>();
        map.put(key1, 1);
        map.put(key2, 2);
        System.out.println(map);

        // in identity hashmap, identityHashCode and == methods are used
        // identityHashCode means objects hashCode
        Map<String, Integer> iMap = new IdentityHashMap<>();
        iMap.put(key1, 1);
        iMap.put(key2, 2);
        System.out.println(iMap);

        System.out.println(System.identityHashCode(key1) + " and " + System.identityHashCode(key2));
        System.out.println(key1.hashCode() + " and " + key2.hashCode());
    }
}
