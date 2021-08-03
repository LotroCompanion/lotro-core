package delta.games.lotro.character.status.statistics.reputation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.lore.reputation.Faction;

/**
 * Reputation statistics.
 * @author DAM
 */
public class ReputationStats
{
  private Map<String,FactionStats> _statistics;

  /**
   * Constructor.
   */
  public ReputationStats()
  {
    _statistics=new HashMap<String,FactionStats>();
  }

  /**
   * Get the statistics for a single faction.
   * @param faction Faction to use.
   * @param createIfNeeded <code>true</code> to create an empty statistics object
   * or <code>null</code> to return <code>null</code> if not found.
   * @return A faction statistics or <code>null</code>.
   */
  public FactionStats get(Faction faction, boolean createIfNeeded)
  {
    String factionKey=faction.getIdentifyingKey();
    FactionStats factionStats=_statistics.get(factionKey);
    if ((factionStats==null) && (createIfNeeded))
    {
      factionStats=new FactionStats(faction);
      _statistics.put(factionKey,factionStats);
    }
    return factionStats;
  }

  /**
   * Get the number of known factions.
   * @return A factions count.
   */
  public int getFactionsCount()
  {
    return _statistics.size();
  }

  /**
   * Get the known statistics.
   * @return the known statistics.
   */
  public List<FactionStats> getFactionStats()
  {
    return new ArrayList<FactionStats>(_statistics.values());
  }

  /**
   * Get the total number of reputation points.
   * @return A reputation points count.
   */
  public int getTotalReputationPoints()
  {
    int total=0;
    for(FactionStats factionStats : _statistics.values())
    {
      int points=factionStats.getPoints();
      total+=points;
    }
    return total;
  }

  /**
   * Reset contents.
   */
  public void reset()
  {
    _statistics.clear();
  }
}
