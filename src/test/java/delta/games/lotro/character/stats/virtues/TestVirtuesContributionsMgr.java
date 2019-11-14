package delta.games.lotro.character.stats.virtues;

import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;

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
    List<VirtueDescription> virtues=VirtuesManager.getInstance().getAll();
    for(VirtueDescription virtue : virtues)
    {
      System.out.println(virtue.getName());
      for(int i=0;i<=20;i++)
      {
        BasicStatsSet stats=mgr.getContribution(virtue, i, false);
        System.out.println("Rank #" + i + ": " + stats);
      }
    }
  }
}
