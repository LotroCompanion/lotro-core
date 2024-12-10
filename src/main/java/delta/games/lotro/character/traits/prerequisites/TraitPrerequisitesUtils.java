package delta.games.lotro.character.traits.prerequisites;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Utility methods related to trait pre-requisites.
 * @author DAM
 */
public class TraitPrerequisitesUtils
{
  private static final Logger LOGGER=LoggerFactory.getLogger(TraitPrerequisitesUtils.class);

  /**
   * Resolve proxies.
   * @param traits Traits to use.
   * @param proxies Proxies to resolve.
   */
  public static void resolveProxies(List<TraitDescription> traits, List<Proxy<TraitDescription>> proxies)
  {
    Map<Integer,TraitDescription> map=new HashMap<Integer,TraitDescription>();
    for(TraitDescription trait : traits)
    {
      map.put(Integer.valueOf(trait.getIdentifier()),trait);
    }
    for(Proxy<TraitDescription> proxy : proxies)
    {
      int id=proxy.getId();
      TraitDescription trait=map.get(Integer.valueOf(id));
      if (trait!=null)
      {
        proxy.setName(trait.getName());
        proxy.setObject(trait);
      }
      else
      {
        LOGGER.warn("Could not resolve trait: {}",proxy);
      }
    }
  }
}
