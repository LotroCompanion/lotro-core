package delta.games.lotro.character.status.achievables;

import java.util.List;

import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;
import delta.games.lotro.lore.quests.objectives.FactionLevelCondition;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;

/**
 * Assessment tool for the deed progress.
 * @author DAM
 */
public class MainAssessDeedProgress
{
  private void handleDeedDescription(DeedDescription deed)
  {
    boolean counted=handleCount(deed);
    if (counted)
    {
      return;
    }
    boolean factionDeed=isFactionDeed(deed);
    if (factionDeed)
    {
      return;
    }
    boolean multiConditionDeed=isMultiConditionDeed(deed);
    if (multiConditionDeed)
    {
      return;
    }
    /*
    int nbObjectives=deed.getObjectives().getObjectivesCount();
    if (nbObjectives>1)
    {
      //System.out.println("Deed: "+deed.getName());
    }
    */
  }

  private boolean isMultiConditionDeed(DeedDescription deed)
  {
    List<Objective> objectives=deed.getObjectives().getObjectives();
    for(Objective objective : objectives)
    {
      int nbConditions=objective.getConditions().size();
      if (nbConditions>1)
      {
        System.out.println("#2 Deed: "+deed.getName()+" => "+nbConditions);
        return true;
      }
    }
    return false;
  }

  private boolean isFactionDeed(DeedDescription deed)
  {
    int nbFactionLevelCondition=0;
    int nbConditions=0;
    for(Objective objective : deed.getObjectives().getObjectives())
    {
      for(ObjectiveCondition condition : objective.getConditions())
      {
        if (condition instanceof FactionLevelCondition)
        {
          nbFactionLevelCondition++;
        }
        nbConditions++;
      }
    }
    if (nbFactionLevelCondition>0)
    {
      if (nbConditions!=nbFactionLevelCondition)
      {
        //System.out.println("Faction Deed: "+deed.getName()+" => "+nbFactionLevelCondition+"/"+nbConditions);
      }
      else
      {
        return true;
        //System.out.println("Faction Deed: "+deed.getName());
      }
    }
    return false;
  }

  private boolean handleCount(DeedDescription deed)
  {
    int nbObjectivesWithCount=0;
    int totalCount=0;
    for(Objective objective : deed.getObjectives().getObjectives())
    {
      boolean hasCount=false;
      for(ObjectiveCondition condition : objective.getConditions())
      {
        int count=condition.getCount();
        if (count>1)
        {
          totalCount+=count;
          hasCount=true;
        }
      }
      if (hasCount) nbObjectivesWithCount++;
    }
    if (nbObjectivesWithCount>0)
    {
      System.out.println("#1 Deed: "+deed.getName()+" => "+totalCount);
      if (nbObjectivesWithCount>1)
      {
        System.out.println("   => several counts!");
      }
      return true;
    }
    return false;
  }

  private void doIt()
  {
    for(DeedDescription deed : DeedsManager.getInstance().getAll())
    {
      handleDeedDescription(deed);
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainAssessDeedProgress().doIt();
  }
}
