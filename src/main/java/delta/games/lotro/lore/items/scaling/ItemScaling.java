package delta.games.lotro.lore.items.scaling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    int nbStats=statsProvider.getNumberOfStatProviders();
    for(int i=0;i<nbStats;i++)
    {
      StatProvider statProvider=statsProvider.getStatProvider(i);
      StatDescription stat=statProvider.getStat();
      _stats.add(stat);
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
}
