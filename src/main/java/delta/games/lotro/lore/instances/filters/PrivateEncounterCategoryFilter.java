package delta.games.lotro.lore.instances.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;

/**
 * Filter for instances of a given category.
 * @author DAM
 */
public class PrivateEncounterCategoryFilter implements Filter<SkirmishPrivateEncounter>
{
  private String _category;

  /**
   * Constructor.
   * @param category Category to select (may be <code>null</code>).
   */
  public PrivateEncounterCategoryFilter(String category)
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

  @Override
  public boolean accept(SkirmishPrivateEncounter instance)
  {
    if (_category==null)
    {
      return true;
    }
    String category=instance.getCategory();
    if (_category.equals(category))
    {
      return true;
    }
    return false;
  }
}
