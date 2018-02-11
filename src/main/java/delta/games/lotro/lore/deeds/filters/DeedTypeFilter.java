package delta.games.lotro.lore.deeds.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedType;

/**
 * Filter for deed of a given type.
 * @author DAM
 */
public class DeedTypeFilter implements Filter<DeedDescription>
{
  private DeedType _deedType;

  /**
   * Constructor.
   * @param deedType Deed type to select (may be <code>null</code>).
   */
  public DeedTypeFilter(DeedType deedType)
  {
    _deedType=deedType;
  }

  /**
   * Get the deed type to use.
   * @return A deed type or <code>null</code>.
   */
  public DeedType getDeedtype()
  {
    return _deedType;
  }

  /**
   * Set the deed type to select.
   * @param deedType Deed type to use, may be <code>null</code>.
   */
  public void setDeedType(DeedType deedType)
  {
    _deedType=deedType;
  }

  public boolean accept(DeedDescription deed)
  {
    if (_deedType==null)
    {
      return true;
    }
    return deed.getType()==_deedType;
  }
}
