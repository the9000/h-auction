package hackerati.task;

/**
 * Thrown when an auction was not found but should have been.
 */
public class AuctionNotFoundError extends AuctionException {
  public AuctionNotFoundError(String item_name) {
    super(item_name);
  }
}
