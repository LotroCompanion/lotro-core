package delta.games.lotro.lore.quests;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.CharacterSummary;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.requirements.filters.UsageRequirementFilter;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;

/**
 * Utility methods related to achievables.
 * @author DAM
 */
public class AchievablesUtils
{
  /**
   * Get the deeds for the given character.
   * @param summary Character summary.
   * @return A list of deeds.
   */
  public static List<DeedDescription> getDeeds(CharacterSummary summary)
  {
    List<DeedDescription> deeds=DeedsManager.getInstance().getAll();
    return filter(deeds,summary);
  }

  /**
   * Get the quests for the given character.
   * @param summary Character summary.
   * @return A list of quests.
   */
  public static List<QuestDescription> getQuests(CharacterSummary summary)
  {
    List<QuestDescription> quests=QuestsManager.getInstance().getAll();
    return filter(quests,summary);
  }

  private static <T extends Achievable> List<T> filter(List<T> achievables, CharacterSummary summary)
  {
    List<T> ret=new ArrayList<T>();
    CharacterClass characterClass=summary.getCharacterClass();
    Race race=summary.getRace();
    UsageRequirementFilter classRaceFilter=new UsageRequirementFilter(characterClass,race);
    for(T achievable : achievables)
    {
      if (classRaceFilter.accept(achievable.getUsageRequirement()))
      {
        ret.add(achievable);
      }
    }
    return ret;
  }
}
