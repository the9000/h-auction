package hackerati.task;

/**
 * Thrown on invalid auction phase transition attempts.
 */
public class InvalidPhaseTransitionError extends AuctionException {
  public InvalidPhaseTransitionError(String message) {
    super(message);
  }
}
