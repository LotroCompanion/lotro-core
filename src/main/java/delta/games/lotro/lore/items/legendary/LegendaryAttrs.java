package delta.games.lotro.lore.items.legendary;

/**
 * Attributes of a legendary item model.
 * @author DAM
 */
public class LegendaryAttrs
{
  private Integer _mainLegacyId;

  /**
   * Get the identifier of the main non-imbued legacy.
   * @return a non-imbued legacy identifier or <code>null</code> in case of DPS main legacy.
   */
  public Integer getMainLegacyId()
  {
    return _mainLegacyId;
  }

  /**
   * Set the identifier of the main non-imbued legacy.
   * @param mainLegacyId Identifier to set, may be <code>null</code> in case of DPS main legacy.
   */
  public void setMainLegacyId(Integer mainLegacyId)
  {
    _mainLegacyId=mainLegacyId;
  }

  @Override
  public String toString()
  {
    return "Main legacy ID: "+_mainLegacyId;
  }
}
