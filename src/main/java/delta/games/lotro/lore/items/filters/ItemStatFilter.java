package delta.games.lotro.lore.items.filters;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Filter items that give one or several stats.
 * @author DAM
 */
public class ItemStatFilter implements ItemFilter
{
  private boolean _hasStats;
  private List<StatDescription> _stats;

  /**
   * Constructor.
   * @param nbItems Number of possible stats.
   */
  public ItemStatFilter(int nbItems)
  {
    _stats=new ArrayList<StatDescription>();
    for(int i=0;i<nbItems;i++)
    {
      _stats.add(null);
    }
    _hasStats=false;
  }

  /**
   * Get the number of stats in this filter.
   * @return A stats count.
   */
  public int getNbItems()
  {
    return _stats.size();
  }

  /**
   * Get the stat set for the given index.
   * @param index Index to use, starting at 0.
   * @return A stat or <code>null</code>.
   */
  public StatDescription getStat(int index)
  {
    return _stats.get(index);
  }

  /**
   * Set the stat to search.
   * @param index Stat index, starting at 0.
   * @param stat Stat to search.
   */
  public void setStat(int index, StatDescription stat)
  {
    _stats.set(index,stat);
    _hasStats=false;
    for(StatDescription selectedStat : _stats)
    {
      if (selectedStat!=null)
      {
        _hasStats=true;
      }
    }
  }

  public boolean accept(Item item)
  {
    if (_hasStats)
    {
      BasicStatsSet itemStats=item.getStats();
      for(StatDescription stat : _stats)
      {
        if (stat!=null)
        {
          FixedDecimalsInteger value=itemStats.getStat(stat);
          if (value==null)
          {
            return false;
          }
        }
      }
      return true;
    }
    return true;
  }
}
