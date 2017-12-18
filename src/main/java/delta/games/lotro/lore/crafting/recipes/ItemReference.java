package delta.games.lotro.lore.crafting.recipes;

/**
 * Item reference.
 * @author DAM
 */
public class ItemReference
{
  private int _itemId;
  private String _itemKey;
  private String _name;
  private String _icon;

  /**
   * Constructor.
   */
  public ItemReference()
  {
    _itemId=0;
    _itemKey=null;
    _name=null;
    _icon=null;
  }

  /**
   * Get the item identifier.
   * @return an item key.
   */
  public int getItemId()
  {
    return _itemId;
  }

  /**
   * Set the item identifier.
   * @param itemId the identifier to set.
   */
  public void setItemId(int itemId)
  {
    _itemId=itemId;
  }

  /**
   * Get the item key.
   * @return an item key.
   */
  public String getItemKey()
  {
    return _itemKey;
  }

  /**
   * Set the item key.
   * @param itemKey the key to set.
   */
  public void setItemKey(String itemKey)
  {
    _itemKey=itemKey;
  }

  /**
   * Get the name of the referenced item.
   * @return an item name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of the referenced item.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the icon of the referenced item.
   * @return an icon path.
   */
  public String getIcon()
  {
    return _icon;
  }

  /**
   * Set the icon of the referenced item.
   * @param icon the icon path to set.
   */
  public void setIcon(String icon)
  {
    _icon=icon;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder(_name);
    if (_itemId!=0)
    {
      sb.append(" (").append(_itemId).append(')');
    }
    if (_itemKey!=null)
    {
      sb.append(" (").append(_itemKey).append(')');
    }
    if (_icon!=null)
    {
      sb.append(" (").append(_icon).append(')');
    }
    return sb.toString();
  }
}
