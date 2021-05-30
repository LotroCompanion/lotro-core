package delta.games.lotro.common.filters;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.text.MatchType;
import delta.common.utils.text.StringFilter;
import delta.games.lotro.common.Named;

/**
 * Filter on the name of stored items.
 * @author DAM
 * @param <T> Type of named objects.
 */
public class NamedFilter<T extends Named> implements Filter<T>
{
  private StringFilter _filter;
  private String _pattern;

  /**
   * Constructor.
   */
  public NamedFilter()
  {
    this("");
  }

  /**
   * Constructor.
   * @param pattern String filter for name.
   */
  public NamedFilter(String pattern)
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

  @Override
  public boolean accept(T namedObject)
  {
    String name=namedObject.getName();
    if (name!=null)
    {
      return _filter.accept(name);
    }
    return false;
  }
}
