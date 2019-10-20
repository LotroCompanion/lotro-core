package delta.games.lotro.character.stats.ratings;

import delta.games.lotro.utils.maths.Progression;

/**
 * Rating curve implementation based on progressions.
 * @author DAM
 */
public class ProgressionRatingCurveImpl implements RatingCurve
{
  private Progression _hardCap;
  private Progression _rating;
  private Progression _targetCap;

  /**
   * Constructor.
   * @param hardCap Hard cap progression.
   * @param rating Rating progression.
   * @param targetCap Target cap progression.
   */
  public ProgressionRatingCurveImpl(Progression hardCap, Progression rating, Progression targetCap)
  {
    _hardCap=hardCap;
    _rating=rating;
    _targetCap=targetCap;
  }

  /**
   * Get the 'hard cap' progression.
   * @return the 'hard cap' progression.
   */
  public Progression getHardCapProgression()
  {
    return _hardCap;
  }

  /**
   * Get the 'rating' progression.
   * @return the 'rating' progression.
   */
  public Progression getRatingProgression()
  {
    return _rating;
  }

  /**
   * Get the 'target cap' progression.
   * @return the 'target cap' progression.
   */
  public Progression getTargetCapProgression()
  {
    return _targetCap;
  }

  @Override
  public Double getPercentage(double rating, int level)
  {
    Float hardCapPoint=_hardCap.getValue(level);
    Float ratingPoint=_rating.getValue(level);
    Float targetCapPoint=_targetCap.getValue(level);
    if ((hardCapPoint!=null) && (ratingPoint!=null) && (targetCapPoint!=null))
    {
      float percentage=targetCapPoint.floatValue()/(1+(ratingPoint.floatValue()/(float)rating));
      percentage=Math.min(percentage,hardCapPoint.floatValue());
      return Double.valueOf(percentage*100);
    }
    return null;
  }

  @Override
  public Double getRatingForCap(int level)
  {
    // B
    Float ratingPoint=_rating.getValue(level);
    // C
    Float hardCapPoint=_hardCap.getValue(level);
    Float targetCapPoint=_targetCap.getValue(level);
    if ((hardCapPoint!=null) && (ratingPoint!=null) && (targetCapPoint!=null))
    {
      return Double.valueOf(ratingPoint.doubleValue()*(hardCapPoint.doubleValue()/(targetCapPoint.doubleValue()-hardCapPoint.doubleValue())));
    }
    return null;
  }
}
