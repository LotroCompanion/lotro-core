package delta.games.lotro.lore.deeds;

import delta.games.lotro.utils.Proxy;

/**
 * Proxy for a deed.
 * @author DAM
 */
public class DeedProxy extends Proxy<DeedDescription>
{
  private String _key;

  /**
   * Constructor.
   */
  public DeedProxy()
  {
    super();
    _key=null;
  }

  /**
   * Get the key of the proxied deed.
   * @return a string key.
   */
  public String getKey()
  {
    DeedDescription deed=getObject();
    if (deed!=null)
    {
      return deed.getKey();
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

  @Override
  public String toString()
  {
    DeedDescription deed=getObject();
    return "Deed proxy: id="+getId()+", key="+getKey()+", name="+getName()+", resolved="+(deed!=null);
  }
}
