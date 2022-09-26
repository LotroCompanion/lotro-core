package delta.games.lotro.lore.items.paper.filters;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.common.filters.NamedFilter;
import delta.games.lotro.lore.items.paper.PaperItem;

/**
 * Paper items filter.
 * @author DAM
 */
public class PaperItemFilter implements Filter<PaperItem>
{
  private Filter<PaperItem> _filter;

  private NamedFilter<PaperItem> _nameFilter;
  private PaperItemCategoryFilter _categoryFilter;
  private PaperItemIsAccountSharedFilter _sharedFilter;

  /**
   * Constructor.
   */
  public PaperItemFilter()
  {
    List<Filter<PaperItem>> filters=new ArrayList<Filter<PaperItem>>();
    // Name
    _nameFilter=new NamedFilter<PaperItem>();
    filters.add(_nameFilter);
    // Account shared or not
    _sharedFilter=new PaperItemIsAccountSharedFilter(null);
    filters.add(_sharedFilter);
    // Category
    _categoryFilter=new PaperItemCategoryFilter();
    filters.add(_categoryFilter);
    _filter=new CompoundFilter<PaperItem>(Operator.AND,filters);
  }

  /**
   * Get the filter on name.
   * @return a name filter.
   */
  public NamedFilter<PaperItem> getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on 'shared' flag.
   * @return a filter on 'shared' flag.
   */
  public PaperItemIsAccountSharedFilter getSharedFilter()
  {
    return _sharedFilter;
  }

  /**
   * Get the filter on category.
   * @return a filter on category.
   */
  public PaperItemCategoryFilter getCategoryFilter()
  {
    return _categoryFilter;
  }

  @Override
  public boolean accept(PaperItem item)
  {
    return _filter.accept(item);
  }
}
