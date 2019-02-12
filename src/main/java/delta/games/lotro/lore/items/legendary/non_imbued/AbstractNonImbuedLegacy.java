package delta.games.lotro.lore.items.legendary.non_imbued;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.lore.items.legendary.AbstractLegacy;

/**
 * Base class for non-imbued legacies.
 * @author DAM
 */
public abstract class AbstractNonImbuedLegacy extends AbstractLegacy
{
  /**
   * Constructor.
   */
  public AbstractNonImbuedLegacy()
  {
    // Nothing
  }

  /**
   * Get the associated stat.
   * @return a stat or <code>null</code> if not set.
   */
  public abstract StatDescription getStat();
}
