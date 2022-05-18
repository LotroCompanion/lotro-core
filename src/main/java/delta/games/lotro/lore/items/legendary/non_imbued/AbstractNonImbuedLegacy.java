package delta.games.lotro.lore.items.legendary.non_imbued;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.lore.items.legendary.AbstractLegacy;

/**
 * Base class for non-imbued legacies.
 * @author DAM
 */
public abstract class AbstractNonImbuedLegacy extends AbstractLegacy
{
  private int _imbuedLegacyId;

  /**
   * Constructor.
   */
  protected AbstractNonImbuedLegacy()
  {
    // Nothing
  }

  /**
   * Get the associated stat.
   * @return a stat or <code>null</code> if not set.
   */
  public abstract StatDescription getStat();

  /**
   * Get the identifier of the associated imbued legacy.
   * @return an imbued legacy identifier.
   */
  public int getImbuedLegacyId()
  {
    return _imbuedLegacyId;
  }

  /**
   * Set the identifier of the associated imbued legacy.
   * @param imbuedlegacyId Identifier to set.
   */
  public void setImbuedLegacyId(int imbuedlegacyId)
  {
    _imbuedLegacyId=imbuedlegacyId;
  }
}
