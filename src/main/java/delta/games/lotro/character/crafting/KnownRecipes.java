package delta.games.lotro.character.crafting;

import java.util.HashSet;
import java.util.Set;

/**
 * Known recipes for a single profession of a single character.
 * @author DAM
 */
public class KnownRecipes
{
  private Set<Integer> _knownRecipes;

  /**
   * Constructor.
   */
  public KnownRecipes()
  {
    _knownRecipes=new HashSet<Integer>();
  }

  /**
   * Reset known recipes.
   */
  public void clear()
  {
    _knownRecipes.clear();
  }

  /**
   * Add a recipe identifier.
   * @param recipeId
   */
  public void addRecipe(int recipeId)
  {
    _knownRecipes.add(Integer.valueOf(recipeId));
  }

  /**
   * Get all the known recipes.
   * @return a set of known recipes identifiers.
   */
  public Set<Integer> getKnownRecipes()
  {
    return _knownRecipes;
  }

  @Override
  public String toString()
  {
    return _knownRecipes.toString();
  }
}
