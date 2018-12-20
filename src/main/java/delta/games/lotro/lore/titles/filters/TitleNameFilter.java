package delta.games.lotro.lore.titles.filters;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.text.MatchType;
import delta.common.utils.text.StringFilter;
import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Filter for title name.
 * @author DAM
 */
public class TitleNameFilter implements Filter<TitleDescription>
{
  private StringFilter _filter;
  private String _pattern;

  /**
   * Constructor.
   */
  public TitleNameFilter()
  {
    this("");
  }

  /**
   * Constructor.
   * @param pattern String filter for name.
   */
  public TitleNameFilter(String pattern)
  {
    _filter=new StringFilter("",MatchType.CONTAINS,true);
    _pattern=pattern;
  }

  /**
   * Get the pattern to use to filter name.
   * @return A pattern string.
   */
  public String getPattern()
  {
    return _pattern;
  }

  /**
   * Set the string pattern.
   * @param pattern Pattern to set.
   */
  public void setPattern(String pattern)
  {
    if (pattern==null)
    {
      pattern="";
    }
    _pattern=pattern;
    _filter=new StringFilter(pattern,MatchType.CONTAINS,true);
  }

  public boolean accept(TitleDescription title)
  {
    String name=title.getName();
    if (name!=null)
    {
      return _filter.accept(name);
    }
    return false;
  }
}
