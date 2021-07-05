package delta.games.lotro.character.achievables;

import java.util.List;

import delta.games.lotro.character.traitPoints.TraitPoint;
import delta.games.lotro.character.traitPoints.TraitPoints;
import delta.games.lotro.character.traitPoints.TraitPointsRegistry;
import delta.games.lotro.character.traitPoints.TraitPointsStatus;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;

/**
 * Methods to synchronize the trait points status and the quests/deeds status for a single character.
 * @author DAM
 */
public class SyncAchievablesStatusAndTraitPoints
{
  /**
   * Update the trait points status to reflect the given deeds status.
   * @param characterClass Character class to use.
   * @param tpStatus Target trait points status.
   * @param deedsStatus Source deeds status.
   */
  public static void syncTraitPointsFromDeeds(CharacterClass characterClass, TraitPointsStatus tpStatus, AchievablesStatusManager deedsStatus)
  {
    TraitPoints tps=TraitPoints.get();
    TraitPointsRegistry registry=tps.getRegistry();
    List<TraitPoint> traitPoints=registry.getPointsForClass(characterClass);
    DeedsManager deedsManager=DeedsManager.getInstance();
    for(TraitPoint traitPoint : traitPoints)
    {
      int achievableId=traitPoint.getAchievableId();
      if (achievableId==0)
      {
        continue;
      }
      DeedDescription deed=deedsManager.getDeed(achievableId);
      if (deed==null)
      {
        // Not a deed
        continue;
      }
      boolean completed=false;
      AchievableStatus deedStatus=deedsStatus.get(deed,false);
      if (deedStatus!=null)
      {
        AchievableElementState state=deedStatus.getState();
        if (state==AchievableElementState.COMPLETED)
        {
          completed=true;
        }
      }
      tpStatus.setStatus(traitPoint.getId(),completed);
    }
  }

  /**
   * Update the trait points status to reflect the given deeds status.
   * @param characterClass Character class to use.
   * @param tpStatus Target trait points status.
   * @param questsStatus Source deeds status.
   */
  public static void syncTraitPointsFromQuests(CharacterClass characterClass, TraitPointsStatus tpStatus, AchievablesStatusManager questsStatus)
  {
    TraitPoints tps=TraitPoints.get();
    TraitPointsRegistry registry=tps.getRegistry();
    List<TraitPoint> traitPoints=registry.getPointsForClass(characterClass);
    QuestsManager questsManager=QuestsManager.getInstance();
    for(TraitPoint traitPoint : traitPoints)
    {
      int achievableId=traitPoint.getAchievableId();
      if (achievableId==0)
      {
        continue;
      }
      QuestDescription quest=questsManager.getQuest(achievableId);
      if (quest==null)
      {
        // Not a quest
        continue;
      }
      boolean completed=false;
      AchievableStatus questStatus=questsStatus.get(quest,false);
      if (questStatus!=null)
      {
        AchievableElementState state=questStatus.getState();
        if (state==AchievableElementState.COMPLETED)
        {
          completed=true;
        }
      }
      tpStatus.setStatus(traitPoint.getId(),completed);
    }
  }
}
