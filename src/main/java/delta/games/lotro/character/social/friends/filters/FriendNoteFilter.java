package delta.games.lotro.character.social.friends.filters;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.text.MatchType;
import delta.common.utils.text.StringFilter;
import delta.games.lotro.character.social.friends.Friend;

/**
 * Filter for friend notes.
 * @author DAM
 */
public class FriendNoteFilter implements Filter<Friend>
{
  private StringFilter _filter;
  private String _pattern;

  /**
   * Constructor.
   */
  public FriendNoteFilter()
  {
    this("");
  }

  /**
   * Constructor.
   * @param pattern String filter for name.
   */
  public FriendNoteFilter(String pattern)
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
  public boolean accept(Friend member)
  {
    String notes=member.getNote();
    if (notes!=null)
    {
      return _filter.accept(notes);
    }
    return false;
  }
}
