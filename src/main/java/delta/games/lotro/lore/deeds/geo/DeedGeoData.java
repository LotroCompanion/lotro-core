package delta.games.lotro.lore.deeds.geo;

import java.util.ArrayList;
import java.util.List;

/**
 * Geographic data for a deed.
 * @author DAM
 */
public class DeedGeoData
{
  private int _requiredPoints;
  private List<DeedGeoPoint> _points;

  /**
   * Constructor.
   * @param requiredPoints Number of required points.
   */
  public DeedGeoData(int requiredPoints)
  {
    _requiredPoints=requiredPoints;
    _points=new ArrayList<DeedGeoPoint>();
  }

  /**
   * Add a point.
   * @param point Point to add.
   */
  public void addPoint(DeedGeoPoint point)
  {
    _points.add(point);
  }

  /**
   * Get all the points for this deed.
   * @return A list of points.
   */
  public List<DeedGeoPoint> getPoints()
  {
    return _points;
  }

  /**
   * Get the number of points required to complete the deed.
   * @return A points count.
   */
  public int getRequiredPoints()
  {
    return _requiredPoints;
  }
}
