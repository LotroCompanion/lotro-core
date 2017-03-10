package delta.games.lotro.lore.recipes;

/**
 * Result of a recipe version.
 * @author DAM
 */
public class CraftingResult
{
  private int _quantity;
  private ItemReference _item;
  private boolean _isCriticalResult;

  /**
   * Get the quantity of result items.
   * @return A quantity.
   */
  public int getQuantity()
  {
    return _quantity;
  }

  /**
   * Set the quantity of result items.
   * @param quantity the quantity to set.
   */
  public void setQuantity(int quantity)
  {
    _quantity=quantity;
  }

  /**
   * Get the result item reference.
   * @return an item reference.
   */
  public ItemReference getItem()
  {
    return _item;
  }

  /**
   * Set the result item reference.
   * @param item the item reference to set.
   */
  public void setItem(ItemReference item)
  {
    _item=item;
  }

  /**
   * Indicates if this is the critical result or
   * the regular one.
   * @return <code>true</code> indicates a critical result,
   * <code>false</code> a regular result.
   */
  public boolean isCriticalResult()
  {
    return _isCriticalResult;
  }

  /**
   * Set the critical result flag.
   * @param isCriticalResult Value to set.
   */
  public void setCriticalResult(boolean isCriticalResult)
  {
    _isCriticalResult=isCriticalResult;
  }
  
  @Override
  public String toString()
  {
    return _item+" x"+_quantity+(_isCriticalResult?" (critical)":"");
  }
}
