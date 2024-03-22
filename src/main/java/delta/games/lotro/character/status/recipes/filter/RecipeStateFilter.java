package delta.games.lotro.character.status.recipes.filter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.recipes.RecipeState;
import delta.games.lotro.character.status.recipes.RecipeStatus;

/**
 * Filter for a recipe in a given state.
 * @author DAM
 */
public class RecipeStateFilter implements Filter<RecipeStatus>
{
  private Set<RecipeState> _states;

  /**
   * Constructor.
   */
  public RecipeStateFilter()
  {
    _states=new HashSet<RecipeState>();
    Collections.addAll(_states,RecipeState.values());
  }

  /**
   * Get the selected states.
   * @return A possibly empty but never <code>null</code> set of states.
   */
  public Set<RecipeState> getSelectedStates()
  {
    return _states;
  }

  /**
   * Set the states to select.
   * @param states States to select.
   */
  public void setStates(Set<RecipeState> states)
  {
    _states=states;
  }

  @Override
  public boolean accept(RecipeStatus recipe)
  {
    return _states.contains(recipe.getState());
  }
}
