package delta.games.lotro.lore.items.legendary2.filters;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.filters.ItemFilter;

/**
 * Filter to select essences using their tier.
 * @author DAM
 */
public class TraceryTierFilter implements ItemFilter
{
  /**
   * Tier pattern.
   */
  public static final String TIER_PATTERN=":Tier";

  private Integer _tier;
  private String _expectedCategoryPattern;

  /**
   * Constructor.
   */
  public TraceryTierFilter()
  {
    _tier=null;
    _expectedCategoryPattern=null;
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
    if (tier!=null)
    {
      _expectedCategoryPattern=TIER_PATTERN+tier;
    }
    else
    {
      _expectedCategoryPattern=null;
    }
    _tier=tier;
  }

  public boolean accept(Item item)
  {
    if (_tier!=null)
    {
      String category=item.getSubCategory();
      return ((category!=null) && (category.endsWith(_expectedCategoryPattern)));
    }
    return true;
  }
}
