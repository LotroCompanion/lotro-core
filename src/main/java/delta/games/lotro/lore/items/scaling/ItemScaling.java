package delta.games.lotro.lore.items.scaling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
  private Map<Integer,ItemScalingEntry> _entryByLevel;

  /**
   * Constructor.
   * @param item Involved item.
   */
  public ItemScaling(Item item)
  {
    _item=item;
    initStats();
    _entries=new ArrayList<ItemScalingEntry>();
    _entryByLevel=new HashMap<Integer,ItemScalingEntry>();
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
      int nbStats=statsProvider.getNumberOfStatProviders();
      for(int i=0;i<nbStats;i++)
      {
        StatProvider statProvider=statsProvider.getStatProvider(i);
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
    Integer level=Integer.valueOf(entry.getLevel());
    ItemScalingEntry old=_entryByLevel.put(level,entry);
    if (old!=null)
    {
      _entries.remove(old);
    }
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
   * Get a scaling entry using its level.
   * @param level Level to use.
   * @return A scaling entry or <code>null</code> if not found.
   */
  public ItemScalingEntry getEntry(int level)
  {
    return _entryByLevel.get(Integer.valueOf(level));
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

  /**
   * Get the scaling entries for the given item level.
   * @param itemLevel Item level to use.
   * @return A possibly empty but never <code>null</code> list of entries (sorted by level).
   */
  public List<ItemScalingEntry> getEntriesForItemLevel(int itemLevel)
  {
    List<ItemScalingEntry> ret=new ArrayList<ItemScalingEntry>();
    for(ItemScalingEntry entry : _entries)
    {
      if (entry.getItemLevel()==itemLevel)
      {
        ret.add(entry);
      }
    }
    return ret;
  }
}
