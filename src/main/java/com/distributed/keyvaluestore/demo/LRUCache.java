package com.distributed.keyvaluestore.demo;

import java.util.*;

public class LRUCache {
    private final int capacity = 100; // Capacity of the cache
    private LinkedHashMap<String, String> cache;

    public LRUCache() {
        cache = new LinkedHashMap<String, String>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > capacity;
            }
        };
    }

    public String get(String key) {
        return cache.getOrDefault(key, null);
    }

    public void set(String key, String value) {
        cache.put(key, value);
    }

    public String pop(String key) {
        return cache.remove(key);
    }

    public Map<String, String> getCacheData() {
        return Collections.unmodifiableMap(cache);
    }
}
