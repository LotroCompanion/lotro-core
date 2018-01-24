package delta.games.lotro.lore.deeds;

/**
 * Proxy for a deed.
 * @author DAM
 */
public class DeedProxy
{
  private int _id;
  private String _key;
  private String _name;
  private DeedDescription _deed;

  /**
   * Constructor.
   */
  public DeedProxy()
  {
    _id=0;
    _key=null;
    _name=null;
    _deed=null;
  }

  /**
   * Get the identifier of the proxied deed.
   * @return an identifier or 0 if not set.
   */
  public int getId()
  {
    if (_deed!=null)
    {
      return _deed.getIdentifier();
    }
    return _id;
  }

  /**
   * Set the identifier of the proxied deed.
   * @param id the identifier to set.
   */
  public void setId(int id)
  {
    _id=id;
  }

  /**
   * Get the key of the proxied deed.
   * @return a string key.
   */
  public String getKey()
  {
    if (_deed!=null)
    {
      return _deed.getKey();
    }
    return _key;
  }

  /**
   * Set the key of the proxied deed.
   * @param key the key to set.
   */
  public void setKey(String key)
  {
    _key=key;
  }

  /**
   * Get the name of the proxied deed.
   * @return the name
   */
  public String getName()
  {
    if (_deed!=null)
    {
      return _deed.getName();
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
   * Get the proxied deed, if resolved.
   * @return A deed or <code>null</code> if not resolved or not found.
   */
  public DeedDescription getDeed()
  {
    return _deed;
  }

  /**
   * Set the proxied deed.
   * @param deed the deed to set.
   */
  public void setDeed(DeedDescription deed)
  {
    _deed=deed;
  }

  @Override
  public String toString()
  {
    return "Deed proxy: id="+getId()+", key="+getKey()+", name="+getName()+", resolved="+(_deed!=null);
  }
}
