package delta.games.lotro.lore.trade.vendor;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.utils.Proxy;

/**
 * Sell list.
 * @author DAM
 */
public class SellList implements Identifiable
{
  private int _id;
  private List<Proxy<Item>> _items;

  /**
   * Constructor.
   * @param identifier
   */
  public SellList(int identifier)
  {
    _id=identifier;
    _items=new ArrayList<Proxy<Item>>();
  }

  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Add an item.
   * @param item Item to add.
   */
  public void addItem(Proxy<Item> item)
  {
    _items.add(item);
  }

  /**
   * Get items to sell.
   * @return a list of items.
   */
  public List<Proxy<Item>> getItems()
  {
    return _items;
  }
}
