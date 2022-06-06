package delta.games.lotro.lore.webStore;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.webStore.io.xml.WebStoreItemsXMLParser;

/**
 * Facade for web store items access.
 * @author DAM
 */
public class WebStoreItemsManager
{
  private static final Logger LOGGER=Logger.getLogger(WebStoreItemsManager.class);

  private static WebStoreItemsManager _instance=null;

  private HashMap<Integer,WebStoreItem> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static WebStoreItemsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new WebStoreItemsManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the web store items shall be loaded or not.
   */
  private WebStoreItemsManager(boolean load)
  {
    _cache=new HashMap<Integer,WebStoreItem>(100);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all web store items.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File webStoreItemsFile=cfg.getFile(DataFiles.WEB_STORE_ITEMS);
    long now=System.currentTimeMillis();
    List<WebStoreItem> webStoreItems=new WebStoreItemsXMLParser().parseXML(webStoreItemsFile);
    for(WebStoreItem webStoreItem : webStoreItems)
    {
      _cache.put(Integer.valueOf(webStoreItem.getIdentifier()),webStoreItem);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" web store items in "+duration+"ms.");
  }

  /**
   * Get a list of all web store items, sorted by identifier.
   * @return A list of web store items.
   */
  public List<WebStoreItem> getAll()
  {
    ArrayList<WebStoreItem> ret=new ArrayList<WebStoreItem>();
    ret.addAll(_cache.values());
    Collections.sort(ret,new IdentifiableComparator<WebStoreItem>());
    return ret;
  }

  /**
   * Get a web store item using its identifier.
   * @param id Identifier.
   * @return A web store item or <code>null</code> if not found.
   */
  public WebStoreItem getWebStoreItem(int id)
  {
    WebStoreItem ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
