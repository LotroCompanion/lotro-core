package delta.games.lotro.character.stats.base;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.stats.WellKnownStat;

/**
 * Test base stats computer.
 * @author DAM
 */
public class TestBaseStatsComputer
{
  /**
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    DerivedStatsContributionsMgr mgr=new DerivedStatsContributionsMgr();
    // Test
    BasicStatsSet stats=new BasicStatsSet();
    stats.addStat(WellKnownStat.MIGHT,Integer.valueOf(100));
    stats.addStat(WellKnownStat.FATE,Integer.valueOf(10));

    BaseStatsManager mgr2 = new BaseStatsManager();
    for(CharacterClass cClass : CharacterClass.ALL_CLASSES)
    {
      BasicStatsSet set = mgr2.getBaseStats(cClass, Race.MAN, 9);
      System.out.println(set);
      BasicStatsSet contrib=mgr.getContribution(cClass,set);
      System.out.println(contrib);
      set.addStats(contrib);
      System.out.println(set);
    }
  }
}
