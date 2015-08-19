package hackerati.task;

/**
 * Thrown on invalid auction myPhase transition attempts.
 */
public class InvalidPhaseTransitionError extends AuctionException {
  public InvalidPhaseTransitionError(String message) {
    super(message);
  }
}
