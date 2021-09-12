package delta.games.lotro.common.groupSize;

/**
 * Group size.
 * @author DAM
 */
public class GroupSize
{
  private int _code;
  private String _legacyKey;
  private String _label;

  /**
   * Constructor.
   * @param code Internal code.
   * @param legacyKey Legacy key.
   * @param label String label.
   */
  public GroupSize(int code, String legacyKey, String label)
  {
    _code=code;
    _legacyKey=legacyKey;
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
   * Get the legacy key.
   * @return A string key.
   */
  public String getLegacyKey()
  {
    return _legacyKey;
  }

  /**
   * Get the group size label.
   * @return a label.
   */
  public String getLabel()
  {
    return _label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
