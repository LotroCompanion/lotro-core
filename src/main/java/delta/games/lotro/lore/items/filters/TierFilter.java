package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Item;

/**
 * Filter to select items using their tier.
 * @author DAM
 */
public class TierFilter implements ItemFilter
{
  private Integer _tier;

  /**
   * Constructor.
   */
  public TierFilter()
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

  public boolean accept(Item item)
  {
    Integer tier=item.getTier();
    if (_tier!=null)
    {
      return _tier.equals(tier);
    }
    return true;
  }
}
