package delta.games.lotro.character.storage.carryalls;

import delta.games.lotro.common.Identifiable;

/**
 * Definition of a carry-all.
 * @author DAM
 */
public class CarryAllDefinition implements Identifiable
{
  private int _itemId;
  private int _maxItems;
  private int _stackMax;

  /**
   * Constructor.
   * @param itemId Item identifier.
   * @param maxItems Maximum number of items.
   * @param stackMax Maximum stack size.
   */
  public CarryAllDefinition(int itemId, int maxItems, int stackMax)
  {
    _itemId=itemId;
    _maxItems=maxItems;
    _stackMax=stackMax;
  }

  @Override
  public int getIdentifier()
  {
    return _itemId;
  }

  /**
   * Get the item identifier.
   * @return the itemId
   */
  public int getItemId()
  {
    return _itemId;
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
