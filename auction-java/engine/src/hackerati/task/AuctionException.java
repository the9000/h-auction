package hackerati.task;

/**
 * Base class for exceptions related to auctions.
 * Serves to make a neater lass hierarchy and allow blanket catch statements.
 */

public abstract class AuctionException extends RuntimeException {
  protected AuctionException(String message) {
    super(message);
  }
}
