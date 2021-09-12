package delta.games.lotro.character.status.skirmishes;

import java.io.File;

import junit.framework.Assert;
import junit.framework.TestCase;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.status.skirmishes.io.xml.SkirmishStatsXMLWriter;
import delta.games.lotro.common.groupSize.GroupSize;
import delta.games.lotro.common.groupSize.GroupSizesManager;
import delta.games.lotro.lore.instances.PrivateEncounter;
import delta.games.lotro.lore.instances.PrivateEncountersManager;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;

/**
 * Test class for skirmish stats.
 * @author DAM
 */
public class TestSkirmishStats extends TestCase
{
  /**
   * Test the skirmish stats IO.
   */
  public void testSkirmishStatsIO()
  {
    SkirmishStatsManager stats=buildStats();
    SkirmishStatsXMLWriter writer=new SkirmishStatsXMLWriter();
    File out=new File("skirmishStats.xml");
    boolean ok=writer.write(out,stats,EncodingNames.UTF_8);
    Assert.assertTrue(ok);
  }

  private SkirmishStatsManager buildStats()
  {
    SkirmishStatsManager ret=new SkirmishStatsManager();
    PrivateEncountersManager mgr=PrivateEncountersManager.getInstance();
    for(PrivateEncounter pe : mgr.getPrivateEncounters())
    {
      if (pe instanceof SkirmishPrivateEncounter)
      {
        SkirmishPrivateEncounter skirmish=(SkirmishPrivateEncounter)pe;
        SingleSkirmishStats stats=buildSingleSkirmishStats(skirmish);
        ret.add(stats);
      }
    }
    return ret;
  }

  private SingleSkirmishStats buildSingleSkirmishStats(SkirmishPrivateEncounter skirmish)
  {
    SingleSkirmishStats ret=new SingleSkirmishStats(skirmish);
    for(GroupSize size : GroupSizesManager.getInstance().getAll())
    {
      for(SkirmishLevel level : SkirmishLevel.values())
      {
        ret.setStats(size,level,buildSkirmishStats());
      }
    }
    return ret;
  }
  private SkirmishStats buildSkirmishStats()
  {
    SkirmishStats ret=new SkirmishStats();
    ret.setMonsterKills(1);
    ret.setLieutenantKills(2);
    ret.setControlPointsTaken(3);
    ret.setEncountersCompleted(4);
    ret.setPlayTime(123.45f);
    ret.setSkirmishesCompleted(5);
    ret.setSkirmishesAttempted(6);
    ret.setBestTime(67.89f);
    ret.setTotalMarksEarned(7);
    return ret;
  }
}
