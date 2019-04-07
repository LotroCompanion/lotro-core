package delta.games.lotro.common.objects;

/**
 * Object item.
 * @author DAM
 */
public class ObjectItem
{
  private int _itemId;
  private String _name;

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
   * Set the name of the referenced item.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    _name=name;
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

  @Override
  public String toString()
  {
    return _itemId+":"+_name;
  }
}
