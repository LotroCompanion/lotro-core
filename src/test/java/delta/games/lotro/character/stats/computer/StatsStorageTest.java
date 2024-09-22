package delta.games.lotro.character.stats.computer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.common.stats.StatOperator;
import delta.games.lotro.common.stats.WellKnownStat;

/**
 * Test class for stats storage.
 * @author DAM
 */
class StatsStorageTest
{
  /**
   * Test.
   */
  @Test
  void test()
  {
    StatsStorage stats=build();
    BasicStatsSet result=stats.aggregate();
    System.out.println("Result: "+result);
    assertEquals(Integer.valueOf(3),result.getStat(WellKnownStat.MIGHT));
    assertEquals(Float.valueOf(231),result.getStat(WellKnownStat.MORALE));
    assertEquals(Integer.valueOf(100),result.getStat(WellKnownStat.AGILITY));
  }

  private StatsStorage build()
  {
    StatsStorage ret=new StatsStorage();
    StatsContribution contrib1=buildContrib1();
    ret.addContrib(contrib1);
    StatsContribution contrib2=buildContrib2();
    ret.addContrib(contrib2);
    StatsContribution contrib3=buildContrib3();
    ret.addContrib(contrib3);
    return ret;
  }

  private StatsContribution buildContrib1()
  {
    BasicStatsSet stats=new BasicStatsSet();
    stats.setStat(WellKnownStat.MIGHT,100);
    stats.setStat(WellKnownStat.MORALE,200);
    StatsContribution ret=new StatsContribution("ID1","Label1",stats);
    return ret;
  }

  private StatsContribution buildContrib2()
  {
    BasicStatsSet stats=new BasicStatsSet();
    stats.setStat(WellKnownStat.AGILITY,100);
    stats.setStat(WellKnownStat.MORALE,StatOperator.MULTIPLY,Float.valueOf(1.10f),null);
    StatsContribution ret=new StatsContribution("ID2","Label2",stats);
    return ret;
  }

  private StatsContribution buildContrib3()
  {
    BasicStatsSet stats=new BasicStatsSet();
    stats.setStat(WellKnownStat.MIGHT,StatOperator.SET,Float.valueOf(3.14f),null);
    stats.setStat(WellKnownStat.MORALE,StatOperator.MULTIPLY,Float.valueOf(1.05f),null);
    StatsContribution ret=new StatsContribution("ID3","Label3",stats);
    return ret;
  }
}
