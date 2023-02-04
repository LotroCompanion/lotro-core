package delta.games.lotro.lore.instances.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.enums.WJEncounterCategory;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;

/**
 * Filter for instances of a given category.
 * @author DAM
 */
public class PrivateEncounterCategoryFilter implements Filter<SkirmishPrivateEncounter>
{
  private WJEncounterCategory _category;

  /**
   * Constructor.
   * @param category Category to select (may be <code>null</code>).
   */
  public PrivateEncounterCategoryFilter(WJEncounterCategory category)
  {
    _category=category;
  }

  /**
   * Get the category to use.
   * @return A category or <code>null</code>.
   */
  public WJEncounterCategory getCategory()
  {
    return _category;
  }

  /**
   * Set the category to select.
   * @param category Category to use, may be <code>null</code>.
   */
  public void setCategory(WJEncounterCategory category)
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
    WJEncounterCategory category=instance.getCategory();
    if (_category==category)
    {
      return true;
    }
    return false;
  }
}
