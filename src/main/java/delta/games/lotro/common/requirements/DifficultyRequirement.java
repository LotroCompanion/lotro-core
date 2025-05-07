package delta.games.lotro.common.requirements;

/**
 * Difficulty requirement.
 * @author DAM
 */
public class DifficultyRequirement implements Requirement
{
  private int _difficulty;

  /**
   * Constructor.
   * @param difficulty Difficulty.
   */
  public DifficultyRequirement(int difficulty)
  {
    _difficulty=difficulty;
  }

  /**
   * Get the difficulty.
   * @return the difficulty.
   */
  public int getDifficulty()
  {
    return _difficulty;
  }
}
