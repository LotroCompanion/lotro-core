package delta.games.lotro.lore.maps;

import java.util.ArrayList;
import java.util.List;

/**
 * Parchment map.
 * @author DAM
 */
public class ParchmentMap extends AbstractMap
{
  private int _parentMapId;
  private int _region;
  private boolean _questGuideDisabled;
  private List<Area> _areas;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Name.
   */
  public ParchmentMap(int id, String name)
  {
    super(id,name);
    _questGuideDisabled=false;
    _areas=new ArrayList<Area>();
  }

  /**
   * Get the parent map identifier.
   * @return A map identifier (0 if no parent).
   */
  public int getParentMapId()
  {
    return _parentMapId;
  }

  /**
   * Set the parent map identifier.
   * @param parentMapId Parent map identifier to set.
   */
  public void setParentMapId(int parentMapId)
  {
    _parentMapId=parentMapId;
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
   * Indicates if the quest guide is disabled or not.
   * @return <code>true</code> if it is disabled, <code>false</code> otherwise.
   */
  public boolean isQuestGuideDisabled()
  {
    return _questGuideDisabled;
  }

  /**
   * Set the 'quest guide disabled' flag.
   * @param questGuideDisabled Value to set.
   */
  public void setQuestGuideDisabled(boolean questGuideDisabled)
  {
    _questGuideDisabled=questGuideDisabled;
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

  /**
   * Remove all areas.
   */
  public void removeAllAreas()
  {
    _areas.clear();
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
}
