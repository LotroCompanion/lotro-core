package delta.games.lotro.lore.items.finder;

import java.util.List;

import delta.games.lotro.lore.items.Item;

/**
 * Interface of an object that can choose an item
 * among a list of items.
 * @author DAM
 */
public interface ItemSelector
{
  /**
   * Choose an item.
   * @param items Items to choose from.
   * @return the selected item, or <code>null</code>.
   */
  public Item chooseItem(List<Item> items);
}
