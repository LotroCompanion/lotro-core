package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Item;

/**
 * Filter to select essences using their tier.
 * @author DAM
 */
public class EssenceTierFilter implements ItemFilter
{
  private Integer _tier;
  private String _expectedCategory;

  /**
   * Constructor.
   */
  public EssenceTierFilter()
  {
    _tier=null;
    _expectedCategory=null;
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
      _expectedCategory="Essence:Tier"+tier;
    }
    else
    {
      _expectedCategory=null;
    }
    _tier=tier;
  }

  public boolean accept(Item item)
  {
    if (_tier!=null)
    {
      String category=item.getSubCategory();
      return ((category!=null) && (category.equals(_expectedCategory)));
    }
    return true;
  }
}
