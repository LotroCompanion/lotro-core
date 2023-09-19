package delta.games.lotro.lore.items.essences;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.enums.SocketType;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.essences.io.xml.EssencesXMLParser;

/**
 * Manager for all known essences.
 * @author DAM
 */
public class EssencesManager
{
  private static final Logger LOGGER=Logger.getLogger(EssencesManager.class);

  private static EssencesManager _instance=null;

  private HashMap<Integer,Essence> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static EssencesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new EssencesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public EssencesManager()
  {
    _cache=new HashMap<Integer,Essence>(100);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File essencesFile=cfg.getFile(DataFiles.ESSENCES);
    long now=System.currentTimeMillis();
    List<Essence> essences=EssencesXMLParser.parseEssencesFile(essencesFile);
    for(Essence essence : essences)
    {
      registerEssence(essence);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" essences in "+duration+"ms.");
  }

  /**
   * Register a new essence.
   * @param essence Essence to register.
   */
  public void registerEssence(Essence essence)
  {
    _cache.put(Integer.valueOf(essence.getIdentifier()),essence);
  }

  /**
   * Get an essence using its identifier.
   * @param id Essence identifier.
   * @return An essence or <code>null</code> if not found.
   */
  public Essence getEssence(int id)
  {
    return _cache.get(Integer.valueOf(id));
  }

  /**
   * Get all essences.
   * @return a list of essences, sorted by ID.
   */
  public List<Essence> getAll()
  {
    List<Essence> ret=new ArrayList<Essence>(_cache.values());
    Collections.sort(ret,new IdentifiableComparator<Essence>());
    return ret;
  }

  /**
   * Get all essence items.
   * @return A list of items.
   */
  public List<Item> getAllEssenceItems()
  {
    List<Item> ret=new ArrayList<Item>();
    for(Essence essence : getAll())
    {
      ret.add(essence.getItem());
    }
    return ret;
  }

  /**
   * Get all the essences for a given socket type.
   * @param type Socket type.
   * @return A list of essences.
   */
  public List<Essence> getEssences(SocketType type)
  {
    List<Essence> ret=new ArrayList<Essence>();
    for(Essence essence : _cache.values())
    {
      if (essence.getType()==type)
      {
        ret.add(essence);
      }
    }
    return ret;
  }

  /**
   * Get all essence items for a given type.
   * @param type Socket type.
   * @return A list of items.
   */
  public List<Item> getAllEssenceItems(SocketType type)
  {
    List<Item> ret=new ArrayList<Item>();
    for(Essence essence : getEssences(type))
    {
      ret.add(essence.getItem());
    }
    return ret;
  }
}
