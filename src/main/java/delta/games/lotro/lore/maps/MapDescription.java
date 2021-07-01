package delta.games.lotro.lore.maps;

import delta.games.lotro.lore.geo.GeoBoundingBox;

/**
 * Description of a map.
 * @author DAM
 */
public class MapDescription
{
  private Integer _mapId;
  private int _region;
  private GeoBoundingBox _boundingBox;

  /**
   * Constructor.
   */
  public MapDescription()
  {
    // Nothing!
  }

  /**
   * Get the basemap identifier.
   * @return a basemap identifier (may be <code>null</code>).
   */
  public Integer getMapId()
  {
    return _mapId;
  }

  /**
   * Set the basemap identifier.
   * @param mapId A map identifier (may be <code>null</code>).
   */
  public void setMapId(Integer mapId)
  {
    _mapId=mapId;
  }

  /**
   * Get the region.
   * @return a region or 0 if not set.
   */
  public int getRegion()
  {
    return _region;
  }

  /**
   * Set the region.
   * @param region Region to set.
   */
  public void setRegion(int region)
  {
    _region=region;
  }

  /**
   * Get the geographic bounding box to use.
   * @return A box or <coce>null</code> if not set.
   */
  public GeoBoundingBox getBoundingBox()
  {
    return _boundingBox;
  }

  /**
   * Set the geographic bounding box to use.
   * @param boundingBox A box (may be <code>null</code>).
   */
  public void setBoundingBox(GeoBoundingBox boundingBox)
  {
    _boundingBox=boundingBox;
  }

  @Override
  public String toString()
  {
    return "Map: basemap="+_mapId+", region="+_region+", bounding box="+_boundingBox;
  }
}
