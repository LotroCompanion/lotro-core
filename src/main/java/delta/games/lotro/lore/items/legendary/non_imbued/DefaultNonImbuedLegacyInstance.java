package delta.games.lotro.lore.items.legendary.non_imbued;

/**
 * Default, non-imbued legacy instance.
 * @author DAM
 */
public class DefaultNonImbuedLegacyInstance extends NonImbuedLegacyInstance
{
  private DefaultNonImbuedLegacy _legacy;

  /**
   * Constructor.
   */
  public DefaultNonImbuedLegacyInstance()
  {
    super();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public DefaultNonImbuedLegacyInstance(DefaultNonImbuedLegacyInstance source)
  {
    super();
    _legacy=source._legacy;
  }

  /**
   * Get the associated legacy.
   * @return the associated legacy.
   */
  public DefaultNonImbuedLegacy getLegacy()
  {
    return _legacy;
  }

  /**
   * Set the associated legacy.
   * @param legacy the legacy to set.
   */
  public void setLegacy(DefaultNonImbuedLegacy legacy)
  {
    _legacy=legacy;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_legacy!=null?_legacy.toString():"?");
    sb.append(", rank=").append(getRank());
    return sb.toString();
  }
}
