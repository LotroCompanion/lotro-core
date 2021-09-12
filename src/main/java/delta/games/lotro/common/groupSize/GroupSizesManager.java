package delta.games.lotro.common.groupSize;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.groupSize.io.xml.GroupSizeXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for access to group sizes.
 * @author DAM
 */
public class GroupSizesManager
{
  private static final Logger LOGGER=Logger.getLogger(GroupSizesManager.class);

  private static GroupSizesManager _instance=null;

  private List<GroupSize> _groupSizes;
  private HashMap<Integer,GroupSize> _cache;
  private HashMap<String,GroupSize> _keyCache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static GroupSizesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new GroupSizesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public GroupSizesManager()
  {
    _groupSizes=new ArrayList<GroupSize>();
    _cache=new HashMap<Integer,GroupSize>(10);
    _keyCache=new HashMap<String,GroupSize>(10);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File groupSizesFile=cfg.getFile(DataFiles.GROUP_SIZES);
    long now=System.currentTimeMillis();
    List<GroupSize> groupSizes=GroupSizeXMLParser.parseXML(groupSizesFile);
    for(GroupSize groupSize : groupSizes)
    {
      registerGroupSize(groupSize);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" group sizes in "+duration+"ms.");
  }

  /**
   * Register a new group size.
   * @param groupSize Group size to register.
   */
  public void registerGroupSize(GroupSize groupSize)
  {
    _groupSizes.add(groupSize);
    _cache.put(Integer.valueOf(groupSize.getCode()),groupSize);
    _keyCache.put(groupSize.getLegacyKey(),groupSize);
  }

  /**
   * Get a list of all group sizes.
   * @return A list of group sizes.
   */
  public List<GroupSize> getAll()
  {
    ArrayList<GroupSize> groupSizes=new ArrayList<GroupSize>(_groupSizes);
    return groupSizes;
  }

  /**
   * Get a group size using its key.
   * @param key Key to use.
   * @return A group size or <code>null</code> if not found.
   */
  public GroupSize getByKey(String key)
  {
    return _keyCache.get(key);
  }

  /**
   * Get a group size using its code.
   * @param code Group size code.
   * @return A group size or <code>null</code> if not found.
   */
  public GroupSize getGroupSize(int code)
  {
    GroupSize ret=null;
    ret=_cache.get(Integer.valueOf(code));
    return ret;
  }
}
