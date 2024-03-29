package delta.games.lotro.character.status.reputation;

import java.util.List;

import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;
import delta.games.lotro.lore.reputation.FactionsRegistry;

/**
 * Computes LOTRO points given by reputation.
 * @author DAM
 */
public class ReputationLotroPointsComputer
{
  /**
   * Get the lotro points for a given reputation.
   * @param status Input reputation status.
   * @return A lotro points count.
   */
  public int compute(ReputationStatus status)
  {
    int fromFactionLevels=getPointFromFactionLevels(status);
    int fromDeeds=getPointFromReputationDeeds(status.getDeedsStatus());
    return fromFactionLevels+fromDeeds;
  }

  private int getPointFromFactionLevels(ReputationStatus status)
  {
    int ret=0;
    FactionsRegistry registry=FactionsRegistry.getInstance();
    List<Faction> factions=registry.getAll();
    for(Faction faction : factions)
    {
      FactionStatus factionStatus=status.getFactionStatus(faction);
      if (factionStatus==null)
      {
        continue;
      }
      FactionLevel currentLevel=factionStatus.getFactionLevel();
      if (currentLevel==null)
      {
        continue;
      }
      int points=0;
      FactionLevel[] levels=faction.getLevels();
      for(FactionLevel level : levels)
      {
        if (level.getTier()<=currentLevel.getTier())
        {
          int levelPoints=level.getLotroPoints();
          points+=levelPoints;
        }
      }
      ret+=points;
    }
    return ret;
  }

  private int getPointFromReputationDeeds(ReputationDeedsStatus status)
  {
    int ret=0;
    List<ReputationDeedStatus> deedStatuses=status.getAllDeedStatuses();
    for(ReputationDeedStatus deedStatus : deedStatuses)
    {
      boolean done=deedStatus.isDone();
      if (done)
      {
        ret+=deedStatus.getDeed().getLotroPoints();
      }
    }
    return ret;
  }
}
