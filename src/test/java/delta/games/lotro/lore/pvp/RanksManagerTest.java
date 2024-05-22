package delta.games.lotro.lore.pvp;

import java.util.HashSet;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test for the ranks manager.
 * @author DAM
 */
public class RanksManagerTest extends TestCase
{
  /**
   * Test the ranks manager.
   */
  public void testRanksManager()
  {
    RanksManager ranksMgr=RanksManager.getInstance();
    for(String key : RankScaleKeys.getKeys())
    {
      RankScale scale=ranksMgr.getRankScale(key);
      Assert.assertNotNull(scale);
      showScale(scale);
    }
  }

  /**
   * Test keys.
   */
  public void testKeys()
  {
    RanksManager ranksMgr=RanksManager.getInstance();
    Assert.assertEquals(new HashSet<String>(RankScaleKeys.getKeys()),new HashSet<String>(ranksMgr.getKeys()));
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
