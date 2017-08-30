package delta.games.lotro.character.stats.ratings;

/**
 * Compound rating curve to handle 105/106 rating curves switch.
 * @author DAM
 */
public class CompoundRatingCurve implements RatingCurve
{
  private RatingCurve _until105;
  private RatingCurve _after106;

  /**
   * Constructor.
   * @param until105 Curve to use until level 105.
   * @param after106 Curve to use after 106.
   */
  public CompoundRatingCurve(RatingCurve until105, RatingCurve after106)
  {
    _until105=until105;
    _after106=after106;
  }

  public Double getPercentage(double rating, int level)
  {
    RatingCurve toUse=(level<=105)?_until105:_after106;
    return toUse.getPercentage(rating,level);
  }
}
