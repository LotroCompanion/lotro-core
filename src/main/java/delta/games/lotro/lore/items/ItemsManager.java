package delta.games.lotro.lore.items;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.LotroCoreConfig;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.lore.items.comparators.ItemIdComparator;
import delta.games.lotro.lore.items.comparators.ItemNameComparator;
import delta.games.lotro.lore.items.finder.ItemsFinder;
import delta.games.lotro.lore.items.io.xml.ItemSaxParser;
import delta.games.lotro.lore.items.sort.ItemsSorter;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Facade for items access.
 * @author DAM
 */
public class ItemsManager
{
  private static final Logger _logger=LotroLoggers.getLotroLogger();

  private static ItemsManager _instance=null;

  private ItemsSorter _sorter;
  private HashMap<Integer,Item> _cache;
  private ItemsFinder _finder;

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
   * Constructor.
   * @param items Items to use.
   */
  public ItemsManager(List<Item> items)
  {
    this(false);
    for(Item item : items)
    {
      _cache.put(Integer.valueOf(item.getIdentifier()),item);
    }
    _sorter.sortItems(items);
  }

  /**
   * Get items that fit a slot.
   * @param slot Targeted slot.
   * @return A list of items.
   */
  public List<Item> getItems(EQUIMENT_SLOT slot)
  {
    List<Item> items=_sorter.getItems(slot);
    Collections.sort(items,new ItemNameComparator());
    return items;
  }

  /**
   * Get a list of all essences.
   * @return a list of essence items.
   */
  public List<Item> getEssences()
  {
    return _sorter.buildEssencesList();
  }

  /**
   * Load all items.
   */
  private void loadAllItems()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File itemsDir=cfg.getLoreDir();
    File itemsFile=new File(itemsDir,"items.xml");
    long now=System.currentTimeMillis();
    List<Item> items=ItemSaxParser.parseItemsFile(itemsFile);
    for(Item item : items)
    {
      _cache.put(Integer.valueOf(item.getIdentifier()),item);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    _logger.info("Loaded "+_cache.size()+" items in "+duration+"ms.");
  }

  /**
   * Get a list of all items, sorted by identifier.
   * @return A list of items.
   */
  public List<Item> getAllItems()
  {
    ArrayList<Item> items=new ArrayList<Item>();
    items.addAll(_cache.values());
    Collections.sort(items,new ItemIdComparator());
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

  /**
   * Get the items finder.
   * @return the items finder.
   */
  public ItemsFinder getFinder()
  {
    if (_finder==null)
    {
      _finder=new ItemsFinder();
    }
    return _finder;
  }
}
