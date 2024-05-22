package delta.games.lotro.lore.pvp;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test class for the rank scale.
 * @author DAM
 */
public class RankScaleTest extends TestCase
{
  /**
   * Test rank computation.
   */
  public void testRankComputation()
  {
    RankScale renown=RanksManager.getInstance().getRankScale(RankScaleKeys.RENOWN);
    Assert.assertNotNull(renown);
    // Not found
    Rank rank=renown.getRank(-1);
    Assert.assertNull(rank);
    // Zero
    rank=renown.getRank(0);
    Assert.assertNotNull(rank);
    Assert.assertEquals(renown.getEntries().get(0).getRank(),rank);
    // Just before rank 1
    int value=renown.getEntries().get(1).getValue()-1;
    rank=renown.getRank(value);
    Assert.assertNotNull(rank);
    Assert.assertEquals(renown.getEntries().get(0).getRank(),rank);
    value++;
    rank=renown.getRank(value);
    Assert.assertNotNull(rank);
    Assert.assertEquals(renown.getEntries().get(1).getRank(),rank);
    // Last
    int nbRanks=renown.getEntries().size();
    rank=renown.getRank(10000000);
    Assert.assertNotNull(rank);
    Assert.assertEquals(renown.getEntries().get(nbRanks-1).getRank(),rank);
  }
}
