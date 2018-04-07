package delta.games.lotro.lore.deeds;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of deed proxies.
 * @author DAM
 */
public class DeedProxies
{
  private List<DeedProxy> _proxies;

  /**
   * Constructor.
   */
  public DeedProxies()
  {
    _proxies=new ArrayList<DeedProxy>();
  }

  /**
   * Get the managed proxies.
   * @return A possibly empty, but not <code>null</code> list of proxies.
   */
  public List<DeedProxy> getDeedProxies()
  {
    return _proxies;
  }

  /**
   * Get the first proxy, if any.
   * @return A proxy or <code>null</code> if none.
   */
  public DeedProxy getFirst()
  {
    int nbProxies=_proxies.size();
    if (nbProxies>0)
    {
      return _proxies.get(0);
    }
    return null;
  }

  /**
   * Add a proxy.
   * @param proxy Proxy to add.
   */
  public void add(DeedProxy proxy)
  {
    _proxies.add(proxy);
  }

  /**
   * Remove a proxy.
   * @param proxy Proxy to remove.
   */
  public void remove(DeedProxy proxy)
  {
    if (proxy!=null)
    {
      _proxies.remove(proxy);
    }
  }

  /**
   * Remove a proxy using its key.
   * @param key Key of the proxy to remove.
   */
  public void remove(String key)
  {
    DeedProxy toRemove=getByKey(key);
    remove(toRemove);
  }

  /**
   * Get a proxy by name.
   * @param name Name of the proxied item.
   * @return A proxy or <code>null</code> if not found.
   */
  public DeedProxy getByName(String name)
  {
    DeedProxy ret=null;
    for(DeedProxy proxy : _proxies)
    {
      if (name.equals(proxy.getName()))
      {
        ret=proxy;
        break;
      }
    }
    return ret;
  }

  /**
   * Get a proxy by key.
   * @param key Key of the proxied item.
   * @return A proxy or <code>null</code> if not found.
   */
  public DeedProxy getByKey(String key)
  {
    DeedProxy ret=null;
    for(DeedProxy proxy : _proxies)
    {
      if (key.equals(proxy.getKey()))
      {
        ret=proxy;
        break;
      }
    }
    return ret;
  }
}
