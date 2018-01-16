package delta.games.lotro.common.objects;

/**
 * Object item.
 * @author DAM
 */
public class ObjectItem
{
  private int _itemId;
  private String _name;
  private String _iconURL;
  private String _objectURL;

  /**
   * Constructor.
   * @param name Name of object.
   */
  public ObjectItem(String name)
  {
    _name=name;
  }

  /**
   * Get the name of this object.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the identifier of this object.
   * @return the identifier of this object (0 if not set).
   */
  public int getItemId()
  {
    return _itemId;
  }

  /**
   * Set the identifier of this object.
   * @param itemId Identifier to set.
   */
  public void setItemId(int itemId)
  {
    _itemId=itemId;
  }

  /**
   * Get the URL of the object's icon.
   * @return An icon URL.
   */
  public String getIconURL()
  {
    return _iconURL;
  }

  /**
   * Set the URL of the object's icon.
   * @param iconURL URL to set.
   */
  public void setIconURL(String iconURL)
  {
    _iconURL=iconURL;
  }

  /**
   * Get the URL of the object's description page.
   * @return An object description page URL.
   */
  public String getObjectURL()
  {
    return _objectURL;
  }

  /**
   * Set the URL of the object's description page.
   * @param objectURL URL to set.
   */
  public void setObjectURL(String objectURL)
  {
    _objectURL=objectURL;
  }

  @Override
  public String toString()
  {
    return _itemId+":"+_name;
  }
}
