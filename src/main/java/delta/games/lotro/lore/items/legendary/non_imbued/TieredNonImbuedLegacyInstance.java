package delta.games.lotro.lore.items.legendary.non_imbued;

/**
 * Tiered, non-imbued legacy instance.
 * @author DAM
 */
public class TieredNonImbuedLegacyInstance extends NonImbuedLegacyInstance
{
  private NonImbuedLegacyTier _legacyTier;

  /**
   * Get the associated legacy tier.
   * @return the associated legacy tier.
   */
  public NonImbuedLegacyTier getLegacyTier()
  {
    return _legacyTier;
  }

  /**
   * Set the associated legacy tier.
   * @param legacyTier the legacy tier to set.
   */
  public void setLegacyTier(NonImbuedLegacyTier legacyTier)
  {
    _legacyTier=legacyTier;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_legacyTier!=null?_legacyTier.toString():"?");
    sb.append(", rank=").append(getRank());
    return sb.toString();
  }
}
