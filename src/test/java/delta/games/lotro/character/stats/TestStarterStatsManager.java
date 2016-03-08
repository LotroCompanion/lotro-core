package delta.games.lotro.character.stats;

import delta.games.lotro.character.stats.base.StarterStatsManager;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

/**
 * Test class for the starter stats manager.
 * @author DAM
 */
public class TestStarterStatsManager
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    StarterStatsManager mgr=StarterStatsManager.build();
    BasicStatsSet set=mgr.getStartingStats(Race.ELF,CharacterClass.WARDEN);
    System.out.println(set);
  }
}
