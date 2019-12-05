package delta.games.lotro.lore.trade.barter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.trade.barter.io.xml.BarterXMLParser;

/**
 * Facade for barterers access.
 * @author DAM
 */
public class BarterersManager
{
  private static final Logger LOGGER=Logger.getLogger(BarterersManager.class);

  private static BarterersManager _instance=null;

  private HashMap<Integer,BarterNpc> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static BarterersManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new BarterersManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the barterers database shall be loaded or not.
   */
  private BarterersManager(boolean load)
  {
    _cache=new HashMap<Integer,BarterNpc>(100);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all barterers.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File barterersFile=cfg.getFile(DataFiles.BARTERS);
    long now=System.currentTimeMillis();
    List<BarterNpc> barterers=new BarterXMLParser().parseXML(barterersFile);
    for(BarterNpc barterer : barterers)
    {
      _cache.put(Integer.valueOf(barterer.getIdentifier()),barterer);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" barterers in "+duration+"ms.");
  }

  /**
   * Get a list of all barterers, sorted by identifier.
   * @return A list of barterers.
   */
  public List<BarterNpc> getAll()
  {
    ArrayList<BarterNpc> barterers=new ArrayList<BarterNpc>();
    barterers.addAll(_cache.values());
    Collections.sort(barterers,new IdentifiableComparator<BarterNpc>());
    return barterers;
  }

  /**
   * Get a barterer using its identifier.
   * @param id Barterer identifier.
   * @return A barterer or <code>null</code> if not found.
   */
  public BarterNpc getBarterer(int id)
  {
    BarterNpc ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
