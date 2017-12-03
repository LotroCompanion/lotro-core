package delta.games.lotro.character.stats.ratings;

/**
 * Rating/percentage curve.
 * @author DAM
 */
public interface RatingCurve
{
  /**
   * Get the percentage for a given rating and level.
   * @param rating Rating.
   * @param level Level.
   * @return A percentage or <code>null</code> if not available.
   */
  public Double getPercentage(double rating, int level);

  /**
   * Get the rating for the percentage cap at the given level.
   * @param level Level to use.
   * @return A rating value or <code>null</code> if not available.
   */
  public Double getRatingForCap(int level);

  /**
   * Get the percentage cap at the given level.
   * @param level Level to use.
   * @return A percentage value or <code>null</code> if not available.
   */
  public Double getPercentageCap(int level);
}
