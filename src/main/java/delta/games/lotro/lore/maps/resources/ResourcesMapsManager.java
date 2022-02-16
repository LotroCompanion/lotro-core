package delta.games.lotro.lore.maps.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.maps.resources.io.xml.ResourcesMapsXMLParser;

/**
 * Manager for all resources maps.
 * @author DAM
 */
public class ResourcesMapsManager
{
  private static ResourcesMapsManager _instance=null;
  private Map<String,ResourcesMapDescriptor> _maps;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static ResourcesMapsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=load();
    }
    return _instance;
  }

  private static ResourcesMapsManager load()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.RESOURCES_MAPS);
    return new ResourcesMapsXMLParser().parseXML(from);
  }

  /**
   * Constructor.
   */
  public ResourcesMapsManager()
  {
    _maps=new HashMap<String,ResourcesMapDescriptor>();
  }

  /**
   * Add a resources map.
   * @param map Map to add.
   */
  public void addResourcesMap(ResourcesMapDescriptor map)
  {
    String key=getKey(map.getLevel());
    _maps.put(key,map);
  }

  /**
   * Get the map for the given crafting level.
   * @param level Level to use.
   * @return A resources map or <code>null</code> if not found.
   */
  public ResourcesMapDescriptor getMapForLevel(CraftingLevel level)
  {
    String key=getKey(level);
    return _maps.get(key);
  }

  private String getKey(CraftingLevel level)
  {
    String professionKey=level.getProfession().getKey();
    int tier=level.getTier();
    return professionKey+"-"+tier;
  }

  /**
   * Get all parchment maps.
   * @return a list of parchment maps.
   */
  public List<ResourcesMapDescriptor> getResourcesMaps()
  {
    List<ResourcesMapDescriptor> ret=new ArrayList<ResourcesMapDescriptor>();
    ret.addAll(_maps.values());
    Collections.sort(ret,new ResourcesMapDescriptorComparator());
    return ret;
  }

  /**
   * Dump the contents of this manager.
   */
  public void dump()
  {
    List<ResourcesMapDescriptor> maps=getResourcesMaps();
    System.out.println("Maps: ("+maps.size()+")");
    for(ResourcesMapDescriptor map : maps)
    {
      System.out.println("\t"+map);
    }
  }
}
