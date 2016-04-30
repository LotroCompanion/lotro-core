package delta.games.lotro.character.stats.ratings;

/**
 * Curved rating/percentage curve.
 * @author DAM
 */
public class CurvedRatingCurveImpl implements RatingCurve
{
  private RatingCurveSegment[] _segments;
  private double _maxRL;
  private double _maxP;

  // See http://lotro-wiki.com/index.php/Rating_to_percentage_formula

  /**
   * Constructor.
   * @param data Curve data.
   */
  public CurvedRatingCurveImpl(double[][] data)
  {
    int nbSegments=data.length;
    _segments=new RatingCurveSegment[nbSegments];
    double startP=0.0;
    double startRL=0.0;
    for(int i=0;i<nbSegments;i++)
    {
      double dp=data[i][0];
      double K=data[i][1];
      double dRL=data[i][2];
      RatingCurveSegment segment=new RatingCurveSegment(startP,dp,K,startRL,dRL);
      _segments[i]=segment;
      _maxP=startP+dp;
      startP=_maxP;
      _maxRL=startRL+dRL;
      startRL=_maxRL;
    }
  }

  /**
   * Get the percentage for a given rating and level.
   * @param rating Rating.
   * @param level Level.
   * @return A percentage or <code>null</code> if not available.
   */
  public Double getPercentage(double rating, int level)
  {
    double rl=rating/level;
    if (rl<=_maxRL)
    {
      for(RatingCurveSegment cs:_segments)
      {
        double max=cs.getMaxRL();
        if (rl<=max)
        {
          Double p=cs.getPercentage(rating,level);
          return p;
        }
      }
    }
    // Shall be not null if rating is >=0.0
    return Double.valueOf(_maxP);
  }
}
