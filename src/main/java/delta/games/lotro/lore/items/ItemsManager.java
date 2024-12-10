package delta.games.lotro.lore.items;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.comparators.ItemNameComparator;
import delta.games.lotro.lore.items.io.xml.ItemSaxParser;
import delta.games.lotro.lore.items.sort.ItemsSorter;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for items access.
 * @author DAM
 */
public class ItemsManager
{
  private static ItemsManager _instance=null;

  private ItemsSorter _sorter;
  private HashMap<Integer,Item> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static ItemsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new ItemsManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the items database shall be loaded or not.
   */
  private ItemsManager(boolean load)
  {
    _cache=new HashMap<Integer,Item>(1000);
    _sorter=new ItemsSorter();
    if (load)
    {
      loadAllItems();
      _sorter.sortItems(_cache.values());
    }
  }

  /**
   * Get items that fit a location.
   * @param location Targeted location.
   * @return A list of items.
   */
  public List<Item> getItems(EquipmentLocation location)
  {
    List<Item> items=_sorter.getItems(location);
    Collections.sort(items,new ItemNameComparator());
    return items;
  }

  /**
   * Load all items.
   */
  private void loadAllItems()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File itemsFile=cfg.getFile(DataFiles.ITEMS);
    long now=System.currentTimeMillis();
    List<Item> items=ItemSaxParser.parseItemsFile(itemsFile);
    for(Item item : items)
    {
      _cache.put(Integer.valueOf(item.getIdentifier()),item);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"items",duration);
  }

  /**
   * Get a list of all items, sorted by identifier.
   * @return A list of items.
   */
  public List<Item> getAllItems()
  {
    ArrayList<Item> items=new ArrayList<Item>();
    items.addAll(_cache.values());
    Collections.sort(items,new IdentifiableComparator<Item>());
    return items;
  }

  /**
   * Get an item using its identifier.
   * @param id Item identifier.
   * @return An item description or <code>null</code> if not found.
   */
  public Item getItem(int id)
  {
    Item ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
