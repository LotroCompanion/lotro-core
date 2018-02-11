package delta.games.lotro.lore.deeds.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Filter for deed of a given category.
 * @author DAM
 */
public class DeedCategoryFilter implements Filter<DeedDescription>
{
  private String _category;

  /**
   * Constructor.
   * @param category Deed category to select (may be <code>null</code>).
   */
  public DeedCategoryFilter(String category)
  {
    _category=category;
  }

  /**
   * Get the deed type to use.
   * @return A deed type or <code>null</code>.
   */
  public String getDeedCategory()
  {
    return _category;
  }

  /**
   * Set the deed category to select.
   * @param category Deed category to use, may be <code>null</code>.
   */
  public void setDeedCategory(String category)
  {
    _category=category;
  }

  public boolean accept(DeedDescription deed)
  {
    if (_category==null)
    {
      return true;
    }
    return _category.equals(deed.getCategory());
  }
}
