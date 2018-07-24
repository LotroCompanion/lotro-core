package delta.games.lotro.character.storage;

/**
 * Stored item.
 * @author DAM
 */
public class StoredItem
{
  private String _name;
  private int _quantity;
  private Integer _iconId;
  private Integer _backgroundIconId;

  /**
   * Constructor.
   * @param name Item name.
   */
  public StoredItem(String name)
  {
    _name=name;
    _quantity=0;
    _iconId=null;
    _backgroundIconId=null;
  }

  /**
   * Get the name of the item.
   * @return an item name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the item quantity.
   * @return a quantity.
   */
  public int getQuantity()
  {
    return _quantity;
  }

  /**
   * Set the item quantity.
   * @param quantity the quantity to set.
   */
  public void setQuantity(int quantity)
  {
    _quantity=quantity;
  }

  /**
   * Get the foreground icon ID.
   * @return an icon ID or <code>null</code> if not set.
   */
  public Integer getIconId()
  {
    return _iconId;
  }

  /**
   * Set the foreground icon ID.
   * @param iconId the ID to set (may be <code>null</code>).
   */
  public void setIconId(Integer iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the backgrund icon ID.
   * @return an icon ID or <code>null</code> if not set.
   */
  public Integer getBackgroundIconId()
  {
    return _backgroundIconId;
  }

  /**
   * Set the background icon ID.
   * @param backgroundIconId the ID to set (may be <code>null</code>).
   */
  public void setBackgroundIconId(Integer backgroundIconId)
  {
    _backgroundIconId=backgroundIconId;
  }
}
