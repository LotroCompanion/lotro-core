package delta.games.lotro.lore.pvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Test class for the rank scale.
 * @author DAM
 */
class RankScaleTest
{
  /**
   * Test rank computation.
   */
  @Test
  void testRankComputation()
  {
    RankScale renown=RanksManager.getInstance().getRankScale(RankScaleKeys.RENOWN);
    assertNotNull(renown);
    // Not found
    Rank rank=renown.getRank(-1);
    assertNull(rank);
    // Zero
    rank=renown.getRank(0);
    assertNotNull(rank);
    assertEquals(renown.getEntries().get(0).getRank(),rank);
    // Just before rank 1
    int value=renown.getEntries().get(1).getValue()-1;
    rank=renown.getRank(value);
    assertNotNull(rank);
    assertEquals(renown.getEntries().get(0).getRank(),rank);
    value++;
    rank=renown.getRank(value);
    assertNotNull(rank);
    assertEquals(renown.getEntries().get(1).getRank(),rank);
    // Last
    int nbRanks=renown.getEntries().size();
    rank=renown.getRank(10000000);
    assertNotNull(rank);
    assertEquals(renown.getEntries().get(nbRanks-1).getRank(),rank);
  }
}
