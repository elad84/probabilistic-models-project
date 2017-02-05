package com.idc.computersience.pm.cache;

import com.google.common.cache.CacheBuilder;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of the cache using manual cache.
 *
 * This implementation is doing loading operations manually, but can have TTL for keys.
 *
 *
 * @author eladcohen
 */
public class ManualCache<K, V> implements InternalCache<K, V> {

    private final com.google.common.cache.Cache<K, V> cache;

    /**
     * Creates a local cache with no eviction policy of any kind
     *
     * Eviction operations are on the using class
     */
    public ManualCache(){
        cache = CacheBuilder.newBuilder().build();
    }


    /**
     * Creates a local cache.
     *
     * When expireMilliseconds is defined add expiry after access operations
     *
     * @param expireMilliseconds the time in milliseconds to save the value after access (create, save, get)
     */
    @SuppressWarnings("unchecked")
    public ManualCache(long expireMilliseconds){
        CacheBuilder builder = CacheBuilder.newBuilder();

        if(expireMilliseconds > 0){
            //add expire after access
            builder.expireAfterAccess(expireMilliseconds, TimeUnit.MILLISECONDS);
        }

        cache = builder.build();
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public V get(K key) {
        return cache.getIfPresent(key);
    }

    @Override
    public V get(K key, Callable<V> loader) throws Exception {
        return cache.get(key, loader);
    }

    @Override
    public void evict(K key) {
        cache.invalidate(key);
    }

    @Override
    public Map<K,V> getAll() {
        return cache.asMap();
    }
}