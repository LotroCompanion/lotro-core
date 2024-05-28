package delta.games.lotro.common.stats;

/**
 * Description of a stat of type "float".
 * @author DAM
 */
public class FloatStatDescription extends StatDescription
{
  private int _maxDigitsAbove1;
  private int _maxDigitsBelow1;

  /**
   * Constructor.
   */
  public FloatStatDescription()
  {
    super();
    _maxDigitsAbove1=0;
    _maxDigitsBelow1=2;
    setType(StatType.FLOAT);
  }

  /**
   * Get the maximum number of digits for values above 1.
   * @return A digits count.
   */
  public int getMaxDigitsAbove1()
  {
    return _maxDigitsAbove1;
  }

  /**
   * Set the maximum number of digits for values above 1.
   * @param digits Digits count.
   */
  public void setMaxDigitsAbove1(int digits)
  {
    _maxDigitsAbove1=digits;
  }

  /**
   * Get the maximum number of digits for values below 1.
   * @return A digits count.
   */
  public int getMaxDigitsBelow1()
  {
    return _maxDigitsBelow1;
  }

  /**
   * Set the maximum number of digits for values below 1.
   * @param digits Digits count.
   */
  public void setMaxDigitsBelow1(int digits)
  {
    _maxDigitsBelow1=digits;
  }
}
