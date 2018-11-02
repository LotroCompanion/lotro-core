package delta.games.lotro.lore.crafting.recipes;

import delta.games.lotro.lore.items.ItemProxy;

/**
 * Result of a recipe version.
 * @author DAM
 */
public class CraftingResult
{
  private int _quantity;
  private ItemProxy _item;
  private boolean _isCriticalResult;

  /**
   * Constructor.
   */
  public CraftingResult()
  {
    _quantity=1;
    _item=null;
    _isCriticalResult=false;
  }

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
   * Get the result item.
   * @return an item proxy.
   */
  public ItemProxy getItem()
  {
    return _item;
  }

  /**
   * Set the result item.
   * @param item the item to set.
   */
  public void setItem(ItemProxy item)
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
