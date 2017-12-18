package delta.games.lotro.lore.crafting.recipes;

/**
 * Ingredient of a recipe.
 * @author DAM
 */
public class Ingredient
{
  private int _quantity;
  private boolean _optional;
  private ItemReference _item;

  /**
   * Constructor.
   */
  public Ingredient()
  {
    _quantity=1;
    _optional=false;
    _item=null;
  }

  /**
   * Get the quantity for this ingredient.
   * @return A quantity.
   */
  public int getQuantity()
  {
    return _quantity;
  }
  
  /**
   * Set the quantity for this ingredient.
   * @param quantity the quantity to set.
   */
  public void setQuantity(int quantity)
  {
    _quantity=quantity;
  }

  /**
   * Indicates if this ingredient is optional or not.
   * @return <code>true</code> if this ingredient is optional, <code>false</code> if it is mandatory.
   */
  public boolean isOptional()
  {
    return _optional;
  }

  /**
   * Set the value of the 'optional' flags.
   * @param optional <code>true</code> means 'optional', <code>false</code> means 'mandatory'.
   */
  public void setOptional(boolean optional)
  {
    _optional=optional;
  }

  /**
   * Get the ingredient item reference.
   * @return an item reference.
   */
  public ItemReference getItem()
  {
    return _item;
  }

  /**
   * Set the ingredient item reference.
   * @param item the item reference to set.
   */
  public void setItem(ItemReference item)
  {
    _item=item;
  }
  
  @Override
  public String toString()
  {
    return _item+" x"+_quantity+(_optional?" (optional)":"");
  }
}

