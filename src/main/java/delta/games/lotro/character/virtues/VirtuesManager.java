package delta.games.lotro.character.virtues;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.character.virtues.io.xml.VirtueDescriptionXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for access to virtues.
 * @author DAM
 */
public class VirtuesManager
{
  private static VirtuesManager _instance=null;

  private HashMap<Integer,VirtueDescription> _cache;
  private HashMap<String,VirtueDescription> _mapByKey;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static VirtuesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new VirtuesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public VirtuesManager()
  {
    _cache=new HashMap<Integer,VirtueDescription>(20);
    _mapByKey=new HashMap<String,VirtueDescription>();
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File virtuesFile=cfg.getFile(DataFiles.VIRTUES);
    long now=System.currentTimeMillis();
    List<VirtueDescription> virtues=new VirtueDescriptionXMLParser().parseVirtuesFile(virtuesFile);
    for(VirtueDescription virtue : virtues)
    {
      registerVirtue(virtue);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"virtues",duration);
  }

  /**
   * Register a new virtue.
   * @param virtue Virtue to register.
   */
  public void registerVirtue(VirtueDescription virtue)
  {
    _cache.put(Integer.valueOf(virtue.getIdentifier()),virtue);
    String key=virtue.getPersistenceKey();
    _mapByKey.put(key,virtue);
  }

  /**
   * Get a list of all virtues, sorted by identifier.
   * @return A list of virtues.
   */
  public List<VirtueDescription> getAll()
  {
    ArrayList<VirtueDescription> virtues=new ArrayList<VirtueDescription>();
    virtues.addAll(_cache.values());
    Collections.sort(virtues,new VirtueKeyComparator());
    return virtues;
  }

  /**
   * Get a virtue using its identifier.
   * @param id Virtue identifier.
   * @return A virtue description or <code>null</code> if not found.
   */
  public VirtueDescription getVirtue(int id)
  {
    return _cache.get(Integer.valueOf(id));
  }

  /**
   * Get a virtue using its key.
   * @param key Virtue key.
   * @return A virtue description or <code>null</code> if not found.
   */
  public VirtueDescription getByKey(String key)
  {
    return _mapByKey.get(key);
  }
}
