package delta.games.lotro.lore.items.scaling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.Item;

/**
 * Scaling data for a single item.
 * @author DAM
 */
public class ItemScaling
{
  private Item _item;
  private List<StatDescription> _stats;
  private List<ItemScalingEntry> _entries;

  /**
   * Constructor.
   * @param item Involved item.
   */
  public ItemScaling(Item item)
  {
    _item=item;
    initStats();
    _entries=new ArrayList<ItemScalingEntry>();
  }

  /**
   * Get the involved item.
   * @return the involved item.
   */
  public Item getItem()
  {
    return _item;
  }

  private void initStats()
  {
    _stats=new ArrayList<StatDescription>();
    StatsProvider statsProvider=_item.getStatsProvider();
    if (statsProvider!=null)
    {
      for(StatProvider statProvider : statsProvider.getStatProviders())
      {
        StatDescription stat=statProvider.getStat();
        _stats.add(stat);
      }
    }
  }

  /**
   * Add a scaling entry.
   * @param entry Entry to add.
   */
  public void addEntry(ItemScalingEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get all the scaling entries.
   * @return a list of scaling entries.
   */
  public List<ItemScalingEntry> getEntries()
  {
    return new ArrayList<ItemScalingEntry>(_entries);
  }

  /**
   * Get the managed stats.
   * @return the managed stats.
   */
  public List<StatDescription> getStats()
  {
    return new ArrayList<StatDescription>(_stats);
  }

  /**
   * Get the managed item levels.
   * @return A sorted list of item levels.
   */
  public List<Integer> getItemLevels()
  {
    Set<Integer> itemLevels=new HashSet<Integer>();
    for(ItemScalingEntry entry : _entries)
    {
      itemLevels.add(Integer.valueOf(entry.getItemLevel()));
    }
    List<Integer> ret=new ArrayList<Integer>(itemLevels);
    Collections.sort(ret);
    return ret;
  }
}
