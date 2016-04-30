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
}
