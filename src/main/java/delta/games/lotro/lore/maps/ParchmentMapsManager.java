package delta.games.lotro.lore.maps;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private static final Logger LOGGER=LoggerFactory.getLogger(ParchmentMapsManager.class);

  private static ParchmentMapsManager _instance=null;
  private Map<Integer,ParchmentMap> _maps;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static ParchmentMapsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=load();
    }
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
   * Get the parchment map for an area.
   * @param areaId Area identifier.
   * @return A parchment map or <code>null</code> if not found.
   */
  public ParchmentMap getParchmentMapForArea(int areaId)
  {
    ParchmentMap ret=null;
    int nbFinds=0;
    ParchmentMapsManager mapsManager=ParchmentMapsManager.getInstance();
    List<ParchmentMap> maps=mapsManager.getParchmentMaps();
    for(ParchmentMap map : maps)
    {
      List<Area> areas=map.getAreas();
      for(Area area : areas)
      {
        if (area.getIdentifier()==areaId)
        {
          ret=map;
          nbFinds++;
        }
      }
    }
    if (nbFinds>1)
    {
      LOGGER.warn("Multiple maps for area: {}",Integer.valueOf(areaId));
    }
    return ret;
  }

  /**
   * Get the root map (Middle-Earth map).
   * @return  the root map.
   */
  public ParchmentMap getRootMap()
  {
    for(ParchmentMap map : _maps.values())
    {
      if (map.getParentMapId()==0)
      {
        return map;
      }
    }
    return null;
  }

  /**
   * Get the child maps for the given map.
   * @param parentMapId Parent map identifier.
   * @return A possibly empty but never <code>null</code> list of maps.
   */
  public List<ParchmentMap> getChildMaps(int parentMapId)
  {
    List<ParchmentMap> ret=new ArrayList<ParchmentMap>();
    for(ParchmentMap map : _maps.values())
    {
      if (map.getParentMapId()==parentMapId)
      {
        ret.add(map);
      }
    }
    return ret;
  }

  /**
   * Dump the contents of this manager.
   * @param out Output stream.
   */
  public void dump(PrintStream out)
  {
    List<ParchmentMap> maps=getParchmentMaps();
    out.println("Maps: ("+maps.size()+")");
    for(ParchmentMap map : maps)
    {
      out.println("\t"+map);
    }
  }
}
