package delta.games.lotro.common.difficulty;

/**
 * Difficulty.
 * @author DAM
 */
public class Difficulty
{
  private int _code;
  private String _label;

  /**
   * Constructor.
   * @param code Internal code.
   * @param label String label.
   */
  public Difficulty(int code, String label)
  {
    _code=code;
    _label=label;
  }

  /**
   * Get the internal code.
   * @return A code.
   */
  public int getCode()
  {
    return _code;
  }

  /**
   * Get the difficulty label.
   * @return a label.
   */
  public String getLabel()
  {
    return _label;
  }

  @Override
  public String toString()
  {
    return "Difficulty '"+_label+"' ("+_code+')';
  }
}
