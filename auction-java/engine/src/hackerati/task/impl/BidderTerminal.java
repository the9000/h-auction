package hackerati.task.impl;

import hackerati.provided.KVStore;
import hackerati.task.*;

/**
 * Instances of this are available to bidders.
 *
 * Allows to do the bidding and query bidder-accessible status of items.
 *
 * This is the terminal object of the library. The library user should construct
 * instances of this class and pass it to clients serving bidders.

 */
class BidderTerminal implements BiddingEngine, BiddingQueryEngine {
  private final KVStore<String, CompleteAuctionStatusImpl> myKVStore;
  private final Object myLockingObject;

  /**
   * Creates a bidder terminal.
   *
   * @param kvstore KV store, common for all terminals (not necessarily the same instance).
   * @param locking_object any object, same for all terminals.
   */
  public BidderTerminal(/* @NonNull */ KVStore<String, CompleteAuctionStatusImpl> kvstore,
                        /* @NonNull */ Object locking_object) {
    myKVStore = kvstore;
    myLockingObject = locking_object;
  }

  @Override
  public void placeBid(/* @NonNull */ String item_name,
                          /* @NonNull */ String bidder,
                          int new_price) {
    synchronized (myLockingObject) {  // only one thread can do an update.
      CompleteAuctionStatusImpl current_status = myKVStore.get(item_name);
      if (current_status == null) {
        throw new AuctionNotFoundError("No auction found for " + item_name);
      }
      // All the checks are done by withBid().
      myKVStore.set(item_name, current_status.withBid(bidder, new_price));
    }
  }

  @Override
  public BiddingStatus getBiddingStatus(String item_name) {
    CompleteAuctionStatusImpl status = myKVStore.get(item_name);
    if (status == null) {
      return null;  // verily, a nullable type is a Maybe, and this call is like >>=.
    }
    return new BiddingStatusImpl(
        status.getPhase(), status.getLastBidder(), status.getLastBid()
    );
  }

}
