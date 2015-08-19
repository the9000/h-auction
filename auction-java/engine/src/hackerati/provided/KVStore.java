package hackerati.provided;

/**
 * Key-Value store provided externally.
 *
 * We make the interface accept only one type of keys
 * and only one type of values to help avoid any serialization code.
 * It suffices for our task, but a real KV store would be more generic
 * and involve packing and unpacking, and some type casting.
 */
public interface KVStore<K, V> {

  /**
   * Retrieve an object by key.
   * @param key the key
   * @return the value, or null.
   */
  /* @Nullable */ V get(/* @NonNull */ K key);

  /**
   * Insert or update the value at key.
   * @param key the key.
   * @param value the new value.
   */
  void set(/* @NonNull */ K key, /* @NonNull */ V value);

  /**
   * In any realistic case, there would be a method to retrieve all
   * or some of the keys available. We do not declare it because we don't
   * strictly need it for the exercise.
   * {@code Iterable<K> getKeys(String search_query)}
   */
}
