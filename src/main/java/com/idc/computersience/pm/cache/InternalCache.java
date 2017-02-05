package com.idc.computersience.pm.cache;

/**
 * Key value mapping for service internal usage
 *
 * The implementation should be for non external cache
 * @author eladcohen
 */

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Key value mapping for service internal usage
 *
 * The implementation should be for non external cache
 *
 * @author eladcohen
 */
public interface InternalCache<K, V> {

    /**
     * Puts or override a key with given value

     * @param key the key to add mapping for
     * @param value value of the key
     */
    void put(K key, V value);

    /**
     * Gets the value for given key
     *
     * @param key key of the data
     * @return value if key is mapped, null otherwise
     */
    V get(K key);

    /**
     * Gets the given value and if does not exists loads it using this Callable
     * object
     *
     * @param key key of the data
     * @param loader loader to load data for given key
     * @return value for that key of exists
     */
    V get(K key, Callable<V> loader) throws Exception;

    /**
     * Evicts the key from the mapping
     *
     * @param key key to evict from the mapping
     */
    void evict(K key);

    /**
     * Gets all the mapping of the cache
     *
     * @return an unmodifiable map representation of the cached data
     */
    Map<K,V> getAll();
}
