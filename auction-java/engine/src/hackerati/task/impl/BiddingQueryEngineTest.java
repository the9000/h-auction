package hackerati.task.impl;


import hackerati.task.AdminEngine;
import hackerati.task.BiddingEngine;
import hackerati.task.BiddingQueryEngine;
import hackerati.task.BiddingStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test query engine.
 */
public class BiddingQueryEngineTest {

  private KVStoreImpl myKVStore;
  private static Object ourLock = new Object();
  private BiddingQueryEngine myEngine;
  private AdminEngine myAdminEngine;
  private BiddingEngine myBiddingEngine;

  @Before
  public void setUp() {
    myKVStore = new KVStoreImpl();
    BidderTerminal terminal = new BidderTerminal(myKVStore, ourLock);
    myEngine = terminal;
    myBiddingEngine = terminal;
    myAdminEngine = new AdminTerminal(myKVStore, ourLock);
  }

  @Test
  public void testQueryForUnlistedItemReturnsNull() {
    BiddingStatus status = myEngine.getBiddingStatus("foo");
    assertNull(status);
  }

  @Test
  public void testQueryForListedDormantItemReturnsNullBid() {
    myAdminEngine.enlistItem("foo", 100);
    BiddingStatus status = myEngine.getBiddingStatus("foo");
    assertNull(status.getLastBid());
  }

  @Test
  public void testQueryForListedActiveItemReturnsLastBidButNotBuyer() {
    myAdminEngine.enlistItem("foo", 100);
    myAdminEngine.startAuction("foo");
    myBiddingEngine.placeBid("foo", "bidder", 50);
    BiddingStatus status = myEngine.getBiddingStatus("foo");
    assertEquals(new Integer(50), status.getLastBid());
    assertNull(status.getBuyer());
  }

  @Test
  public void testQueryForListedCalledItemReturnsBuyer() {
    myAdminEngine.enlistItem("foo", 100);
    myAdminEngine.startAuction("foo");
    myBiddingEngine.placeBid("foo", "bidder", 110);
    myAdminEngine.callAuction("foo");
    BiddingStatus status = myEngine.getBiddingStatus("foo");
    assertEquals("bidder", status.getBuyer());
  }


}
