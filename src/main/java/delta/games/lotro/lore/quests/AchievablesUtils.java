package delta.games.lotro.lore.quests;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharacterSummary;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.status.crafting.CraftingStatus;
import delta.games.lotro.common.requirements.filters.ProfessionRequirementFilter;
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
   * @param character Character.
   * @return A list of deeds.
   */
  public static List<DeedDescription> getDeeds(CharacterFile character)
  {
    List<DeedDescription> deeds=DeedsManager.getInstance().getAll();
    return filter(deeds,character);
  }

  /**
   * Get the quests for the given character.
   * @param character Character.
   * @return A list of quests.
   */
  public static List<QuestDescription> getQuests(CharacterFile character)
  {
    List<QuestDescription> quests=QuestsManager.getInstance().getAll();
    return filter(quests,character);
  }

  private static <T extends Achievable> List<T> filter(List<T> achievables, CharacterFile character)
  {
    List<T> ret=new ArrayList<T>();
    CharacterSummary summary=character.getSummary();
    ClassDescription characterClass=summary.getCharacterClass();
    RaceDescription race=summary.getRace();
    UsageRequirementFilter classRaceFilter=new UsageRequirementFilter(characterClass,race);
    CraftingStatus status=character.getCraftingMgr().getCraftingStatus();
    ProfessionRequirementFilter craftingFilter=new ProfessionRequirementFilter(status);
    for(T achievable : achievables)
    {
      if (classRaceFilter.accept(achievable.getUsageRequirement()))
      {
        if (craftingFilter.accept(achievable.getUsageRequirement()))
        {
          ret.add(achievable);
        }
      }
    }
    return ret;
  }
}
