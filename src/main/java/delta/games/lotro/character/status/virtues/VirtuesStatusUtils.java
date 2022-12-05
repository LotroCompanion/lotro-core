package delta.games.lotro.character.status.virtues;

import delta.games.lotro.character.stats.virtues.VirtuesSet;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;

/**
 * Utility methods related to virtues.
 * @author DAM
 */
public class VirtuesStatusUtils
{
  /**
   * Get the total needed virtue XP for the given virtues goal.
   * @param goal Goal to reach.
   * @param current Current virtues status.
   * @return A virtue XP amount.
   */
  public static int getNeededVirtueXP(VirtuesSet goal, VirtuesStatus current)
  {
    int ret=0;
    for(VirtueDescription virtue : VirtuesManager.getInstance().getAll())
    {
      int goalRank=goal.getVirtueRank(virtue);
      int neededXP=getNeededVirtueXP(virtue,goalRank,current);
      ret+=neededXP;
    }
    return ret;
  }

  private static int getNeededVirtueXP(VirtueDescription virtue, int goalRank, VirtuesStatus current)
  {
    int ret=0;
    SingleVirtueStatus virtueStatus=current.getVirtueStatus(virtue);
    int xp=virtueStatus.getXp();
    Integer goalXP=virtue.getXpForTier(goalRank);
    if ((goalXP!=null) && (goalXP.intValue()>xp))
    {
      ret=goalXP.intValue()-xp;
    }
    return ret;
  }
}
