package delta.games.lotro.character.status.skirmishes;

import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.common.groupSize.GroupSize;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;

/**
 * Gathers all stats for a single skirmish.
 * @author DAM
 */
public class SingleSkirmishStats
{
  private SkirmishPrivateEncounter _skirmish;
  private Map<String,SkirmishStats> _stats=new HashMap<String,SkirmishStats>();

  /**
   * Constructor.
   * @param skirmish Associated skirmish.
   */
  public SingleSkirmishStats(SkirmishPrivateEncounter skirmish)
  {
    _skirmish=skirmish;
    _stats=new HashMap<String,SkirmishStats>();
  }

  /**
   * Get the associated skirmish.
   * @return a skirmish.
   */
  public SkirmishPrivateEncounter getSkirmish()
  {
    return _skirmish;
  }

  /**
   * Set the stats for a given level and group size.
   * @param size Group size to use.
   * @param level Level to use.
   * @param stats Stats to set.
   */
  public void setStats(GroupSize size, SkirmishLevel level, SkirmishStats stats)
  {
    String key=buildKey(size,level);
    _stats.put(key,stats);
  }

  /**
   * Get the stats for a given level and group size.
   * @param size Group size to use.
   * @param level Level to use.
   * @return Some stats or <code>null</code> if not found.
   */
  public SkirmishStats getStats(GroupSize size, SkirmishLevel level)
  {
    String key=buildKey(size,level);
    SkirmishStats ret=_stats.get(key);
    return ret;
  }

  private String buildKey(GroupSize size, SkirmishLevel level)
  {
    return size.getCode()+"#"+level.name();
  }

  /**
   * Indicates if this object is empty or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEmpty()
  {
    for(SkirmishStats stats : _stats.values())
    {
      if (!stats.isEmpty())
      {
        return false;
      }
    }
    return true;
  }
}
