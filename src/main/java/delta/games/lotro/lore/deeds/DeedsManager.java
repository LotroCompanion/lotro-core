package delta.games.lotro.lore.deeds;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.LotroCoreConfig;
import delta.games.lotro.lore.deeds.io.xml.DeedProxiesResolver;
import delta.games.lotro.lore.deeds.io.xml.DeedXMLParser;

/**
 * Facade for deeds access.
 * @author DAM
 */
public final class DeedsManager
{
  private static DeedsManager _instance=new DeedsManager();

  private List<DeedDescription> _deeds;
  private Map<Integer,DeedDescription> _deedsMapById;
  private Map<String,DeedDescription> _deedsMapByKey;
  private DeedRewardsExplorer _rewardsExplorer;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static DeedsManager getInstance()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private DeedsManager()
  {
    _deeds=new ArrayList<DeedDescription>();
    _deedsMapById=new HashMap<Integer,DeedDescription>();
    _deedsMapByKey=new HashMap<String,DeedDescription>();
    File loreDir=LotroCoreConfig.getInstance().getLoreDir();
    File deedFile=new File(loreDir,"deeds.xml");
    DeedXMLParser parser=new DeedXMLParser();
    List<DeedDescription> deeds=parser.parseXML(deedFile);
    new DeedProxiesResolver(deeds).doIt();
    _deeds.addAll(deeds);
    for(DeedDescription deed : _deeds)
    {
      _deedsMapById.put(Integer.valueOf(deed.getIdentifier()),deed);
      _deedsMapByKey.put(deed.getKey(),deed);
    }
    _rewardsExplorer=new DeedRewardsExplorer();
    _rewardsExplorer.doIt(deeds);
  }

  /**
   * Get a list of all deeds.
   * @return A list of all deeds.
   */
  public List<DeedDescription> getAll()
  {
    List<DeedDescription> ret=new ArrayList<DeedDescription>(_deeds);
    return ret;
  }

  /**
   * Get the rewards explorer.
   * @return the rewards explorer.
   */
  public DeedRewardsExplorer getRewardsExplorer()
  {
    return _rewardsExplorer;
  }

  /**
   * Get a deed using its identifier.
   * @param id Deed identifier.
   * @return A deed description or <code>null</code> if not found.
   */
  public DeedDescription getDeed(int id)
  {
    DeedDescription ret=_deedsMapById.get(Integer.valueOf(id));
    return ret;
  }

  /**
   * Get a deed using its key.
   * @param key Deed key.
   * @return A deed description or <code>null</code> if not found.
   */
  public DeedDescription getDeed(String key)
  {
    DeedDescription ret=_deedsMapByKey.get(key);
    return ret;
  }
}
