package delta.games.lotro.character.reputation;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;
import delta.games.lotro.lore.reputation.FactionsRegistry;

/**
 * Computes reputation deeds status.
 * @author DAM
 */
public class ReputationDeedsComputer
{
  /**
   * Get the status of reputation deeds.
   * @param data Input reputation data.
   * @return A list of reputation deed status.
   */
  public List<ReputationDeedStatus> compute(ReputationData data)
  {
    List<ReputationDeedStatus> ret=new ArrayList<ReputationDeedStatus>();
    FactionsRegistry registry=FactionsRegistry.getInstance();
    List<String> deeds=registry.getFactionDeeds();
    for(String deed : deeds)
    {
      ReputationDeedStatus deedData=new ReputationDeedStatus(deed);
      List<Faction> factions=registry.getFactionsForDeed(deed);
      for(Faction faction : factions)
      {
        boolean acquired=false;
        FactionData factionData=data.getFactionStat(faction);
        if ((factionData!=null) && (factionData.getFactionLevel()==FactionLevel.KINDRED))
        {
          acquired=true;
        }
        deedData.setFactionStatus(faction,acquired);
      }
      ret.add(deedData);
    }
    return ret;
  }
}
