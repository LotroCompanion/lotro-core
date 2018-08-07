package delta.games.lotro.character.storage.filters;

/**
 * Proxy value resolver.
 * @author DAM
 * @param <PROXY> Proxy POJO type.
 * @param <PROXY_VALUE> Proxy value POJO type.
 */
public interface ProxyValueResolver<PROXY,PROXY_VALUE>
{
  /**
   * Get the value of the proxy.
   * @param pojo Proxy.
   * @return A proxy value.
   */
  PROXY_VALUE getValue(PROXY pojo);
}
