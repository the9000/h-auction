package hackerati.task;

/**
 * Bidder-facing auction state interface.
 *
 * Bid amount is an integer, probably cents or some smaller subdivision.
 * Using floating point for monetary values is a bad, error-prone idea,
 * and using a Decimal is excessively cumbersome since we only need
 * a fixed-point number.
 *
 * The conversion factor and any currency considerations are explicitly
 * not handled here. In a real case, they would be.
 */
public interface BiddingStatus {

  /**
   * A new bid should be above the current bid at least by this amount.
   * It could be a per-auction setting, though in real life it rarely varies.
   */
  int MINIMAL_INCREASE = 1;

  /**
   * @return the phase of the auction.
   */
  /* @NonNull */
  AuctionPhase getPhase();

  /**
   * Gets the last bid amount.
   *
   * The problem definition asked for a more limited thing, the sale price.
   * But bidding cannot be reasonably done if the latest bid amount is not known.
   * So providing the last bid amount here is the most natural extension.
   *
   * @return last bid price, or null.
   */
  /* @Nullable */
  Integer getLastBid();

  /**
   * @return buyer name if the item has been sold (phase is CALLED), or null.
   */
  /* @Nullable */
  String getBuyer();

}
