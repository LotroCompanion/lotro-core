package delta.games.lotro.lore.items.legendary;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.LotroCoreConfig;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.lore.items.legendary.io.xml.LegacyXMLParser;

/**
 * Facade for access to legacies.
 * @author DAM
 */
public class LegaciesManager
{
  private static final Logger LOGGER=Logger.getLogger(LegaciesManager.class);

  private static LegaciesManager _instance=null;

  private HashMap<Integer,Legacy> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static LegaciesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new LegaciesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public LegaciesManager()
  {
    _cache=new HashMap<Integer,Legacy>(100);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File loreDir=cfg.getLoreDir();
    File legaciesFile=new File(loreDir,"legacies.xml");
    long now=System.currentTimeMillis();
    List<Legacy> legacies=LegacyXMLParser.parseLegaciesFile(legaciesFile);
    for(Legacy legacy : legacies)
    {
      registerLegacy(legacy);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" legacies in "+duration+"ms.");
  }

  /**
   * Register a new legacy.
   * @param legacy Legacy to register.
   */
  public void registerLegacy(Legacy legacy)
  {
    _cache.put(Integer.valueOf(legacy.getIdentifier()),legacy);
  }

  /**
   * Get a list of all legacies, sorted by identifier.
   * @return A list of legacies.
   */
  public List<Legacy> getAll()
  {
    ArrayList<Legacy> legacies=new ArrayList<Legacy>();
    legacies.addAll(_cache.values());
    Collections.sort(legacies,new IdentifiableComparator<Legacy>());
    return legacies;
  }

  /**
   * Get a legacy using its identifier.
   * @param id Legacy identifier.
   * @return A legacy or <code>null</code> if not found.
   */
  public Legacy getLegacy(int id)
  {
    Legacy ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
