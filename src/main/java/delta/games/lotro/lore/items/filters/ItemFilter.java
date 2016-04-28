package delta.games.lotro.lore.items.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.items.Item;

/**
 * Item filter.
 * @author DAM
 */
public interface ItemFilter extends Filter<Item>
{
  /**
   * Indicates if this filter accepts the given item or not.
   * @param item Item to use.
   * @return <code>true</code> to accept this item, <code>false</code> otherwise.
   */
  public boolean accept(Item item);
}
