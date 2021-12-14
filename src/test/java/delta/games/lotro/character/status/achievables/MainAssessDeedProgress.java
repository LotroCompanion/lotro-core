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
    handleMetaDeed(deed);
  }

  private void handleMetaDeed(DeedDescription deed)
  {
    List<Objective> objectives=deed.getObjectives().getObjectives();
    int nbObjectives=objectives.size();
    if (nbObjectives>1)
    {
      System.out.println("Deed: "+deed.getName()+" => "+nbObjectives+" objectives");
    }
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
    @SuppressWarnings("unused")
    int nbCounts=0;
    int nbObjectivesWithCount=0;
    for(Objective objective : deed.getObjectives().getObjectives())
    {
      boolean hasCount=false;
      for(ObjectiveCondition condition : objective.getConditions())
      {
        int count=condition.getCount();
        if (count>1)
        {
          nbCounts++;
          hasCount=true;
        }
      }
      if (hasCount) nbObjectivesWithCount++;
    }
    if (nbObjectivesWithCount>0)
    {
      //System.out.println("Deed: "+deed.getName()+" => "+nbCounts+" conditions");
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
   * @param args
   */
  public static void main(String[] args)
  {
    new MainAssessDeedProgress().doIt();

  }

}
