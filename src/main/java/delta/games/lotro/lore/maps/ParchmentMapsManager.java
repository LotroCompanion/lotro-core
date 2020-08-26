package delta.games.lotro.lore.maps;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.maps.io.xml.ParchmentMapsXMLParser;

/**
 * Manager for all parchment maps.
 * @author DAM
 */
public class ParchmentMapsManager
{
  private static final ParchmentMapsManager _instance=load();
  private Map<Integer,ParchmentMap> _maps;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static ParchmentMapsManager getInstance()
  {
    return _instance;
  }

  private static ParchmentMapsManager load()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.PARCHMENT_MAPS);
    return new ParchmentMapsXMLParser().parseXML(from);
  }

  /**
   * Constructor.
   */
  public ParchmentMapsManager()
  {
    _maps=new HashMap<Integer,ParchmentMap>();
  }

  /**
   * Add a parchment map.
   * @param map Map to add.
   */
  public void addParchmentMap(ParchmentMap map)
  {
    Integer key=Integer.valueOf(map.getIdentifier());
    _maps.put(key,map);
  }

  /**
   * Get a map using its identifier.
   * @param mapId Map identifier.
   * @return A parchment map or <code>null</code> if not found.
   */
  public ParchmentMap getMapById(int mapId)
  {
    return _maps.get(Integer.valueOf(mapId));
  }

  /**
   * Get all parchment maps.
   * @return a list of parchment maps.
   */
  public List<ParchmentMap> getParchmentMaps()
  {
    List<ParchmentMap> ret=new ArrayList<ParchmentMap>();
    ret.addAll(_maps.values());
    Collections.sort(ret,new IdentifiableComparator<ParchmentMap>());
    return ret;
  }

  /**
   * Dump the contents of this manager.
   */
  public void dump()
  {
    List<ParchmentMap> maps=getParchmentMaps();
    System.out.println("Maps: ("+maps.size()+")");
    for(ParchmentMap map : maps)
    {
      System.out.println("\t"+map);
    }
  }
}
