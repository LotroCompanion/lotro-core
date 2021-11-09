package delta.games.lotro.lore.items.containers;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.items.Item;

/**
 * Preview data for a container.
 * @author DAM
 */
public class ContainerPreview
{
  private List<Item> _items;

  /**
   * Constructor.
   */
  public ContainerPreview()
  {
    _items=new ArrayList<Item>();
  }

  /**
   * Add an item.
   * @param item Item to add.
   */
  public void addItem(Item item)
  {
    _items.add(item);
  }

  /**
   * Get the items to preview.
   * @return A list of items.
   */
  public List<Item> getItems()
  {
    return new ArrayList<Item>(_items);
  }
}
