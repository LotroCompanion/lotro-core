package delta.games.lotro.lore.items;

/**
 * Proxy for an item.
 * @author DAM
 */
public class ItemProxy
{
  private int _id;
  private String _name;
  private String _icon;
  private Item _item;

  /**
   * Constructor.
   */
  public ItemProxy()
  {
    _id=0;
    _name=null;
    _icon=null;
    _item=null;
  }

  /**
   * Get the identifier of the proxied item.
   * @return an identifier or 0 if not set.
   */
  public int getId()
  {
    if (_item!=null)
    {
      return _item.getIdentifier();
    }
    return _id;
  }

  /**
   * Set the identifier of the proxied item.
   * @param id the identifier to set.
   */
  public void setId(int id)
  {
    _id=id;
  }

  /**
   * Get the icon of the proxied item.
   * @return an icon.
   */
  public String getIcon()
  {
    if (_item!=null)
    {
      return _item.getIcon();
    }
    return _icon;
  }

  /**
   * Set the icon of the proxied item.
   * @param icon the icon to set.
   */
  public void setIcon(String icon)
  {
    _icon=icon;
  }

  /**
   * Get the name of the proxied deed.
   * @return the name
   */
  public String getName()
  {
    if (_item!=null)
    {
      return _item.getName();
    }
    return _name;
  }

  /**
   * Set the name of the proxied deed.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the proxied item, if resolved.
   * @return A item or <code>null</code> if not resolved or not found.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Set the proxied item.
   * @param item the item to set.
   */
  public void setItem(Item item)
  {
    _item=item;
  }

  @Override
  public String toString()
  {
    return "Item proxy: id="+getId()+", icon="+getIcon()+", name="+getName()+", resolved="+(_item!=null);
  }
}
