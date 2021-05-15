package delta.games.lotro.lore.items.paper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.paper.io.xml.PaperItemsXMLParser;

/**
 * Facade for paper items access.
 * @author DAM
 */
public class PaperItemsManager
{
  private static final Logger LOGGER=Logger.getLogger(PaperItemsManager.class);

  private static PaperItemsManager _instance=null;

  private HashMap<Integer,PaperItem> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static PaperItemsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new PaperItemsManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the database shall be loaded or not.
   */
  private PaperItemsManager(boolean load)
  {
    _cache=new HashMap<Integer,PaperItem>(100);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all paper items.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File paperItemsFile=cfg.getFile(DataFiles.PAPER_ITEMS);
    long now=System.currentTimeMillis();
    List<PaperItem> paperItems=PaperItemsXMLParser.parsepaperItemsFile(paperItemsFile);
    for(PaperItem paperItem : paperItems)
    {
      _cache.put(Integer.valueOf(paperItem.getIdentifier()),paperItem);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" paper items in "+duration+"ms.");
  }

  /**
   * Get a list of all paper items, sorted by identifier.
   * @return A list of paper items.
   */
  public List<PaperItem> getAll()
  {
    ArrayList<PaperItem> paperItems=new ArrayList<PaperItem>();
    paperItems.addAll(_cache.values());
    Collections.sort(paperItems,new IdentifiableComparator<PaperItem>());
    return paperItems;
  }

  /**
   * Get a paper item using its identifier.
   * @param id Paper item identifier.
   * @return A paper item or <code>null</code> if not found.
   */
  public PaperItem getMount(int id)
  {
    PaperItem ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }

  /**
   * Get all paper item categories.
   * @return a sorted list of paper item categories.
   */
  public List<String> getCategories()
  {
    Set<String> categories=new HashSet<String>();
    for(PaperItem paperItem : _cache.values())
    {
      categories.add(paperItem.getCategory());
    }
    List<String> ret=new ArrayList<String>(categories);
    Collections.sort(ret);
    return ret;
  }
}
