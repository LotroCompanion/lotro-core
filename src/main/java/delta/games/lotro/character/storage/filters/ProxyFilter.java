package delta.games.lotro.character.storage.filters;

import delta.common.utils.collections.filters.Filter;

/**
 * Proxy for another filter.
 * @author DAM
 * @param <SOURCE_POJO> Source POJO type.
 * @param <POJO> Intermediate POJO type.
 */
public class ProxyFilter<SOURCE_POJO,POJO> implements Filter<SOURCE_POJO>
{
  private ProxyValueResolver<SOURCE_POJO,POJO> _proxySolver;
  private Filter<POJO> _childFilter;

  /**
   * Constructor.
   * @param proxySolver Proxy solver.
   * @param childFilter Filter on proxied type.
   */
  public ProxyFilter(ProxyValueResolver<SOURCE_POJO,POJO> proxySolver, Filter<POJO> childFilter)
  {
    _proxySolver=proxySolver;
    _childFilter=childFilter;
  }

  /**
   * Get the child filter.
   * @return the child filter.
   */
  public Filter<POJO> getChildFilter()
  {
    return _childFilter;
  }

  @Override
  public boolean accept(SOURCE_POJO item)
  {
    POJO value=_proxySolver.getValue(item);
    try
    {
      return _childFilter.accept(value);
    }
    catch(Exception e)
    {
      return false;
    }
  }
}
