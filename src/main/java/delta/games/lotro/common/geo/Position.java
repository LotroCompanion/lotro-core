package delta.games.lotro.common.geo;

/**
 * Position as used in LOTRO.
 * @author DAM
 */
public class Position
{
  private int _region;
  private float _latitude;
  private float _longitude;

  /**
   * Constructor.
   * @param region Region.
   * @param longitude Longitude.
   * @param latitude Latitude.
   */
  public Position(int region, float longitude, float latitude)
  {
    _region=region;
    _longitude=longitude;
    _latitude=latitude;
  }

  /**
   * Get the region.
   * @return the region.
   */
  public int getRegion()
  {
    return _region;
  }

  /**
   * Get the latitude.
   * @return the latitude.
   */
  public float getLatitude()
  {
    return _latitude;
  }

  /**
   * Get the longitude.
   * @return the longitude.
   */
  public float getLongitude()
  {
    return _longitude;
  }

  @Override
  public String toString()
  {
    return "Lon="+_longitude+", lat="+_latitude+" (region "+_region+")";
  }
}
