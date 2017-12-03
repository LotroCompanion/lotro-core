package delta.games.lotro.character.stats.ratings;

/**
 * Data for a rating curve segment.
 * @author DAM
 */
public class Update21RatingCurveSegment
{
  private int _startLevel;
  private int _endLevel;
  private double _c;
  private double _pCap;
  private double _rCapF;
  private double _rCapC;

  /**
   * Constructor.
   * @param startLevel 
   * @param endLevel 
   * @param c 
   * @param pCap 
   * @param rCapF 
   * @param rCapC 
   */
  public Update21RatingCurveSegment(int startLevel, int endLevel, double c, double pCap, double rCapF, double rCapC)
  {
    _startLevel=startLevel;
    _endLevel=endLevel;
    _c=c;
    _pCap=pCap;
    _rCapF=rCapF;
    _rCapC=rCapC;
  }

  /**
   * Get the percentage for a given rating and level.
   * @param rating Rating value.
   * @param level Level value.
   * @return A percentage or <code>null</code> if not managed.
   */
  public double getPercentage(double rating, int level)
  {
    double rCap=_rCapF*level+_rCapC;
    double p=((_c+1)/(_c+(rCap/rating)))*_pCap;
    if (p>_pCap) p=_pCap;
    return p;
  }

  /**
   * Get the rating for the percentage cap at the given level.
   * @param level Level to use.
   * @return A rating value.
   */
  public double getRatingForCap(int level)
  {
    double rCap=_rCapF*level+_rCapC;
    return rCap;
  }

  /**
   * Get the percentage cap for this curve segment.
   * @return A percentage value.
   */
  public double getPercentageCap()
  {
    return _pCap;
  }

  /**
   * Indicates if this segment manages the specified level.
   * @param level Level to test.
   * @return <code>true</code> if the given level is managed.
   */
  public boolean manages(int level)
  {
    return ((_startLevel<=level) && (level<=_endLevel));
  }
}
