package delta.games.lotro.character.reputation;

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
   * @param data Input reputation data.
   * @return A lotro points count.
   */
  public int compute(ReputationData data)
  {
    int fromFactionLevels=getPointFromFactionLevels(data);
    int fromDeeds=getPointFromReputationDeeds(data.getDeedsStatus());
    return fromFactionLevels+fromDeeds;
  }

  private int getPointFromFactionLevels(ReputationData data)
  {
    int ret=0;
    FactionsRegistry registry=FactionsRegistry.getInstance();
    List<Faction> factions=registry.getAll();
    for(Faction faction : factions)
    {
      FactionData factionData=data.getFactionStat(faction);
      if (factionData!=null)
      {
        FactionLevel currentLevel=factionData.getFactionLevel();
        int points=0;
        FactionLevel[] levels=faction.getLevels();
        for(FactionLevel level : levels)
        {
          int levelPoints=level.getLotroPoints();
          points+=levelPoints;
          if (level==currentLevel) break;
        }
        ret+=points;
        //System.out.println("Points for "+faction.getName()+": "+points);
      }
    }
    return ret;
  }

  private int getPointFromReputationDeeds(ReputationDeedsData data)
  {
    int ret=0;
    List<ReputationDeedStatus> deedStatuses=data.getStatus();
    for(ReputationDeedStatus status : deedStatuses)
    {
      boolean done=status.isDone();
      if (done)
      {
        ret+=status.getDeed().getLotroPoints();
      }
    }
    return ret;
  }
}
