package delta.games.lotro.character.status.recipes.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.recipes.RecipeStatus;
import delta.games.lotro.common.blacklist.Blacklist;
import delta.games.lotro.common.blacklist.filter.BlackListFilter;
import delta.games.lotro.lore.crafting.recipes.filters.RecipeFilter;

/**
 * Filter for recipe statuses.
 * @author DAM
 */
public class RecipeStatusFilter implements Filter<RecipeStatus>
{
  private RecipeFilter _recipeFilter;
  private RecipeStateFilter _stateFilter;
  // Blacklist
  private BlackListFilter _blacklist;

  /**
   * Constructor.
   */
  public RecipeStatusFilter()
  {
    _recipeFilter=new RecipeFilter();
    _stateFilter=new RecipeStateFilter();
    _blacklist=null;
  }

  /**
   * Get the managed recipe filter.
   * @return the managed recipe filter.
   */
  public RecipeFilter getRecipeFilter()
  {
    return _recipeFilter;
  }

  /**
   * Get the managed state filter.
   * @return a state filter.
   */
  public RecipeStateFilter getStateFilter()
  {
    return _stateFilter;
  }

  /**
   * Get the blacklist filter.
   * @return the blacklist filter.
   */
  public BlackListFilter getBlacklistFilter()
  {
    return _blacklist;
  }

  /**
   * Set the blacklist.
   * @param blacklist Blacklist to use.
   */
  public void setBlacklist(Blacklist blacklist)
  {
    _blacklist=new BlackListFilter(blacklist);
  }

  @Override
  public boolean accept(RecipeStatus item)
  {
    boolean ok=_recipeFilter.accept(item.getRecipe());
    if ((ok) && (_blacklist!=null))
    {
      ok=_blacklist.accept(item.getRecipe());
    }
    if (ok)
    {
      return _stateFilter.accept(item);
    }
    return false;
  }
}
