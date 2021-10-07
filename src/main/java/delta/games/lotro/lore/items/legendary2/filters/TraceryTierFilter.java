package delta.games.lotro.lore.items.legendary2.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.items.legendary2.Tracery;

/**
 * Filter to select traceries using their tier.
 * @author DAM
 */
public class TraceryTierFilter implements Filter<Tracery>
{
  private Integer _tier;

  /**
   * Constructor.
   */
  public TraceryTierFilter()
  {
    _tier=null;
  }

  /**
   * Get the tier to select.
   * @return A tier value or <code>null</code> to select all.
   */
  public Integer getTier()
  {
    return _tier;
  }

  /**
   * Set the tier to select.
   * @param tier A tier or <code>null</code> to accept all.
   */
  public void setTier(Integer tier)
  {
    _tier=tier;
  }

  @Override
  public boolean accept(Tracery tracery)
  {
    if (_tier!=null)
    {
      Integer tier=tracery.getTier();
      return _tier.equals(tier);
    }
    return true;
  }
}
