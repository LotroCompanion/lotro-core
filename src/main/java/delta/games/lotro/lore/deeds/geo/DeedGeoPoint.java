package delta.games.lotro.lore.deeds.geo;

/**
 * Point of a geographic deed.
 * @author DAM
 */
public class DeedGeoPoint
{
  private int _mapKey;
  private int _pointId;

  /**
   * Constructor.
   * @param mapKey Key of the map to use.
   * @param pointId Point identifier.
   */
  public DeedGeoPoint(int mapKey, int pointId)
  {
    _mapKey=mapKey;
    _pointId=pointId;
  }

  /**
   * Get the point identifier.
   * @return a point identifier.
   */
  public int getPointId()
  {
    return _pointId;
  }

  /**
   * Get the key of the map to use.
   * @return A map key.
   */
  public int getMapKey()
  {
    return _mapKey;
  }
}
