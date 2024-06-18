package delta.games.lotro.character.status.traitPoints;

import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.utils.maths.Progression;

/**
 * Utility methods for trait points.
 * @author DAM
 */
public class TraitPoints
{
  /**
   * Get the number of trait points for a character level.
   * @param characterLevel Level to use.
   * @return A points count.
   */
  public static int getTraitPointsFromLevel(int characterLevel)
  {
    // TODO: Avoid hard coded progression ID
    Progression progression=ProgressionsManager.getInstance().getProgression(1879271247);
    Float value=progression.getValue(characterLevel);
    return value!=null?value.intValue():0;
  }
}
