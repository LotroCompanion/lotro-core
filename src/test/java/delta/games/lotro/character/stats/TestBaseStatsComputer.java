package delta.games.lotro.character.stats;

import delta.games.lotro.character.CharacterStat.STAT;
import delta.games.lotro.character.stats.base.BaseStatsManager;
import delta.games.lotro.character.stats.base.DerivatedStatsContributionsMgr;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.utils.FixedDecimalsInteger;

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
    DerivatedStatsContributionsMgr mgr=new DerivatedStatsContributionsMgr();
    // Test
    BasicStatsSet stats=new BasicStatsSet();
    stats.addStat(STAT.MIGHT,new FixedDecimalsInteger(100));
    stats.addStat(STAT.FATE,new FixedDecimalsInteger(10));

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
