package delta.games.lotro.utils.maths;

/**
 * Base class for progressions.
 * @author DAM
 */
public class AbstractProgression
{
  private int _identifier;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public AbstractProgression(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the progression identifier.
   * @return the progression identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }
}
