package delta.games.lotro.lore.items;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

import org.apache.log4j.Logger;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.LotroCoreConfig;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.lore.items.comparators.ItemIdComparator;
import delta.games.lotro.lore.items.comparators.ItemNameComparator;
import delta.games.lotro.lore.items.io.xml.ItemSaxParser;
import delta.games.lotro.lore.items.io.xml.ItemXMLWriter;
import delta.games.lotro.lore.items.io.xml.ItemsSetXMLParser;
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
  private WeakHashMap<String,ItemsSet> _setsCache;

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
    _setsCache=new WeakHashMap<String,ItemsSet>();
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
   * Get items that fit a slot for a given character.
   * @param c Character.
   * @param slot Targeted slot.
   * @return A list of items.
   */
  public List<Item> getItems(CharacterData c, EQUIMENT_SLOT slot)
  {
    List<Item> items=_sorter.getItems(c,slot);
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
   * Load all items (can take a while).
   */
  public void loadAllItems()
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
   * Get a set of items using its identifier.
   * @param id Set of items identifier.
   * @return A description of this set of items or <code>null</code> if not found.
   */
  public ItemsSet getItemsSet(String id)
  {
    ItemsSet ret=null;
    if ((id!=null) && (id.length()>0))
    {
      ret=(_setsCache!=null)?_setsCache.get(id):null;
      if (ret==null)
      {
        ret=loadItemsSet(id);
        if (ret!=null)
        {
          if (_setsCache!=null)
          {
            _setsCache.put(id,ret);
          }
        }
      }
    }
    return ret;
  }

  /**
   * Write a file with items.
   * @param toFile Output file.
   * @param items Items to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeItemsFile(File toFile, List<Item> items)
  {
    ItemXMLWriter writer=new ItemXMLWriter();
    Collections.sort(items,new ItemIdComparator());
    boolean ok=writer.writeItems(toFile,items,EncodingNames.UTF_8);
    return ok;
  }

  private ItemsSet loadItemsSet(String id)
  {
    ItemsSet ret=null;
    File itemsSetFile=getItemsSetFile(id);
    if (itemsSetFile.exists())
    {
      if (itemsSetFile.length()>0)
      {
        ItemsSetXMLParser parser=new ItemsSetXMLParser();
        ret=parser.parseXML(itemsSetFile);
        if (ret==null)
        {
          _logger.error("Cannot load items set file ["+itemsSetFile+"]!");
        }
      }
    }
    return ret;
  }

  private File getItemsSetFile(String id)
  {
    File itemsDir=LotroCoreConfig.getInstance().getItemsDir();
    File setsDir=new File(itemsDir,"sets");
    String fileName=id+".xml";
    File ret=new File(setsDir,fileName);
    return ret;
  }
}
