package delta.games.lotro.lore.pvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

/**
 * Test for the ranks manager.
 * @author DAM
 */
class RanksManagerTest
{
  /**
   * Test the ranks manager.
   */
  @Test
  void testRanksManager()
  {
    RanksManager ranksMgr=RanksManager.getInstance();
    for(String key : RankScaleKeys.getKeys())
    {
      RankScale scale=ranksMgr.getRankScale(key);
      assertNotNull(scale);
      showScale(scale);
    }
  }

  /**
   * Test keys.
   */
  @Test
  void testKeys()
  {
    RanksManager ranksMgr=RanksManager.getInstance();
    assertEquals(new HashSet<String>(RankScaleKeys.getKeys()),new HashSet<String>(ranksMgr.getKeys()));
  }

  private void showScale(RankScale scale)
  {
    System.out.println("key: "+scale.getKey());
    for(RankScaleEntry entry : scale.getEntries())
    {
      System.out.println("\t"+entry);
    }
  }
}
