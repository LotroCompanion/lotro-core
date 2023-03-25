package delta.games.lotro.lore.deeds.filters;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.quests.filter.AchievableFilter;

/**
 * Deed filter.
 * @author DAM
 */
public class DeedFilter extends AchievableFilter<DeedDescription>
{
  private DeedTypeFilter _typeFilter;
  private DeedCategoryFilter _categoryFilter;

  /**
   * Constructor.
   */
  public DeedFilter()
  {
    CompoundFilter<DeedDescription> filter=getFilter();
    // Type
    _typeFilter=new DeedTypeFilter(null);
    filter.addFilter(_typeFilter);
    // Category
    _categoryFilter=new DeedCategoryFilter(null);
    filter.addFilter(_categoryFilter);
  }

  /**
   * Get the filter on deed type.
   * @return a deed type filter.
   */
  public DeedTypeFilter getTypeFilter()
  {
    return _typeFilter;
  }

  /**
   * Get the filter on deed category.
   * @return a deed category filter.
   */
  public DeedCategoryFilter getCategoryFilter()
  {
    return _categoryFilter;
  }
}
