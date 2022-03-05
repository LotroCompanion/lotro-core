package delta.games.lotro.character.status.achievables;

import java.util.List;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.status.achievables.io.DeedsStatusIo;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.geo.AchievableGeoPoint;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;

/**
 * Test class to show the deeds status for a single toon.
 * @author DAM
 */
public class MainTestDeedGeoDataStatus
{
  private void doIt()
  {
    CharactersManager charsMgr=CharactersManager.getInstance();
    CharacterFile toon=charsMgr.getToonById("Landroval","Glumlug");
    AchievablesStatusManager deedsStatusMgr=DeedsStatusIo.load(toon);
    List<AchievableStatus> deedStatuses=deedsStatusMgr.getAll();
    for(AchievableStatus deedStatus : deedStatuses)
    {
      AchievableElementState state=deedStatus.getState();
      if (state==AchievableElementState.UNDERWAY)
      {
        Achievable achievable=deedStatus.getAchievable();
        if (achievable instanceof DeedDescription)
        {
          handleDeed(deedStatus,(DeedDescription)achievable);
        }
      }
    }
  }

  private void handleDeed(AchievableStatus status, DeedDescription deed)
  {
    boolean hasGeoData=status.getAchievable().hasGeoData();
    if (!hasGeoData)
    {
      return;
    }
    System.out.println("Deed: ID="+deed.getIdentifier()+", name="+deed.getName());
    ObjectivesManager objectivesMgr=deed.getObjectives();
    List<Objective> objectives=objectivesMgr.getObjectives();
    for(Objective objective : objectives)
    {
     AchievableObjectiveStatus objectiveStatus=status.getObjectiveStatus(objective.getIndex());
     AchievableElementState objectiveState=objectiveStatus.getState();
     int objectiveIndex=objective.getIndex();
     System.out.println("\tObjective index="+objectiveIndex+" - "+objectiveState);
     if (objectiveState==AchievableElementState.UNDERWAY)
     {
       handleDeedObjective(objectiveStatus,deed,objective);
     }
    }
  }

  private void handleDeedObjective(AchievableObjectiveStatus objectiveStatus, DeedDescription deed, Objective objective)
  {
    List<ObjectiveConditionStatus> conditionStatuses=objectiveStatus.getConditionStatuses();
    for(ObjectiveConditionStatus conditionStatus : conditionStatuses)
    {
      int conditionIndex=conditionStatus.getCondition().getIndex();
      AchievableElementState state=conditionStatus.getState();
      System.out.println("\t\tCondition index="+conditionIndex+" - "+state);
      if (state==AchievableElementState.COMPLETED)
      {
        continue;
      }
      List<String> keys=conditionStatus.getKeys();
      if (keys!=null)
      {
        System.out.println("\t\tCondition index="+conditionIndex+" - keys="+keys);
      }
      ObjectiveCondition condition=conditionStatus.getCondition();
      List<AchievableGeoPoint> items=condition.getPoints();
      if (items.size()>0)
      {
        System.out.println("\t\t\tGeo data:");
        for(AchievableGeoPoint item : items)
        {
          System.out.println("\t\t\t\t"+item);
        }
      }
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestDeedGeoDataStatus().doIt();
  }
}
