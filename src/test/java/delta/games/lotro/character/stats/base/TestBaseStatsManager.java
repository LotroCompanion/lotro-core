package delta.games.lotro.character.stats.base;

import java.util.List;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.classes.WellKnownCharacterClassKeys;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.character.stats.contribs.StatsContribution;

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
    RaceDescription elf=RacesManager.getInstance().getByKey("elf");
    ClassDescription warden=ClassesManager.getInstance().getCharacterClassByKey(WellKnownCharacterClassKeys.WARDEN);
    List<StatsContribution> baseStatsContribs = mgr.getBaseStats(warden, elf, 1);
    System.out.println(baseStatsContribs);
  }
}
