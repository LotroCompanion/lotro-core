package delta.games.lotro.lore.crafting.recipes.filters;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.text.MatchType;
import delta.common.utils.text.StringFilter;
import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Filter for recipe name.
 * @author DAM
 */
public class RecipeNameFilter implements Filter<Recipe>
{
  private StringFilter _filter;
  private String _pattern;

  /**
   * Constructor.
   */
  public RecipeNameFilter()
  {
    this("");
  }

  /**
   * Constructor.
   * @param pattern String filter for name.
   */
  public RecipeNameFilter(String pattern)
  {
    _filter=new StringFilter("",MatchType.CONTAINS,true, true);
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
    _filter=new StringFilter(pattern,MatchType.CONTAINS,true, true);
  }

  public boolean accept(Recipe recipe)
  {
    String name=recipe.getName();
    if (name!=null)
    {
      return _filter.accept(name);
    }
    return false;
  }
}
