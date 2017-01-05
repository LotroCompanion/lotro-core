package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Item;

/**
 * Filter to select essences using their tier.
 * @author DAM
 */
public class EssenceTierFilter implements ItemFilter
{
  private String _expectedCategory;

  /**
   * Constructor.
   * @param tier Tier to select.
   */
  public EssenceTierFilter(int tier)
  {
    _expectedCategory="Essence:Tier"+tier;
  }

  public boolean accept(Item item)
  {
    String category=item.getSubCategory();
    return ((category!=null) && (category.equals(_expectedCategory)));
  }
}
