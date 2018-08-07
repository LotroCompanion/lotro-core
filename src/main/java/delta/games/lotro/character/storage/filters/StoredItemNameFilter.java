package delta.games.lotro.character.storage.filters;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.text.MatchType;
import delta.common.utils.text.StringFilter;
import delta.games.lotro.character.storage.StoredItem;

/**
 * Filter on the name of stored items.
 * @author DAM
 */
public class StoredItemNameFilter implements Filter<StoredItem>
{
  private StringFilter _filter;
  private String _pattern;

  /**
   * Constructor.
   */
  public StoredItemNameFilter()
  {
    this("");
  }

  /**
   * Constructor.
   * @param pattern String filter for name.
   */
  public StoredItemNameFilter(String pattern)
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

  public boolean accept(StoredItem storedItem)
  {
    String name=storedItem.getName();
    if (name!=null)
    {
      return _filter.accept(name);
    }
    return false;
  }
}
