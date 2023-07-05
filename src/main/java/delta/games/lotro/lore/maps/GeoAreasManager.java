package delta.games.lotro.lore.maps;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.maps.io.xml.GeoAreasXMLParser;

/**
 * Manager for all geographic areas.
 * @author DAM
 */
public class GeoAreasManager
{
  private static final Logger LOGGER=Logger.getLogger(GeoAreasManager.class);
  private static GeoAreasManager _instance=null;
  private Map<Integer,Region> _regions;
  private Map<Integer,Territory> _territories;
  private Map<Integer,Area> _areas;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static GeoAreasManager getInstance()
  {
    if (_instance==null)
    {
      _instance=load();
    }
    return _instance;
  }

  private static GeoAreasManager load()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.GEO_AREAS);
    return new GeoAreasXMLParser().parseXML(from);
  }

  /**
   * Constructor.
   */
  public GeoAreasManager()
  {
    _regions=new HashMap<Integer,Region>();
    _territories=new HashMap<Integer,Territory>();
    _areas=new HashMap<Integer,Area>();
  }

  /**
   * Add a region.
   * @param region Region to add.
   */
  public void addRegion(Region region)
  {
    Integer key=Integer.valueOf(region.getIdentifier());
    _regions.put(key,region);
  }

  /**
   * Get a region using its identifier.
   * @param regionId Region identifier.
   * @return A region or <code>null</code> if not found.
   */
  public Region getRegionById(int regionId)
  {
    return _regions.get(Integer.valueOf(regionId));
  }

  /**
   * Get a region using its code.
   * @param regionCode Region code.
   * @return A region or <code>null</code> if not found.
   */
  public Region getRegionByCode(int regionCode)
  {
    for(Region region : _regions.values())
    {
      if (region.getCode()==regionCode)
      {
        return region;
      }
    }
    return null;
  }

  /**
   * Get all regions.
   * @return a list of regions.
   */
  public List<Region> getRegions()
  {
    List<Region> ret=new ArrayList<Region>();
    ret.addAll(_regions.values());
    Collections.sort(ret,new IdentifiableComparator<Region>());
    return ret;
  }

  /**
   * Add a territory.
   * @param territory Territory to add.
   */
  public void addTerritory(Territory territory)
  {
    Integer key=Integer.valueOf(territory.getIdentifier());
    _territories.put(key,territory);
  }

  /**
   * Get a territory using its identifier.
   * @param territoryId Territory identifier.
   * @return A territory or <code>null</code> if not found.
   */
  public Territory getTerritoryById(int territoryId)
  {
    return _territories.get(Integer.valueOf(territoryId));
  }

  /**
   * Get all territories.
   * @return a list of territories.
   */
  public List<Territory> getTerritories()
  {
    List<Territory> ret=new ArrayList<Territory>();
    ret.addAll(_territories.values());
    Collections.sort(ret,new IdentifiableComparator<Territory>());
    return ret;
  }

  /**
   * Get the territories for a region.
   * @param regionId Region identifier.
   * @return An un-ordered list of territories.
   */
  public List<Territory> getTerritoriesForRegion(int regionId)
  {
    List<Territory> ret=new ArrayList<Territory>();
    for(Territory territory : _territories.values())
    {
      Region parentRegion=territory.getParentRegion();
      if ((parentRegion!=null) && (parentRegion.getIdentifier()==regionId))
      {
        ret.add(territory);
      }
    }
    return ret;
  }

  /**
   * Add an area.
   * @param area Area to add.
   */
  public void addArea(Area area)
  {
    Integer key=Integer.valueOf(area.getIdentifier());
    _areas.put(key,area);
  }

  /**
   * Get an area using its identifier.
   * @param areaId Area identifier.
   * @return A area or <code>null</code> if not found.
   */
  public Area getAreaById(int areaId)
  {
    return _areas.get(Integer.valueOf(areaId));
  }

  /**
   * Get all areas.
   * @return a list of areas.
   */
  public List<Area> getAreas()
  {
    List<Area> ret=new ArrayList<Area>();
    ret.addAll(_areas.values());
    Collections.sort(ret,new IdentifiableComparator<Area>());
    return ret;
  }

  /**
   * Get the areas for a territory.
   * @param territoryId Territory identifier.
   * @return An un-ordered list of areas.
   */
  public List<Area> getAreasForTerritory(int territoryId)
  {
    List<Area> ret=new ArrayList<Area>();
    for(Area area : _areas.values())
    {
      Territory parentTerritory=area.getParentTerritory();
      if ((parentTerritory!=null) && (parentTerritory.getIdentifier()==territoryId))
      {
        ret.add(area);
      }
    }
    return ret;
  }

  /**
   * Get a land division using its identifier.
   * @param landId Land division identifier.
   * @return A land division or <code>null</code> if not found.
   */
  public LandDivision getLandById(int landId)
  {
    GeoAreasManager mgr=GeoAreasManager.getInstance();
    Territory territory=mgr.getTerritoryById(landId);
    if (territory!=null)
    {
      return territory;
    }
    Region region=mgr.getRegionById(landId);
    if (region!=null)
    {
      return region;
    }
    Area area=mgr.getAreaById(landId);
    if (area!=null)
    {
      return area;
    }
    LOGGER.warn("Land not found: "+landId);
    return null;
  }
  /**
   * Dump the contents of this manager.
   */
  public void dump()
  {
    List<Region> regions=getRegions();
    System.out.println("Regions: ("+regions.size()+")");
    for(Region region : regions)
    {
      System.out.println("\t"+region);
    }
    List<Territory> territories=getTerritories();
    System.out.println("Territories: ("+territories.size()+")");
    for(Territory territory : territories)
    {
      System.out.println("\t"+territory);
    }
    List<Area> areas=getAreas();
    System.out.println("Areas: ("+areas.size()+")");
    for(Area area : areas)
    {
      System.out.println("\t"+area);
    }
  }
}
