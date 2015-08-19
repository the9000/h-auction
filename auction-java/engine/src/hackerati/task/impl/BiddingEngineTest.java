package hackerati.task.impl;

import hackerati.task.*;
import org.junit.Test;
import org.junit.Before;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Check the sanity of  BiddingEngine implementation.
 */
public class BiddingEngineTest {

  private KVStoreImpl myKVStore;
  private static Object ourLock = new Object();
  private BiddingEngine myEngine;
  private AdminEngine myAdminEngine;

  @Before
  public void setUp() {
    myKVStore = new KVStoreImpl();
    myEngine = new BidderTerminal(myKVStore, ourLock);
    myAdminEngine = new AdminTerminal(myKVStore, ourLock);
  }

  private void createActiveItem(String item_name) {
    myAdminEngine.enlistItem(item_name, 50);
    myAdminEngine.startAuction(item_name);
  }

  @Test
  public void testPlaceNewBidWork() {
    String item_name = "item";
    createActiveItem(item_name);
    myEngine.placeBid(item_name, "foo", 100);
    CompleteAuctionStatusImpl status = myKVStore.get(item_name);
    assertNotNull(status);
    assertEquals(new Integer(100), status.getLastBid());
  }

  @Test
  public void testSequentialGrowingBidsWork() {
    String item_name = "item";
    createActiveItem(item_name);
    myEngine.placeBid(item_name, "foo", 100);
    myEngine.placeBid(item_name, "bar", 120);
  }

  @Test(expected = InvalidBidError.class)
  public void testBidBelowLastBidFails() {
    String item_name = "item";
    createActiveItem(item_name);
    myEngine.placeBid(item_name, "foo", 100);
    myEngine.placeBid(item_name, "bar", 90);
  }

  @Test(expected = InvalidBidError.class)
  public void testBidBeforStartFails() {
    String item_name = "item";
    myAdminEngine.enlistItem(item_name, 50);
    myEngine.placeBid(item_name, "foo", 100);
  }

  @Test(expected = InvalidBidError.class)
  public void testBidAfterCallFails() {
    String item_name = "item";
    createActiveItem(item_name);
    myAdminEngine.callAuction(item_name);
    myEngine.placeBid(item_name, "foo", 100);
  }

  @Test(expected = InvalidBidError.class)
  public void testBidEqualToLastBidFails() {
    String item_name = "item";
    createActiveItem(item_name);
    myEngine.placeBid(item_name, "foo", 100);
    myEngine.placeBid(item_name, "bar", 100);
  }

  @Test(expected = AuctionNotFoundError.class)
  public void testBidForNonExistentItemFails() {
    createActiveItem("moon");
    myEngine.placeBid("sun", "foo", 100);
  }

}
