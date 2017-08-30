package delta.games.lotro.character.stats.ratings;

/**
 * Implmementation of Mordor rating/level->percentage curves.
 * @author DAM
 */
public class MordorRatingCurveImpl implements RatingCurve
{
  // Formula: (N+1)*MAX/(N+(level*P1+P2)/R)+0,0002, with MAX percentage.

  private double _c;
  private double _p1;
  private double _p2;
  private double _maxPercentage;

  /**
   * Constructor.
   * @param c
   * @param p1
   * @param p2
   * @param maxPercentage Maximum percentage.
   */
  public MordorRatingCurveImpl(double c, double p1, double p2, double maxPercentage)
  {
    _c=c;
    _p1=p1;
    _p2=p2;
    _maxPercentage=maxPercentage;
  }

  public Double getPercentage(double rating, int level)
  {
    if (rating<=0) return Double.valueOf(0);
    double ret=_c+(((level*_p1)+_p2)/rating);
    ret=((_c+1)*(_maxPercentage/ret))+0.0002;
    ret=Math.min(ret,_maxPercentage);
    return Double.valueOf(ret);
  }
}
