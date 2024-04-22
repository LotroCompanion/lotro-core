package delta.games.lotro.character.stats.base;

import java.util.List;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.DerivedStatContributionsIO;
import delta.games.lotro.character.stats.computer.StatsStorage;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.common.stats.WellKnownStat;

/**
 * Test base stats computer.
 * @author DAM
 */
public class MainTestBaseStatsComputer
{
  /**
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    DerivedStatsContributionsMgr mgr=DerivedStatContributionsIO.load();
    // Test
    BasicStatsSet stats=new BasicStatsSet();
    stats.addStat(WellKnownStat.MIGHT,Integer.valueOf(100));
    stats.addStat(WellKnownStat.FATE,Integer.valueOf(10));

    BaseStatsManager mgr2 = new BaseStatsManager();
    RaceDescription man=RacesManager.getInstance().getByKey("man");
    for(ClassDescription characterClass : ClassesManager.getInstance().getAllCharacterClasses())
    {
      System.out.println("Class: "+characterClass.getName());
      List<StatsContribution> baseStatsContribs = mgr2.getBaseStats(characterClass, man, 9);
      System.out.println(baseStatsContribs);
      StatsStorage storage=new StatsStorage();
      for(StatsContribution contrib : baseStatsContribs)
      {
        storage.addContrib(contrib);
      }
      BasicStatsSet baseStats=storage.aggregate();
      BasicStatsSet contrib=mgr.getContribution(characterClass,baseStats);
      System.out.println(contrib);
      baseStats.addStats(contrib);
      System.out.println(baseStats);
    }
  }
}
