package hackerati.task.impl;

import hackerati.provided.KVStore;
import hackerati.task.AdminEngine;
import hackerati.task.AuctionPhase;
import hackerati.task.BiddingQueryEngine;
import hackerati.task.BiddingStatus;

/**
 * Auction administrators receive an instance of this.
 */
class AdminTerminal implements AdminEngine, BiddingQueryEngine {

  private final KVStore<String, CompleteAuctionStatusImpl> myKVStore;
  private final Object myLockingObject;

  public AdminTerminal(/* @NonNull */ KVStore<String, CompleteAuctionStatusImpl> myKVStore,
                       /* @NonNull */ Object myLockingObject) {
    this.myKVStore = myKVStore;
    this.myLockingObject = myLockingObject;
  }

  @Override
  public void enlistItem(String item_name, int reserved_price) {
    CompleteAuctionStatusImpl status = new CompleteAuctionStatusImpl(
        AuctionPhase.DORMANT, reserved_price, null, null
    );
    synchronized (myLockingObject) {

    }
  }

  @Override
  public void startAuction(String item_name) {

  }

  @Override
  public void callAuction(String item_name) {

  }

  @Override
  public BiddingStatus getBiddingStatus(String item_name) {
    return null;
  }
}
