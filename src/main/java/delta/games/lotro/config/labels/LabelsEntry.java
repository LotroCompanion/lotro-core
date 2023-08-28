package delta.games.lotro.config.labels;

/**
 * Labels set description.
 * @author DAM
 */
public class LabelsEntry
{
  private String _key;
  private String _localeID;
  private String _label;

  /**
   * Constructor.
   * @param key Identification key.
   * @param localeID Locale identifier for data labels or app labels.
   * @param label USer display label.
   */
  public LabelsEntry(String key, String localeID, String label)
  {
    _key=key;
    _localeID=localeID;
    _label=label;
  }

  /**
   * Get the identifying key.
   * @return A key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the locale identifier.
   * @return the locale identifier.
   */
  public String getLocaleID()
  {
    return _localeID;
  }

  /**
   * Get the displayable label for this entry.
   * @return A displayable label.
   */
  public String getLabel()
  {
    return _label;
  }
}
