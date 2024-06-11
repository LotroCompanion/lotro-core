package delta.games.lotro.utils.style;

/**
 * Title style.
 * @author DAM
 */
public class TitleStyle implements StyleElement
{
  private int _level;

  /**
   * Constructor.
   * @param level Level.
   */
  public TitleStyle(int level)
  {
    _level=level;
  }

  /**
   * Get the level.
   * @return the level.
   */
  public int getLevel()
  {
    return _level;
  }

  @Override
  public String toString()
  {
    return "Title style: level="+_level;
  }
}
