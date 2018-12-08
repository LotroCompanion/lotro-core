package delta.games.lotro.plugins.kikiinventory;

import java.util.List;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.finder.ItemSelector;

/**
 * Item selector that chooses the item with category 'Barter Item', if any.
 * @author DAM
 */
public class BarterItemSelector implements ItemSelector
{
  @Override
  public Item chooseItem(List<Item> items)
  {
    Item selected=null;
    for(Item item : items)
    {
      String subCategory=item.getSubCategory();
      if ("Barter".equals(subCategory))
      {
        selected=item;
      }
    }
    return selected;
  }
}
