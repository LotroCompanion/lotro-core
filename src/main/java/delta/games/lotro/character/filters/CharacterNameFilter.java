package delta.games.lotro.character.filters;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.text.MatchType;
import delta.common.utils.text.StringFilter;
import delta.games.lotro.character.CharacterReference;

/**
 * Filter for character name.
 * @param <T> Type of managed data.
 * @author DAM
 */
public class CharacterNameFilter<T extends CharacterReference> implements Filter<T>
{
  private StringFilter _filter;
  private String _pattern;

  /**
   * Constructor.
   */
  public CharacterNameFilter()
  {
    this("");
  }

  /**
   * Constructor.
   * @param pattern String filter for name.
   */
  public CharacterNameFilter(String pattern)
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
  public boolean accept(T summary)
  {
    String name=summary.getName();
    if (name!=null)
    {
      return _filter.accept(name);
    }
    return false;
  }
}
