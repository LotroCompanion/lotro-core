package delta.games.lotro.lore.emotes.filters;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.text.MatchType;
import delta.common.utils.text.StringFilter;
import delta.games.lotro.lore.emotes.EmoteDescription;

/**
 * Filter for emote command.
 * @author DAM
 */
public class EmoteCommandFilter implements Filter<EmoteDescription>
{
  private StringFilter _filter;
  private String _pattern;

  /**
   * Constructor.
   */
  public EmoteCommandFilter()
  {
    this("");
  }

  /**
   * Constructor.
   * @param pattern String filter for name.
   */
  public EmoteCommandFilter(String pattern)
  {
    _filter=new StringFilter("",MatchType.CONTAINS,true);
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
    _filter=new StringFilter(pattern,MatchType.CONTAINS,true);
  }

  public boolean accept(EmoteDescription emote)
  {
    String command=emote.getCommand();
    if (command!=null)
    {
      return _filter.accept(command);
    }
    return false;
  }
}
