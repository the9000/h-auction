package hackerati.task;

/**
 * Available for auction administrators.
 *
 * Auction administrator code should be built against this interface.
 */
public interface AdminEngine {
  /**
   * Put the item into the auction system in DORMANT state.
   *
   * Note that we don't plan to ever unlist anything.
   *
   * @param item_name Uniquely identifies the item.
   * @param reserved_price Bidding at or above this price makes
   *                       the auction successful.
   * @throws InvalidPhaseTransitionError if the item is already listed.
   */
  void enlistItem(/* @NonNull */ String item_name, int reserved_price);

  /**
   * Puts the auction from DORMANT to ACTIVE state.
   * Bidding on this item begins.
   * @param item_name Uniquely identifies the item.
   * @throws InvalidPhaseTransitionError if auction is not in DORMANT phase.
   */
  void startAuction(/* @NonNull */ String item_name);

  /**
   * Puts the auction from ACTIVE to CALLED state.
   * Bidding stops, the highest bidder is considered the winner.
   * @param item_name Uniquely identifies the item.
   * @throws InvalidPhaseTransitionError if auction is not in ACTIVE phase.
   */
  void callAuction(/* @NonNull */ String item_name);


}
