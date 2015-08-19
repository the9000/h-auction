package hackerati.task.impl;

import hackerati.task.AuctionPhase;
import hackerati.task.BiddingStatus;
import hackerati.task.InvalidBidError;

/**
 * An immutable container for status.
 */
class BiddingStatusImpl implements BiddingStatus {

  private final AuctionPhase myPhase;
  private final String myBuyer;
  private final Integer myLastBid;

  public BiddingStatusImpl(/* @NonNull */  AuctionPhase phase,
                           /* @Nullable */ String buyer,
                           /* @Nullable */ Integer last_bid)
  {
    if (phase != AuctionPhase.CALLED) {
      assert last_bid == null : "Cannot set sale price if myPhase is " + phase;
      assert buyer == null : "Cannot set buyer if myPhase is " + phase;
    }
    assert ((buyer == null) || (last_bid != null)) : "If setting buyer, set the bid, too.";
    this.myPhase = phase;
    this.myBuyer = buyer;
    this.myLastBid = last_bid;
  }

  @Override
  public AuctionPhase getPhase() { return myPhase; }

  @Override
  public Integer getLastBid() { return myLastBid; }

  @Override
  public String getBuyer() { return myBuyer; }

}
