package delta.games.lotro.character.status.achievables.statistics.virtues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.lore.quests.Achievable;

/**
 * Virtue XP statistics.
 * @author DAM
 */
public class VirtueXPStatsFromAchievables
{
  private static final Logger LOGGER=LoggerFactory.getLogger(VirtueXPStatsFromAchievables.class);

  private int _totalVirtueXP;
  private int _totalCompletions;
  private Map<Integer,VirtueXPStatsFromAchievable> _mapByAchievable;
  private List<VirtueXPStatsFromAchievable> _stats;

  /**
   * Constructor.
   */
  public VirtueXPStatsFromAchievables()
  {
    _mapByAchievable=new HashMap<Integer,VirtueXPStatsFromAchievable>();
    _stats=new ArrayList<VirtueXPStatsFromAchievable>();
  }

  /**
   * Register an achievable.
   * @param achievable Achievable.
   * @param virtueXP Virtue XP reward.
   * @param completions Completions count.
   */
  public void addAchievable(Achievable achievable, int virtueXP, int completions)
  {
    VirtueXPStatsFromAchievable entry=new VirtueXPStatsFromAchievable(achievable);
    entry.setCompletions(completions,virtueXP);
    _stats.add(entry);
    VirtueXPStatsFromAchievable old=_mapByAchievable.put(Integer.valueOf(achievable.getIdentifier()),entry);
    if (old!=null)
    {
      LOGGER.warn("Using duplicate achievable: "+achievable);
      _stats.remove(old);
    }
    _totalVirtueXP+=entry.getPoints();
    _totalCompletions+=completions;
  }

  /**
   * Get a list of virtue XP stats.
   * @return A list of virtue XP stats.
   */
  public List<VirtueXPStatsFromAchievable> getStats()
  {
    return _stats;
  }

  /**
   * Get the number of registered achievables.
   * @return A count.
   */
  public int getEntriesCount()
  {
    return _mapByAchievable.size();
  }

  /**
   * Clear all data.
   */
  public void clear()
  {
    _mapByAchievable.clear();
    _stats.clear();
    _totalVirtueXP=0;
    _totalCompletions=0;
  }

  /**
   * Get the total virtue XP.
   * @return a virtue XP count.
   */
  public int getTotalVirtueXP()
  {
    return _totalVirtueXP;
  }

  /**
   * Get the total completions count.
   * @return a count.
   */
  public int getTotalCompletions()
  {
    return _totalCompletions;
  }
}
