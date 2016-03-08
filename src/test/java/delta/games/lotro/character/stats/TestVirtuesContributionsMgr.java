package delta.games.lotro.character.stats;

import delta.games.lotro.character.stats.virtues.VirtuesContributionsMgr;
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
    VirtuesContributionsMgr mgr=new VirtuesContributionsMgr();
    for(int i=1;i<=19;i++)
    {
      BasicStatsSet stats=mgr.getContribution(VirtueId.IDEALISM, i);
      System.out.println("Rank #" + i + ": " + stats);
    }
  }
}
