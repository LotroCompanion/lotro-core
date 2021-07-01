package delta.games.lotro.lore.quests.geo;

import java.awt.geom.Point2D;

/**
 * Point of an achievable.
 * @author DAM
 */
public class AchievableGeoPoint
{
  private int _did;
  private String _key;
  private int _mapIndex;
  private Point2D.Float _lonLat;

  /**
   * Constructor.
   * @param did Data identifier.
   * @param key Associated key.
   * @param mapIndex Map index (index of associated map in the parent achievable).
   * @param lonLat Position.
   */
  public AchievableGeoPoint(int did, String key, int mapIndex, Point2D.Float lonLat)
  {
    _did=did;
    _key=key;
    _mapIndex=mapIndex;
    _lonLat=lonLat;
  }

  /**
   * Get the label for this point. 
   * @return a label.
   */
  public String getLabel()
  {
    return "";
  }

  /**
   * Get the data identifier.
   * @return a data identifier.
   */
  public int getDataId()
  {
    return _did;
  }

  /**
   * Get the associated key.
   * @return A string key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the index of the map to use.
   * @return A map index.
   */
  public int getMapIndex()
  {
    return _mapIndex;
  }

  /**
   * Set the map index.
   * @param mapIndex Map index to set.
   */
  public void setMapIndex(int mapIndex)
  {
    _mapIndex=mapIndex;
  }

  /**
   * Get the position as lon/lat.
   * @return a position.
   */
  public Point2D.Float getLonLat()
  {
    return _lonLat;
  }

  /**
   * Set the position.
   * @param lon Longitude.
   * @param lat Latitude.
   */
  public void setPosition(float lon, float lat)
  {
    _lonLat=new Point2D.Float(lon,lat);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Achievable point: position=");
    sb.append("lon:").append(_lonLat.x);
    sb.append(",lat:").append(_lonLat.y);
    if (_did!=0)
    {
      sb.append(", DID=").append(_did);
    }
    if (_key!=null)
    {
      sb.append(", key=").append(_key);
    }
    sb.append(", mapIndex=").append(_mapIndex);
    return sb.toString();
  }
}
