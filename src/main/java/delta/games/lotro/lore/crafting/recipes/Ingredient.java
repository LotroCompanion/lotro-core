package delta.games.lotro.lore.crafting.recipes;

import delta.games.lotro.lore.items.ItemProxy;

/**
 * Ingredient of a recipe.
 * @author DAM
 */
public class Ingredient
{
  private int _quantity;
  private boolean _optional;
  private ItemProxy _item;
  private Integer _criticalChanceBonus;

  /**
   * Constructor.
   */
  public Ingredient()
  {
    _quantity=1;
    _optional=false;
    _item=null;
    _criticalChanceBonus=null;
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
   * Get the ingredient item proxy.
   * @return an item proxy.
   */
  public ItemProxy getItem()
  {
    return _item;
  }

  /**
   * Set the ingredient item proxy.
   * @param item the item proxy to set.
   */
  public void setItem(ItemProxy item)
  {
    _item=item;
  }

  /**
   * Get the ingredient name.
   * @return a name or <code>null</code>.
   */
  public String getName()
  {
    String ret=null;
    if (_item!=null)
    {
      ret=_item.getName();
    }
    return ret;
  }

  /**
   * Get the bonus percentage for critical success.
   * @return a percentage value (1..100) or <code>null</code>.
   */
  public Integer getCriticalChanceBonus()
  {
    return _criticalChanceBonus;
  }

  /**
   * Set the bonus percentage for critical success.
   * @param criticalChanceBonus a percentage value (1..100) or <code>null</code>.
   */
  public void setCriticalChanceBonus(Integer criticalChanceBonus)
  {
    _criticalChanceBonus=criticalChanceBonus;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_item);
    if (_quantity!=1)
    {
      sb.append(" x");
      sb.append(_quantity);
    }
    if (_optional)
    {
      sb.append(" (optional");
      if (_criticalChanceBonus!=null)
      {
        sb.append(" ").append(_criticalChanceBonus).append('%');
      }
      sb.append(')');
    }
    return sb.toString();
  }
}
