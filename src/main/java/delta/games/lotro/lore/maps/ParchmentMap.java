package delta.games.lotro.lore.maps;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;

/**
 * Parchment map.
 * @author DAM
 */
public class ParchmentMap implements Identifiable
{
  private int _identifier;
  private String _name;
  private int _region;
  private List<Area> _areas;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Name.
   */
  public ParchmentMap(int id, String name)
  {
    _identifier=id;
    _name=name;
    _areas=new ArrayList<Area>();
  }

  /**
   * Get the identifier of this map.
   * @return the identifier of this map.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the map name.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the region code.
   * @return a region code.
   */
  public int getRegion()
  {
    return _region;
  }

  /**
   * Set the region code.
   * @param region Region code to set.
   */
  public void setRegion(int region)
  {
    _region=region;
  }

  /**
   * Add a child area.
   * @param area Area to add.
   */
  public void addArea(Area area)
  {
    _areas.add(area);
  }

  /**
   * Remove an area.
   * @param areaId Area identifier.
   * @return the remove Area, if any.
   */
  public Area removeArea(int areaId)
  {
    Area area=getAreaById(areaId);
    if (area!=null)
    {
      _areas.remove(area);
    }
    return area;
  }

  private Area getAreaById(int areaId)
  {
    for(Area area : _areas)
    {
      if (area.getIdentifier()==areaId)
      {
        return area;
      }
    }
    return null;
  }

  /**
   * Get the child areas.
   * @return a list of areas.
   */
  public List<Area> getAreas()
  {
    return _areas;
  }

  @Override
  public String toString()
  {
    return _identifier+" - "+_name;
  }
}
