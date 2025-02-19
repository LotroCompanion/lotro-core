package delta.games.lotro.character.status.housing.filter;

import java.util.Objects;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.housing.HousingItem;

/**
 * Filter for housing items using their hook.
 * @author DAM
 */
public class HousingItemHookFilter implements Filter<HousingItem>
{
  private String _hook;

  /**
   * Constructor.
   * @param hook Hook to select (may be <code>null</code>).
   */
  public HousingItemHookFilter(String hook)
  {
    _hook=hook;
  }

  /**
   * Get the hook to use.
   * @return A hook or <code>null</code>.
   */
  public String getHook()
  {
    return _hook;
  }

  /**
   * Set the hook to select.
   * @param hook Hook to use, may be <code>null</code>.
   */
  public void setHook(String hook)
  {
    _hook=hook;
  }

  @Override
  public boolean accept(HousingItem item)
  {
    if (_hook==null)
    {
      return true;
    }
    return Objects.equals(item.getHookID().getLabel(),_hook);
  }
}
