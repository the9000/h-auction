package hackerati.task.impl;


import hackerati.task.AdminAuctionStatus;
import hackerati.task.AuctionPhase;
import hackerati.task.InvalidBidError;
import hackerati.task.InvalidPhaseTransitionError;

/**
 * Item auction data, an immutable POJO.
 * Not visible beyond impl.
 */
class CompleteAuctionStatusImpl implements AdminAuctionStatus {

  private final AuctionPhase myPhase;
  private final int myReservedPrice;
  private final String myLastBidder;
  private final Integer myLastBid;

  protected CompleteAuctionStatusImpl(AuctionPhase phase, int reserved_price, String last_bidder, Integer last_bid) {
    this.myPhase = phase;
    this.myReservedPrice = reserved_price;
    this.myLastBidder = last_bidder;
    this.myLastBid = last_bid;
  }

  @Override
  public String getLastBidder() { return myLastBidder; }

  @Override
  public boolean isSuccess() {
    return (myLastBid != null) && myLastBid > myReservedPrice;
  }

  @Override
  public AuctionPhase getPhase() { return myPhase; }

  @Override
  public Integer getLastBid() { return myLastBid; }

  @Override
  public String getBuyer() {
    return (myPhase == AuctionPhase.CALLED)? myLastBidder : null;
  }

  @Override
  public CompleteAuctionStatusImpl start() {
    if (myPhase == AuctionPhase.DORMANT) {
      return new CompleteAuctionStatusImpl(
          AuctionPhase.ACTIVE, myReservedPrice, myLastBidder, myLastBid);
    }
    throw new InvalidPhaseTransitionError("Cannot start, phase is " + myPhase);
  }

  @Override
  public CompleteAuctionStatusImpl call() {
    if (myPhase == AuctionPhase.ACTIVE) {
      return new CompleteAuctionStatusImpl(
          AuctionPhase.CALLED, myReservedPrice, myLastBidder, myLastBid);
    }
    throw new InvalidPhaseTransitionError("Cannot call, phase is " + myPhase);
  }

  public CompleteAuctionStatusImpl withBid(/* @NonNull */ String new_bidder, int new_bid) {
    if (myPhase != AuctionPhase.ACTIVE) {
      throw new InvalidBidError("Cannot bid, phase is " + myPhase);
    }
    if (new_bid - myLastBid < MINIMAL_INCREASE) {
      throw new InvalidBidError("Amount is too low for a new bid.");
    }
    return new CompleteAuctionStatusImpl(
        myPhase, myReservedPrice, new_bidder, new_bid);
  }


}
