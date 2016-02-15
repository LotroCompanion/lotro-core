package delta.games.lotro.character.stats;

/**
 * Data for a rating curve segment.
 * @author DAM
 */
public class RatingCurveSegment
{
  private double _startP;
  private double _endP;
  private double _startRL;
  private double _endRL;
  private double _K;

  /**
   * Constructor.
   * @param startP Start percentage.
   * @param dp Delta percentage.
   * @param K Factor/slope.
   * @param startRL Start of rating/level value.
   * @param dRL Delta for rating/level.
   */
  public RatingCurveSegment(double startP, double dp, double K, double startRL, double dRL)
  {
    _startP=startP;
    _endP=startP+dp;
    _K=K;
    _startRL=startRL;
    _endRL=startRL+dRL;
  }

  /**
   * Get the maximum rating/level value.
   * @return a rating/level value.
   */
  public double getMaxRL()
  {
    return _endRL;
  }

  /**
   * Get the percentage for a given rating and level.
   * @param rating Rating value.
   * @param level Level value.
   * @return A percentage or <code>null</code> if not managed.
   */
  public Double getPercentage(double rating, int level)
  {
    if (doesManage(rating,level))
    {
      double p=_startP+100/(1+_K/((rating/level)-_startRL));
      return Double.valueOf(p);
    }
    return null;
  }

  /**
   * Indicates if the given rating and level are managed
   * (i.e. if a percentage can be associated to those values).
   * @param rating Rating value.
   * @param level Level value.
   * @return <code>true</code> if managed, <code>false</code> otherwise.
   * 
   */
  private boolean doesManage(double rating, int level)
  {
    double rl=rating/level;
    return ((rl>=_startRL)&&(rl<=_endRL));
  }

  /**
   * Get the percentage for a given percentage and level.
   * @param percentage Percentage value.
   * @param level Level value.
   * @return A rating or <code>null</code> if not managed.
   */
  public Double getRating(double percentage, int level)
  {
    if (doesManage(percentage))
    {
      double rl=_startRL+_K*(percentage-_startP)/(1-(percentage-_startP));
      return Double.valueOf(rl*level);
    }
    return null;
  }

  /**
   * Indicates if the given percentage is managed
   * (i.e. if a rating can be associated to this value).
   * @param percentage Rating value.
   * @return <code>true</code> if managed, <code>false</code> otherwise.
   * 
   */
  private boolean doesManage(double percentage)
  {
    return ((percentage>=_startP)&&(percentage<=_endP));
  }
}
