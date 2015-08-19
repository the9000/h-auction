package hackerati.task;

/**
 * Thrown on an invalid bidding attempt.
 */
public class InvalidBidError extends AuctionException {
  public InvalidBidError(String message) {
    super(message);
  }
}
