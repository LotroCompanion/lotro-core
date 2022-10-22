package delta.games.lotro.character.skills.filters;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.text.MatchType;
import delta.common.utils.text.StringFilter;
import delta.games.lotro.character.skills.SkillDescription;

/**
 * Filter for skills using their name.
 * @author DAM
 */
public class SkillNameFilter implements Filter<SkillDescription>
{
  private StringFilter _filter;
  private String _pattern;

  /**
   * Constructor.
   */
  public SkillNameFilter()
  {
    this("");
  }

  /**
   * Constructor.
   * @param pattern String filter for name.
   */
  public SkillNameFilter(String pattern)
  {
    _filter=new StringFilter("",MatchType.CONTAINS,true, true);
    _pattern=pattern;
  }

  /**
   * Get the pattern to use to filter command.
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
    _filter=new StringFilter(pattern,MatchType.CONTAINS,true, true);
  }

  @Override
  public boolean accept(SkillDescription skill)
  {
    String name=skill.getName();
    if (name!=null)
    {
      return _filter.accept(name);
    }
    return false;
  }
}
