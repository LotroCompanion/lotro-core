package delta.games.lotro.lore.geo;

import java.awt.geom.Point2D;

/**
 * Geographic bounding box.
 * @author DAM
 */
public class GeoBoundingBox
{
  private Point2D.Float _min;
  private Point2D.Float _max;

  /**
   * Constructor.
   * @param lon1 Longitude of point 1.
   * @param lat1 Latitude of point 1.
   * @param lon2 Longitude of point 2.
   * @param lat2 Latitude of point 2.
   */
  public GeoBoundingBox(float lon1, float lat1, float lon2, float lat2)
  {
    float minLat=Math.min(lat1,lat2);
    float maxLat=Math.max(lat1,lat2);
    float minLon=Math.min(lon1,lon2);
    float maxLon=Math.max(lon1,lon2);
    _min=new Point2D.Float(minLon,minLat);
    _max=new Point2D.Float(maxLon,maxLat);
  }

  /**
   * Get the minimum point of this box (most south-western point).
   * @return a point.
   */
  public Point2D.Float getMin()
  {
    return _min;
  }

  /**
   * Get the maximum point of this box (most north-eastern point).
   * @return a point.
   */
  public Point2D.Float getMax()
  {
    return _max;
  }

  /**
   * Extend this box so that it includes the given box.
   * @param inputBox Box to include.
   */
  public void extend(GeoBoundingBox inputBox)
  {
    Point2D.Float inputMin=inputBox.getMin();
    if ((inputMin.y<_min.y) || (inputMin.x<_min.x))
    {
      float minLat=Math.min(inputMin.y,_min.y);
      float minLon=Math.min(inputMin.x,_min.x);
      _min=new Point2D.Float(minLon,minLat);
    }
    Point2D.Float inputMax=inputBox.getMax();
    if ((inputMax.y>_max.y) || (inputMax.x>_max.x))
    {
      float maxLat=Math.max(inputMax.y,_max.y);
      float maxLon=Math.max(inputMax.x,_max.x);
      _max=new Point2D.Float(maxLon,maxLat);
    }
  }

  @Override
  public String toString()
  {
    return "min="+_min+",max="+_max;
  }
}
