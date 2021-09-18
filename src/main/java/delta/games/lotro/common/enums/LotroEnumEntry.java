package delta.games.lotro.common.enums;

/**
 * Enum entry.
 * @author DAM
 */
public class LotroEnumEntry
{
  private int _code;
  private String _key;
  private String _label;

  /**
   * Constructor.
   */
  public LotroEnumEntry()
  {
    _code=0;
    _key=null;
    _label="";
  }

  void set(int code, String key, String label)
  {
    _code=code;
    _key=key;
    if (label==null)
    {
      label="";
    }
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
   * Get the key.
   * @return a key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the label.
   * @return a label.
   */
  public String getLabel()
  {
    return _label;
  }

  @Override
  public String toString()
  {
    return "'"+_label+"' ("+_code+')';
  }
}
