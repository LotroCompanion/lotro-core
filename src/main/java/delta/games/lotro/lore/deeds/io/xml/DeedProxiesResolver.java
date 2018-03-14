package delta.games.lotro.lore.deeds.io.xml;

import java.util.HashMap;
import java.util.List;

import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedProxy;

/**
 * Resolver for deed proxies.
 * @author DAM
 */
public class DeedProxiesResolver
{
  private List<DeedDescription> _deeds;
  private HashMap<String,DeedDescription> _mapByKey;

  /**
   * Constructor.
   * @param deeds Deeds to process.
   */
  public DeedProxiesResolver(List<DeedDescription> deeds)
  {
    _deeds=deeds;
  }

  private void loadMapByKey()
  {
    _mapByKey=new HashMap<String,DeedDescription>();
    for(DeedDescription deed : _deeds)
    {
      String key=deed.getKey();
      DeedDescription old=_mapByKey.put(key,deed);
      if (old!=null)
      {
        System.out.println("Multiple instances of deed key: "+key);
      }
    }
  }

  /**
   * Do resolve links.
   */
  public void doIt()
  {
    loadMapByKey();
    // Resolve deed links
    for(DeedDescription deed : _deeds)
    {
      resolveDeed(deed);
    }
  }

  private void resolveDeed(DeedDescription deed)
  {
    resolveDeedProxy(deed.getParentDeedProxy());
    resolveDeedProxy(deed.getNextDeedProxy());
    resolveDeedProxy(deed.getPreviousDeedProxy());
    for(DeedProxy childProxy : deed.getChildDeeds())
    {
      resolveDeedProxy(childProxy);
    }
  }

  private void resolveDeedProxy(DeedProxy proxy)
  {
    if (proxy==null) return;
    String key=proxy.getKey();
    DeedDescription proxiedDeed=_mapByKey.get(key);
    if (proxiedDeed!=null)
    {
      proxy.setName(proxiedDeed.getName());
      proxy.setDeed(proxiedDeed);
    }
    else
    {
      System.out.println("Deed not found for key: "+key);
    }
  }
}
