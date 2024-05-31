package delta.games.lotro.lore.mood;

/**
 * Mood entry.
 * @author DAM
 */
public class MoodEntry
{
  private int _level;
  private float _moraleModifier;

  /**
   * Constructor.
   * @param level Level (positive for hope, negative for dread).
   * @param moraleModifier Morale modifier.
   */
  public MoodEntry(int level, float moraleModifier)
  {
    _level=level;
    _moraleModifier=moraleModifier;
  }

  /**
   * Get the mood level.
   * @return a level.
   */
  public int getLevel()
  {
    return _level;
  }

  /**
   * Get the morale modifier.
   * @return the morale modifier.
   */
  public float getMoraleModifier()
  {
    return _moraleModifier;
  }
}
