package delta.games.lotro.lore.items;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

import org.apache.log4j.Logger;

import delta.common.utils.NumericTools;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.LotroCoreConfig;
import delta.games.lotro.lore.items.comparators.ItemIdComparator;
import delta.games.lotro.lore.items.io.xml.ItemXMLParser;
import delta.games.lotro.lore.items.io.xml.ItemXMLWriter;
import delta.games.lotro.lore.items.io.xml.ItemsSetXMLParser;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Facade for items access.
 * @author DAM
 */
public class ItemsManager
{
  private static final Logger _logger=LotroLoggers.getLotroLogger();
  private static final String URL_SEED="http://lorebook.lotro.com/wiki/Special:LotroResource?id=";

  private static ItemsManager _instance=new ItemsManager();

  private HashMap<Integer,Item> _cache;
  private WeakHashMap<String,ItemsSet> _setsCache;
  
  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static ItemsManager getInstance()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private ItemsManager()
  {
    _cache=new HashMap<Integer,Item>(1000);
    _setsCache=new WeakHashMap<String,ItemsSet>();
  }

  /**
   * Load all items (can take a while).
   */
  public void loadAllItems()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File itemsDir=cfg.getItemsDir();
    File itemsFile=new File(itemsDir,"items.xml");
    ItemXMLParser parser=new ItemXMLParser();
    List<Item> items=parser.parseItemsFile(itemsFile);
    for(Item item : items)
    {
      _cache.put(Integer.valueOf(item.getIdentifier()),item);
    }
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
  public Item getItem(Integer id)
  {
    Item ret=null;
    if (id!=null)
    {
      ret=_cache.get(id);
    }
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
   * Extract item identifier from LOTRO resource URL.
   * @param url URL to use.
   * @return An item identifier or <code>null</code> if URL does not fit.
   */
  public Integer idFromURL(String url)
  {
    Integer ret=null;
    if ((url!=null) && (url.startsWith(URL_SEED)))
    {
      String idStr=url.substring(URL_SEED.length());
      ret=NumericTools.parseInteger(idStr,true);
    }
    return ret;
  }

  /**
   * Write a file with items.
   * @param toFile Output file.
   * @param items Items to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeItemsFile(File toFile, List<Item> items)
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
