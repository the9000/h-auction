package hackerati.task;

/**
 * Allows a bidder to bid on auctions..
 */
public interface BiddingEngine {

  /**
   * Places a bid.
   * @param item_name the name of the item / auction.
   * @param bidder name of the bidder
   * @param new_bid amount of the new bid, must be above current bid + threshold.
   * @throws InvalidBidError if auction phase is not ACTIVE or new bid is too low.
   *
   * Note: I would rather return a value than throw exceptions, but in Java
   * it's too easy to ignore a returned value.
   */
  void placeBid(/* @NonNull */ String item_name,
                /* @NonNull */ String bidder,
                int new_bid);
}
