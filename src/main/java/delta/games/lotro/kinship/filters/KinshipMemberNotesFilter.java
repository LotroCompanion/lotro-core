package delta.games.lotro.kinship.filters;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.text.MatchType;
import delta.common.utils.text.StringFilter;
import delta.games.lotro.kinship.KinshipMember;

/**
 * Filter for kinship member notes.
 * @author DAM
 */
public class KinshipMemberNotesFilter implements Filter<KinshipMember>
{
  private StringFilter _filter;
  private String _pattern;

  /**
   * Constructor.
   */
  public KinshipMemberNotesFilter()
  {
    this("");
  }

  /**
   * Constructor.
   * @param pattern String filter for name.
   */
  public KinshipMemberNotesFilter(String pattern)
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
  public boolean accept(KinshipMember member)
  {
    String notes=member.getNotes();
    if (notes!=null)
    {
      return _filter.accept(notes);
    }
    return false;
  }
}
