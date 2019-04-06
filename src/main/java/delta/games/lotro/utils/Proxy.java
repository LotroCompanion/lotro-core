package delta.games.lotro.utils;

import delta.games.lotro.common.Identifiable;

/**
 * Proxy for an identifiable.
 * @author DAM
 * @param <T> Type of the proxied data.
 */
public class Proxy<T extends Identifiable>
{
  private int _id;
  private String _name;
  private T _data;

  /**
   * Constructor.
   */
  public Proxy()
  {
    _id=0;
    _name=null;
    _data=null;
  }

  /**
   * Get the identifier of the proxied object.
   * @return an identifier or 0 if not set.
   */
  public int getId()
  {
    if (_data!=null)
    {
      return _data.getIdentifier();
    }
    return _id;
  }

  /**
   * Set the identifier of the proxied object.
   * @param id the identifier to set.
   */
  public void setId(int id)
  {
    _id=id;
  }

  /**
   * Get the name of the proxied object.
   * @return the name
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of the proxied object.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the proxied object, if resolved.
   * @return An object or <code>null</code> if not resolved or not found.
   */
  public T getObject()
  {
    return _data;
  }

  /**
   * Set the proxied object.
   * @param object the object to set.
   */
  public void setObject(T object)
  {
    _data=object;
  }

  @Override
  public String toString()
  {
    return "Proxy: id="+getId()+", name="+getName()+", resolved="+(_data!=null);
  }
}
