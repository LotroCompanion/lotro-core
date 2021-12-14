package delta.games.lotro.character.status.achievables;

/**
 * Progress.
 * <p>
 * It stores:
 * <ul>
 * <li>a current value,
 * <li>a maximum value,
 * <li>a percentage value computed from those 2.
 * </ul>
 * @author DAM
 */
public class Progress
{
  private int _max;
  private int _current;
  private int _percentage;

  /**
   * Constructor.
   * @param current Current value.
   * @param max Maximum value.
   */
  public Progress(int current, int max)
  {
    _current=current;
    _max=max;
    _percentage=computePercentage();
  }

  /**
   * Get the current value.
   * @return a value.
   */
  public int getCurrent()
  {
    return _current;
  }

  /**
   * Get the maximum value.
   * @return a value.
   */
  public int getMax()
  {
    return _max;
  }

  /**
   * Get the progress percentage.
   * @return A percentage value.
   */
  public int getPercentage()
  {
    return _percentage;
  }

  private int computePercentage()
  {
    if (_current>=_max)
    {
      return 100;
    }
    return (int)((100f*_current)/_max);
  }
}
