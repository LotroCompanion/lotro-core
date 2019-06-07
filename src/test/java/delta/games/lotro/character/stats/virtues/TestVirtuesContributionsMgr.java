package delta.games.lotro.character.stats.virtues;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.VirtueId;

/**
 * Test class for the virtues contributions manager.
 * @author DAM
 */
public class TestVirtuesContributionsMgr
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    VirtuesContributionsMgr mgr=VirtuesContributionsMgr.get();
    for(VirtueId id : VirtueId.values())
    {
      System.out.println(id);
      for(int i=0;i<=20;i++)
      {
        BasicStatsSet stats=mgr.getContribution(id, i, false);
        System.out.println("Rank #" + i + ": " + stats);
      }
    }
  }
}
