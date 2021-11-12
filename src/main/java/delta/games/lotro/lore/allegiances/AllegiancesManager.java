package delta.games.lotro.lore.allegiances;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.allegiances.io.xml.AllegianceXMLParser;

/**
 * Facade for allegiances access.
 * @author DAM
 */
public class AllegiancesManager
{
  private static final Logger LOGGER=Logger.getLogger(AllegiancesManager.class);

  private static AllegiancesManager _instance=null;

  private HashMap<Integer,AllegianceDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static AllegiancesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new AllegiancesManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the allegiances database shall be loaded or not.
   */
  private AllegiancesManager(boolean load)
  {
    _cache=new HashMap<Integer,AllegianceDescription>(1000);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all allegiances.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File allegiancesFile=cfg.getFile(DataFiles.ALLEGIANCES);
    long now=System.currentTimeMillis();
    List<AllegianceDescription> allegiances=new AllegianceXMLParser().parseXML(allegiancesFile);
    for(AllegianceDescription allegiance : allegiances)
    {
      _cache.put(Integer.valueOf(allegiance.getIdentifier()),allegiance);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" allegiances in "+duration+"ms.");
  }

  /**
   * Get the allegiances groups.
   * @return A sorted list of allegiances groups.
   */
  public List<String> getAllegiancesGroups()
  {
    Set<String> groups=new HashSet<String>();
    for(AllegianceDescription allegiance : _cache.values())
    {
      String group=allegiance.getGroup();
      groups.add(group);
    }
    List<String> ret=new ArrayList<String>(groups);
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get a list of all allegiances, sorted by identifier.
   * @return A list of allegiances.
   */
  public List<AllegianceDescription> getAll()
  {
    ArrayList<AllegianceDescription> titles=new ArrayList<AllegianceDescription>();
    titles.addAll(_cache.values());
    Collections.sort(titles,new IdentifiableComparator<AllegianceDescription>());
    return titles;
  }

  /**
   * Get a list of all allegiances for a group, sorted by names.
   * @param group Group to use.
   * @return A list of allegiances.
   */
  public List<AllegianceDescription> getAllegiances(String group)
  {
    ArrayList<AllegianceDescription> ret=new ArrayList<AllegianceDescription>();
    for(AllegianceDescription allegiance : _cache.values())
    {
      if (group.equals(allegiance.getGroup()))
      {
        ret.add(allegiance);
      }
    }
    Collections.sort(ret,new NamedComparator());
    return ret;
  }

  /**
   * Get an allegiance using its identifier.
   * @param id Allegiance identifier.
   * @return An allegiance description or <code>null</code> if not found.
   */
  public AllegianceDescription getAllegiance(int id)
  {
    AllegianceDescription ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
