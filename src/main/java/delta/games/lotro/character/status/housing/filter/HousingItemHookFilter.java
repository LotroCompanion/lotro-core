package delta.games.lotro.character.status.housing.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.housing.HousingItem;
import delta.games.lotro.common.enums.HousingHookID;

/**
 * Filter for housing items using their hook ID.
 * @author DAM
 */
public class HousingItemHookFilter implements Filter<HousingItem>
{
  private HousingHookID _hookID;

  /**
   * Constructor.
   * @param hookID Hook to select (may be <code>null</code>).
   */
  public HousingItemHookFilter(HousingHookID hookID)
  {
    _hookID=hookID;
  }

  /**
   * Get the hook ID to use.
   * @return A hook ID or <code>null</code>.
   */
  public HousingHookID getHookID()
  {
    return _hookID;
  }

  /**
   * Set the hook ID to select.
   * @param hookID Hook ID to use, may be <code>null</code>.
   */
  public void setHookID(HousingHookID hookID)
  {
    _hookID=hookID;
  }

  @Override
  public boolean accept(HousingItem item)
  {
    if (_hookID==null)
    {
      return true;
    }
    return item.getHookID()==_hookID;
  }
}
