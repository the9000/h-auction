package hackerati.task;

/**
 * Admin's view of auction state.
 * Provides state information not accessible to simple bidders,
 *
 *
 * The view is immutable.
 * All mutator-like methods return new instances, and check the correctness
 * of the operations.
 */
public interface AdminAuctionStatus extends BiddingStatus {

  /**
   * @return last bidder name, or null.
   */
  String getLastBidder();

  /**
   * Used to start bidding on the auction / item.
   * @return a new status with myPhase set to ACTIVE.
   * @throws InvalidPhaseTransitionError if myPhase is not DORMANT.
   */
  AdminAuctionStatus start();

  /**
   * Used to call (end) the auction.
   * @return a new status with with myPhase set to CALLED.
   * @throws InvalidPhaseTransitionError if myPhase is not ACTIVE.
   */
  AdminAuctionStatus call();

  /**
   * @return true iff the last bid price is above the reserved price.
   */
  boolean isSuccess();

}
