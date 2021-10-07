package delta.games.lotro.lore.items.legendary2.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.legendary2.Tracery;

/**
 * Filter to select essences using their tier.
 * @author DAM
 */
public class TraceryTierFilter implements Filter<Tracery>
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

  @Override
  public boolean accept(Tracery tracery)
  {
    if (_tier!=null)
    {
      Item item=tracery.getItem();
      String category=item.getSubCategory();
      return ((category!=null) && (category.endsWith(_expectedCategoryPattern)));
    }
    return true;
  }
}
