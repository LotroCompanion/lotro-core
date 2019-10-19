package delta.games.lotro.character.stats.ratings;

/**
 * Rating/percentage curve for Update 21.
 * @author DAM
 */
public class Update21RatingCurveImpl implements RatingCurve
{
  private Update21RatingCurveSegment[] _segments;

  // See http://lotro-wiki.com/index.php/Rating_to_percentage_formula

  /**
   * Constructor.
   * @param segments Segments.
   */
  public Update21RatingCurveImpl(Update21RatingCurveSegment[] segments)
  {
    _segments=segments;
  }

  public Double getRatingForCap(int level)
  {
    for(Update21RatingCurveSegment segment : _segments)
    {
      boolean managed=segment.manages(level);
      if (managed)
      {
        double percentage=segment.getRatingForCap(level);
        return Double.valueOf(percentage);
      }
    }
    return null;
  }

  /**
   * Get the percentage for a given rating and level.
   * @param rating Rating.
   * @param level Level.
   * @return A percentage or <code>null</code> if not available.
   */
  public Double getPercentage(double rating, int level)
  {
    for(Update21RatingCurveSegment segment : _segments)
    {
      boolean managed=segment.manages(level);
      if (managed)
      {
        double percentage=segment.getPercentage(rating,level);
        return Double.valueOf(percentage);
      }
    }
    return null;
  }
}
