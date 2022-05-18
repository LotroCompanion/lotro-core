package delta.games.lotro.common.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.lore.reputation.Faction;

/**
 * Reputation statistics.
 * @author DAM
 * @param <T> Type of managed faction stats.
 */
public abstract class ReputationStats<T extends FactionStats>
{
  private Map<String,T> _statistics;

  /**
   * Constructor.
   */
  protected ReputationStats()
  {
    _statistics=new HashMap<String,T>();
  }

  /**
   * Get the statistics for a single faction.
   * @param faction Faction to use.
   * @param createIfNeeded <code>true</code> to create an empty statistics object
   * or <code>null</code> to return <code>null</code> if not found.
   * @return A faction statistics or <code>null</code>.
   */
  public T get(Faction faction, boolean createIfNeeded)
  {
    String factionKey=faction.getIdentifyingKey();
    T factionStats=_statistics.get(factionKey);
    if ((factionStats==null) && (createIfNeeded))
    {
      factionStats=buildFactionStats(faction);
      _statistics.put(factionKey,factionStats);
    }
    return factionStats;
  }

  /**
   * Build a new faction stats instance.
   * @param faction Faction to use.
   * @return A new faction stats instance.
   */
  public abstract T buildFactionStats(Faction faction);

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
  public List<T> getFactionStats()
  {
    return new ArrayList<T>(_statistics.values());
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
