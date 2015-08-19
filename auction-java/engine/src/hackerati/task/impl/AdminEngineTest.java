package hackerati.task.impl;

import hackerati.task.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Check sanity of admin engine. Other tests make sense if this test passes.
 */
public class AdminEngineTest {

  private KVStoreImpl myKVStore;
  private static Object ourLock = new Object();
  private AdminEngine myAdminEngine;

  @Before
  public void setUp() {
    myKVStore = new KVStoreImpl();
    myAdminEngine = new AdminTerminal(myKVStore, ourLock);
  }

  @Test
  public void testEnlistItemWorks() {
    myAdminEngine.enlistItem("foo", 100);
    CompleteAuctionStatusImpl status = myKVStore.get("foo");
    assertEquals(AuctionPhase.DORMANT, status.getPhase());
    assertNull(status.getLastBidder());
    assertNull(status.getLastBid());
  }

  @Test
  public void testEnlistTwoSeparateItemsWorks() {
    myAdminEngine.enlistItem("foo", 100);
    myAdminEngine.enlistItem("bar", 100);
    assertNotNull(myKVStore.get("foo"));
    assertNotNull(myKVStore.get("bar"));
  }
  @Test(expected = InvalidPhaseTransitionError.class)
  public void testEnlistItemTwiceFails() {
    myAdminEngine.enlistItem("foo", 100);
    myAdminEngine.enlistItem("foo", 110);
  }

  @Test
  public void testStartAuctionWorks() {
    myAdminEngine.enlistItem("foo", 100);
    myAdminEngine.startAuction("foo");
    CompleteAuctionStatusImpl status = myKVStore.get("foo");
    assertEquals(AuctionPhase.ACTIVE, status.getPhase());
    assertNull(status.getLastBidder());
    assertNull(status.getLastBid());
  }

  @Test(expected = InvalidPhaseTransitionError.class)
  public void testStartAuctionTwiceFails() {
    myAdminEngine.enlistItem("foo", 100);
    myAdminEngine.startAuction("foo");
    myAdminEngine.startAuction("foo");
  }

  @Test
  public void testCallAuctionWorks() {
    myAdminEngine.enlistItem("foo", 100);
    myAdminEngine.startAuction("foo");
    myAdminEngine.callAuction("foo");
    CompleteAuctionStatusImpl status = myKVStore.get("foo");
    assertEquals(AuctionPhase.CALLED, status.getPhase());
  }

  @Test(expected = InvalidPhaseTransitionError.class)
  public void testCallAuctionTwiceFails() {
    myAdminEngine.enlistItem("foo", 100);
    myAdminEngine.startAuction("foo");
    myAdminEngine.callAuction("foo");
    myAdminEngine.callAuction("foo");
  }
}
