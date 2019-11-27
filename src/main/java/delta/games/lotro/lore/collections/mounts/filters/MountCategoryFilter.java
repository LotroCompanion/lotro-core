package delta.games.lotro.lore.collections.mounts.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.collections.mounts.MountDescription;

/**
 * Filter for mounts of a given category.
 * @author DAM
 */
public class MountCategoryFilter implements Filter<MountDescription>
{
  private String _category;

  /**
   * Constructor.
   * @param category Category to select (may be <code>null</code>).
   */
  public MountCategoryFilter(String category)
  {
    _category=category;
  }

  /**
   * Get the category to use.
   * @return A category or <code>null</code>.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the category to select.
   * @param category Category to use, may be <code>null</code>.
   */
  public void setCategory(String category)
  {
    _category=category;
  }

  public boolean accept(MountDescription mount)
  {
    if (_category==null)
    {
      return true;
    }
    String category=mount.getCategory();
    if (_category.equals(category))
    {
      return true;
    }
    return false;
  }
}
