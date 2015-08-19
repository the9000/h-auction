package hackerati.task;

/**
 * Phases of an auction.
 */
public enum AuctionPhase {
  /**
   * Registered but the bidding has not yet started.
   */
  DORMANT,

  /**
   * Biddig is allowed.
   */
  ACTIVE,

  /**
   * Bidding ended.
   */
  CALLED,
}
