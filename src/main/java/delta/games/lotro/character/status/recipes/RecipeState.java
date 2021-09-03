package delta.games.lotro.character.status.recipes;

/**
 * Recipe status (automatically known, learnt, unknown).
 * @author DAM
 */
public enum RecipeState
{
  /**
   * Auto.
   */
  AUTO("Auto"),
  /**
   * Learnt.
   */
  LEARNT("Learnt"),
  /**
   * Not known.
   */
  NOT_KNOWN("Not Known");

  private String _label;
  private RecipeState(String label)
  {
    _label=label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
