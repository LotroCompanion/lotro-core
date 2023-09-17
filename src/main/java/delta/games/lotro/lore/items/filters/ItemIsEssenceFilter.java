package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Item;

/**
 * Filter to select items that are essences.
 * @author DAM
 */
public class ItemIsEssenceFilter implements ItemFilter
{
  public boolean accept(Item item)
  {
    // TODO Bad i18n
    String category=item.getSubCategory();
    return ((category!=null) && (category.startsWith("Essence")));
  }
}
