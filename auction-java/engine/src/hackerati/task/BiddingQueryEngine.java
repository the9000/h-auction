package hackerati.task;

/**
 * Allows to query the sale status of an auction / item.
 *
 * The problem statement makes it accessible to both bidders and admins,
 * so it's a separate interface.
 *
 * Both admin and bidder code should be built against this interface.
 */
public interface BiddingQueryEngine {

  BiddingStatus getBiddingStatus(/* @NonNull */ String item_name);
}
