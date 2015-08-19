package hackerati.task.impl;

import hackerati.task.AdminEngine;
import hackerati.task.AuctionPhase;
import hackerati.task.BiddingEngine;
import org.junit.Test;
import org.junit.Before;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Check the sanity of  BiddingEngine implementation.
 */
public class BidderEngineTest {

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

  @Test
  public void testPlaceNewBidWork() {
    String item_name = "item";
    myAdminEngine.enlistItem(item_name, 50);
    myAdminEngine.startAuction(item_name);
    myEngine.placeBid(item_name, "bidder", 100);
    CompleteAuctionStatusImpl status = myKVStore.get(item_name);
    assertNotNull(status);
    assertEquals(new Integer(100), status.getLastBid());
  }
}
