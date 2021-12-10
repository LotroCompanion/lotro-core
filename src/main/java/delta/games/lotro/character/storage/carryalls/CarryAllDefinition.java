package delta.games.lotro.character.storage.carryalls;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.items.Item;

/**
 * Definition of a carry-all.
 * @author DAM
 */
public class CarryAllDefinition implements Identifiable
{
  private Item _item;
  private int _maxItems;
  private int _stackMax;

  /**
   * Constructor.
   * @param item Item.
   * @param maxItems Maximum number of items.
   * @param stackMax Maximum stack size.
   */
  public CarryAllDefinition(Item item, int maxItems, int stackMax)
  {
    _item=item;
    _maxItems=maxItems;
    _stackMax=stackMax;
  }

  @Override
  public int getIdentifier()
  {
    return (_item!=null)?_item.getIdentifier():0;
  }

  /**
   * Get the carry-all name.
   * @return A name.
   */
  public String getName()
  {
    return (_item!=null)?_item.getName():null;
  }

  /**
   * Get the item.
   * @return the item
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the maximum number of items in this carry-all.
   * @return an items count.
   */
  public int getMaxItems()
  {
    return _maxItems;
  }

  /**
   * Get the maximum size of each stack.
   * @return a stack size.
   */
  public int getStackMax()
  {
    return _stackMax;
  }
}
