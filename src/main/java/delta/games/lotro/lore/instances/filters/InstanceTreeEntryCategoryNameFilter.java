package delta.games.lotro.lore.instances.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.instances.InstanceTreeEntry;

/**
 * Filter on instance entries on category name.
 * @author DAM
 */
public class InstanceTreeEntryCategoryNameFilter implements Filter<InstanceTreeEntry>
{
  private String _categoryName;

  /**
   * Constructor.
   */
  public InstanceTreeEntryCategoryNameFilter()
  {
    this("");
  }

  /**
   * Constructor.
   * @param categoryName Category name to use.
   */
  public InstanceTreeEntryCategoryNameFilter(String categoryName)
  {
    _categoryName=categoryName;
  }

  /**
   * Get the pattern to use to filter name.
   * @return A pattern string.
   */
  public String getCategoryName()
  {
    return _categoryName;
  }

  /**
   * Set the category name.
   * @param categoryName Category name to set.
   */
  public void setCategoryName(String categoryName)
  {
    _categoryName=categoryName;
  }

  @Override
  public boolean accept(InstanceTreeEntry entry)
  {
    if (_categoryName==null)
    {
      return true;
    }
    String name=entry.getName();
    return _categoryName.equals(name);
  }
}
