package delta.games.lotro.lore.collections.mounts;

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
import delta.games.lotro.lore.collections.mounts.io.xml.MountXMLParser;

/**
 * Facade for mounts access.
 * @author DAM
 */
public class MountsManager
{
  private static final Logger LOGGER=Logger.getLogger(MountsManager.class);

  private static MountsManager _instance=null;

  private HashMap<Integer,MountDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static MountsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new MountsManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the mounts database shall be loaded or not.
   */
  private MountsManager(boolean load)
  {
    _cache=new HashMap<Integer,MountDescription>(100);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all mounts.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File mountsFile=cfg.getFile(DataFiles.MOUNTS);
    long now=System.currentTimeMillis();
    List<MountDescription> mounts=new MountXMLParser().parseMountsFile(mountsFile);
    for(MountDescription mount : mounts)
    {
      _cache.put(Integer.valueOf(mount.getIdentifier()),mount);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" mounts in "+duration+"ms.");
  }

  /**
   * Get a list of all mounts, sorted by identifier.
   * @return A list of mounts.
   */
  public List<MountDescription> getAll()
  {
    ArrayList<MountDescription> mounts=new ArrayList<MountDescription>();
    mounts.addAll(_cache.values());
    Collections.sort(mounts,new IdentifiableComparator<MountDescription>());
    return mounts;
  }

  /**
   * Get a mount using its identifier.
   * @param id Mount identifier.
   * @return A mount description or <code>null</code> if not found.
   */
  public MountDescription getMount(int id)
  {
    MountDescription ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }

  /**
   * Get all mount categories.
   * @return a sorted list of mount categories.
   */
  public List<String> getCategories()
  {
    Set<String> categories=new HashSet<String>();
    for(MountDescription mount : _cache.values())
    {
      categories.add(mount.getMountCategory());
    }
    List<String> ret=new ArrayList<String>(categories);
    Collections.sort(ret);
    return ret;
  }
}
