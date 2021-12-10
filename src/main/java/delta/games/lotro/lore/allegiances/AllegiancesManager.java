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
  private Points2LevelCurvesManager _curvesManager;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static AllegiancesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=load();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public AllegiancesManager()
  {
    _cache=new HashMap<Integer,AllegianceDescription>(1000);
    _curvesManager=new Points2LevelCurvesManager();
  }

  /**
   * Load data from a file.
   * @return the loaded data.
   */
  private static AllegiancesManager load()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File allegiancesFile=cfg.getFile(DataFiles.ALLEGIANCES);
    long now=System.currentTimeMillis();
    AllegiancesManager mgr=new AllegianceXMLParser().parseXML(allegiancesFile);
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    int nbAllegiances=mgr.getAll().size();
    LOGGER.info("Loaded "+nbAllegiances+" allegiances in "+duration+"ms.");
    return mgr;
  }

  /**
   * Add an allegiance.
   * @param allegianceDescription Allegiance to add.
   */
  public void addAllegiance(AllegianceDescription allegianceDescription)
  {
    Integer key=Integer.valueOf(allegianceDescription.getIdentifier());
    _cache.put(key,allegianceDescription);
  }

  /**
   * Get the curves manager.
   * @return the curves manager.
   */
  public Points2LevelCurvesManager getCurvesManager()
  {
    return _curvesManager;
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
    ArrayList<AllegianceDescription> allegiances=new ArrayList<AllegianceDescription>();
    allegiances.addAll(_cache.values());
    Collections.sort(allegiances,new IdentifiableComparator<AllegianceDescription>());
    return allegiances;
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
