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

  /**
   * This constructor sets buyer name to last bidder name
   * iff the phase is CALLED, else it sets buyer to null.
   *
   * @param phase phase to set.
   * @param last_bidder last bidder to calculate buyer from.
   * @param last_bid last bid value.
   */
  public BiddingStatusImpl(/* @NonNull */  AuctionPhase phase,
                           /* @Nullable */ String last_bidder,
                           /* @Nullable */ Integer last_bid)
  {
    final String buyer;
    if (phase == AuctionPhase.CALLED) {
      buyer = last_bidder;
    } else {
      buyer = null;
    }
    assert ((last_bidder == null) || (last_bid != null)) : "If setting buyer, set the bid, too.";
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

  static BiddingStatus fromAuctionStatusImpl(CompleteAuctionStatusImpl status) {
    if (status == null) {
      return null;  // verily, a nullable type is a Maybe, and this call is like >>=.
    }
    return new BiddingStatusImpl(
        status.getPhase(), status.getLastBidder(), status.getLastBid()
    );

  }
}
