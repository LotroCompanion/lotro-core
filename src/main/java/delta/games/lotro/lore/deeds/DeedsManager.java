package delta.games.lotro.lore.deeds;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.LotroCoreConfig;
import delta.games.lotro.lore.deeds.io.xml.DeedXMLParser;

/**
 * Facade for deeds access.
 * @author DAM
 */
public final class DeedsManager
{
  private static DeedsManager _instance=new DeedsManager();

  private List<DeedDescription> _deeds;
  private Map<Integer,DeedDescription> _deedsMap;

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
    _deedsMap=new HashMap<Integer,DeedDescription>();
    File loreDir=LotroCoreConfig.getInstance().getLoreDir();
    File deedFile=new File(loreDir,"deeds.xml");
    DeedXMLParser parser=new DeedXMLParser();
    List<DeedDescription> deeds=parser.parseXML(deedFile);
    _deeds.addAll(deeds);
    for(DeedDescription deed : _deeds)
    {
      _deedsMap.put(Integer.valueOf(deed.getIdentifier()),deed);
    }
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
   * Get a deed using its identifier.
   * @param id Deed identifier.
   * @return A deed description or <code>null</code> if not found.
   */
  public DeedDescription getDeed(int id)
  {
    DeedDescription ret=_deedsMap.get(Integer.valueOf(id));
    return ret;
  }
}
