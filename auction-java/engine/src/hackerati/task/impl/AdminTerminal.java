package hackerati.task.impl;

import hackerati.provided.KVStore;
import hackerati.task.*;

/**
 * Auction administrators receive an instance of this.
 *
 * This is the terminal object of the library. The library user should construct
 * instances of this class and pass it to clients serving administrators.
 */
class AdminTerminal implements AdminEngine, BiddingQueryEngine {

  private final KVStore<String, CompleteAuctionStatusImpl> myKVStore;
  private final Object myLockingObject;

  /**
   * Creates an admin terminal.
   *
   * @param myKVStore KV store, common for all terminals.
   * @param myLockingObject any object, same for all terminals.
   *
   * Note: If we were to do per-item locking, we could synchronize
   *    on item_name.intern().
   */
  public AdminTerminal(/* @NonNull */ KVStore<String, CompleteAuctionStatusImpl> myKVStore,
                       /* @NonNull */ Object myLockingObject) {
    this.myKVStore = myKVStore;
    this.myLockingObject = myLockingObject;
  }

  @Override
  public void enlistItem(String item_name, int reserved_price) {
    // the check is not under the lock because we don't expect to ever unlist anything.
    if (myKVStore.get(item_name) != null) {
      throw new InvalidPhaseTransitionError("Item already listed: " + item_name);
    }
    CompleteAuctionStatusImpl status = new CompleteAuctionStatusImpl(
        AuctionPhase.DORMANT, reserved_price, null, null
    );
    synchronized (myLockingObject) {
      myKVStore.set(item_name, status);
    }
  }

  @Override
  public void startAuction(String item_name) {
    synchronized (myLockingObject) {
      myKVStore.set(item_name, getStatusOrFail(item_name).start());
    }
  }

  @Override
  public void callAuction(String item_name) {
    synchronized (myLockingObject) {
      myKVStore.set(item_name, getStatusOrFail(item_name).call());
    }
  }

  private CompleteAuctionStatusImpl getStatusOrFail(String item_name) {
    CompleteAuctionStatusImpl status = myKVStore.get(item_name);
    if (status == null) {
      throw new AuctionNotFoundError(item_name);
    }
    return status;
  }

  @Override
  public BiddingStatus getBiddingStatus(String item_name) {
    return BiddingStatusImpl.fromAuctionStatusImpl(myKVStore.get(item_name));
  }
}
