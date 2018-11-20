package delta.games.lotro.utils.maths;

import delta.games.lotro.common.Identifiable;

/**
 * Interface of progressions.
 * @author DAM
 */
public interface Progression extends Identifiable
{
  /**
   * Get a Y value for a given X value. 
   * @param x X value.
   * @return A Y value or <code>null</code> if not supported.
   */
  Float getValue(int x);
}
