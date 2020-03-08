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
public class ReputationDeedsStatus
{
  private List<ReputationDeedStatus> _deedStatuses;

  /**
   * Constructor.
   */
  public ReputationDeedsStatus()
  {
    _deedStatuses=new ArrayList<ReputationDeedStatus>();
    FactionsRegistry registry=FactionsRegistry.getInstance();
    List<ReputationDeed> deeds=registry.getReputationDeeds();
    for(ReputationDeed deed : deeds)
    {
      ReputationDeedStatus deedStatus=new ReputationDeedStatus(deed);
      _deedStatuses.add(deedStatus);
    }
  }

  /**
   * Get the current status of reputation deeds.
   * @return a list of reputation deed statuses.
   */
  public List<ReputationDeedStatus> getAllDeedStatuses()
  {
    return _deedStatuses;
  }

  /**
   * Update the status of reputation deeds.
   * @param status Input reputation status.
   */
  public void update(ReputationStatus status)
  {
    for(ReputationDeedStatus deedStatus : _deedStatuses)
    {
      deedStatus.clear();
      ReputationDeed deed=deedStatus.getDeed();
      List<Faction> factions=deed.getFactions();
      for(Faction faction : factions)
      {
        boolean acquired=false;
        FactionStatus factionStatus=status.getFactionStatus(faction);
        if (factionStatus!=null)
        {
          FactionLevel level=factionStatus.getFactionLevel();
          if (level!=null)
          {
            if (level.getTier()==7) // Kindred
            {
              acquired=true;
            }
          }
        }
        deedStatus.setFactionStatus(faction,acquired);
      }
    }
  }
}
