package delta.games.lotro.character.reputation;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.ReputationDeed;
import delta.games.lotro.lore.reputation.FactionLevel;
import delta.games.lotro.lore.reputation.FactionsRegistry;

/**
 * Maintains reputation deeds status.
 * @author DAM
 */
public class ReputationDeedsData
{
  private List<ReputationDeedStatus> _deedsStatus;

  /**
   * Constructor.
   */
  public ReputationDeedsData()
  {
    _deedsStatus=new ArrayList<ReputationDeedStatus>();
    FactionsRegistry registry=FactionsRegistry.getInstance();
    List<ReputationDeed> deeds=registry.getReputationDeeds();
    for(ReputationDeed deed : deeds)
    {
      ReputationDeedStatus deedData=new ReputationDeedStatus(deed);
      _deedsStatus.add(deedData);
    }
  }

  /**
   * Get the current status of reputation deeds
   * @return a list of reputation deed statuses.
   */
  public List<ReputationDeedStatus> getStatus()
  {
    return _deedsStatus;
  }

  /**
   * Update the status of reputation deeds.
   * @param data Input reputation data.
   */
  public void update(ReputationData data)
  {
    for(ReputationDeedStatus status : _deedsStatus)
    {
      status.clear();
      ReputationDeed deed=status.getDeed();
      List<Faction> factions=deed.getFactions();
      for(Faction faction : factions)
      {
        boolean acquired=false;
        FactionData factionData=data.getFactionStat(faction);
        if ((factionData!=null) && (factionData.getFactionLevel()==FactionLevel.KINDRED))
        {
          acquired=true;
        }
        status.setFactionStatus(faction,acquired);
      }
    }
  }
}
