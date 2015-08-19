package hackerati.task;

/**
 * Available for auction administrators.
 *
 * Accessible to auction admins.
 */
public interface AdminEngine {
  /**
   * Put the item into the auction system in DORMANT state.
   * @param item_name Uniquely identifies the item.
   * @param reserved_price Bidding at or above this price makes
   *                       the auction successful.
   */
  void enlistItem(/* @NonNull */ String item_name, int reserved_price);

  /**
   * Puts the auction from DORMANT to ACTIVE state.
   * Bidding on this item begins.
   * @param item_name Uniquely identifies the item.
   */
  void startAuction(/* @NonNull */ String item_name);

  /**
   * Puts the auction from ACTIVE to CALLED state.
   * Bidding stops, the highest bidder is considered the winner.
   * @param item_name Uniquely identifies the item.
   */
  void callAuction(/* @NonNull */ String item_name);


}
