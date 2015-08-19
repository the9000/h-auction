package hackerati.task;

/**
 * Allows to query the sale status of an auction / item.
 *
 * The problem statement makes it accessible to both bidders and admins,
 * so it's a separate interface.
 */
public interface BiddingQueryEngine {

  BiddingStatus getBiddingStatus(/* @NonNull */ String item_name);
}
