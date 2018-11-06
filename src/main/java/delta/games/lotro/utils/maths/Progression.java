package delta.games.lotro.utils.maths;

/**
 * Interface of progressions.
 * @author DAM
 */
public interface Progression
{
  /**
   * Get a Y value for a given X value. 
   * @param x X value.
   * @return A Y value or <code>null</code> if not supported.
   */
  Float getValue(int x);
}
