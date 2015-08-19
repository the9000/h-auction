package hackerati.task.impl;

import hackerati.provided.KVStore;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A simplest KV store implementation.
 *
 * It is fully concurrent, like e.g Cassandra.
 *
 * We only need it for tests.
 */
public class KVStoreImpl implements KVStore<String, CompleteAuctionStatusImpl> {

  final ConcurrentMap<String, CompleteAuctionStatusImpl> myData;

  public KVStoreImpl() {
    myData = new ConcurrentHashMap<>();
  }

  @Override
  public CompleteAuctionStatusImpl get(String key) {
    return myData.get(key);
  }

  @Override
  public void set(String key, CompleteAuctionStatusImpl value) {
    myData.put(key, value);
  }
}

