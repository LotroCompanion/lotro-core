package delta.games.lotro.character.stats;

import delta.games.lotro.character.stats.base.BaseStatsManager;
import delta.games.lotro.character.stats.base.DerivatedStatsContributionsMgr;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

/**
 * Test class for the base stats manager.
 * @author DAM
 */
public class TestBaseStatsManager
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    BaseStatsManager mgr = new BaseStatsManager();
    BasicStatsSet set = mgr.getBaseStats(CharacterClass.WARDEN, Race.ELF, 1);
    DerivatedStatsContributionsMgr derivatedMgr = new DerivatedStatsContributionsMgr();
    BasicStatsSet derivated = derivatedMgr.getContribution(CharacterClass.WARDEN, set);
    set.addStats(derivated);
    System.out.println(set);
    /*
    for(int i=1;i<=100;i++) {
      BasicStatsSet set = mgr.getBaseStats(CharacterClass.CHAMPION, Race.MAN, i);
      System.out.println("#"+i+": "+set);
    }
    */

    /*
    BaseStatsManager statsMgr=new BaseStatsManager();
    StarterStatsManager starterStatsMgr=new StarterStatsManager();
    DerivatedStatsContributionsMgr derivatedMgr=new DerivatedStatsContributionsMgr();
    for(Race race : Race.ALL_RACES)
    {
      for(CharacterClass cClass : CharacterClass.ALL_CLASSES)
      {
        BasicStatsSet statsSet=statsMgr.getBaseStats(cClass,race,1);
        BasicStatsSet contrib=derivatedMgr.getContribution(cClass,statsSet);
        statsSet.addStats(contrib);
        for(CharacterStat stat : statsSet.getAllStats())
        {
          starterStatsMgr.setStat(race,cClass,stat);
        }
      }
    }
    File to2=new File("starter2.xml");
    writer.write(to2,starterStatsMgr,EncodingNames.UTF_8);
    System.out.println("Wrote file " + to2.getAbsolutePath());
    */
  }
}
