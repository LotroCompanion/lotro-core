package delta.games.lotro.character.stats.buffs;

/**
 * Stats contributions from hope/dread.
 * @author DAM
 */
public class MoraleFromHopeOrDread
{
  private static final float[] MORALE_FRACTION = {
      // Dread: -15 -> -1
      -0.99f, -0.97f, -0.95f, -0.90f, -0.85f, -0.80f, -0.65f,
      -0.60f, -0.50f, -0.40f, -0.30f, -0.20f, -0.15f, -0.10f, -0.05f,
      // Neutral
      0,
      // Hope: 1 -> 15
      0.01f, 0.02f, 0.03f, 0.04f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f,
      0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f
  };

  /**
   * Get the morale factor associated to the given hope/dread level.
   * @param hopeDreadLevel Hope/dread level.
   * @return A morale factor.
   */
  public float getMoraleFactor(int hopeDreadLevel)
  {
    if (hopeDreadLevel!=0)
    {
      float factor=MORALE_FRACTION[hopeDreadLevel+15]+1;
      return factor;
    }
    return 1.0f;
  }
}
