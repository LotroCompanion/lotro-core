package delta.games.lotro.common.stats;

/**
 * Stat operator.
 * @author DAM
 */
public enum StatOperator
{
  /**
   * Set stat value.
   */
  SET,
  /**
   * Add to stat value.
   */
  ADD,
  /**
   * Substract to stat value.
   */
  SUBSTRACT,
  /**
   * Multiply stat value.
   */
  MULTIPLY;

  /**
   * Get a stat operator by name.
   * @param name Name to use.
   * @return A stat operator instance or <code>null</code> if not found.
   */
  public static StatOperator getByName(String name)
  {
    StatOperator ret=null;
    if (name!=null)
    {
      try
      {
        ret=StatOperator.valueOf(name);
      }
      catch(Exception e)
      {
        // Ignored
      }
    }
    return ret;
  }
}
