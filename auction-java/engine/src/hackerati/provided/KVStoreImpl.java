package hackerati.provided;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A simplest KV store implementation.
 *
 * It is fully concurrent, like e.g Cassandra.
 */
public class KVStoreImpl<K, V> implements KVStore<K, V> {

  private final ConcurrentMap<K, V> myData;

  public KVStoreImpl() {
    myData = new ConcurrentHashMap<>();
  }

  @Override
  public V get(K key) {
    return myData.get(key);
  }

  @Override
  public void set(K key, V value) {
    myData.put(key, value);
  }
}

