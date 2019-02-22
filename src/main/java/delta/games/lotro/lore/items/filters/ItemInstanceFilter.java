package delta.games.lotro.lore.items.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Item instance filter that uses an item filter.
 * @author DAM
 */
public class ItemInstanceFilter implements Filter<ItemInstance<? extends Item>>
{
  private Filter<Item> _itemFilter;

  /**
   * Constructor.
   * @param itemFilter Embedded item filter.
   */
  public ItemInstanceFilter(Filter<Item> itemFilter)
  {
    _itemFilter=itemFilter;
  }

  @Override
  public boolean accept(ItemInstance<? extends Item> item)
  {
    return _itemFilter.accept(item.getReference());
  }
}
