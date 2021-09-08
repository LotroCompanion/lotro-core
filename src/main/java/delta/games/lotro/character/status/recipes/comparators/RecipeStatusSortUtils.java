package delta.games.lotro.character.status.recipes.comparators;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import delta.games.lotro.character.status.recipes.RecipeStatus;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.crafting.recipes.RecipeUtils;
import delta.games.lotro.utils.DataProvider;
import delta.games.lotro.utils.comparators.DelegatingComparator;

/**
 * Utility methods to sort recipe statuses.
 * @author DAM
 */
public class RecipeStatusSortUtils
{
  /**
   * Sort recipe statuses using the standard recipes sort.
   * @param recipeStatuses Elements to sort.
   */
  public static void sortByRecipeStatuses(List<RecipeStatus> recipeStatuses)
  {
    Comparator<Recipe> recipesComparator=RecipeUtils.buildRecipeComparator();
    DataProvider<RecipeStatus,Recipe> p=new DataProvider<RecipeStatus,Recipe>()
    {
      public Recipe getData(RecipeStatus taskStatus)
      {
        return taskStatus.getRecipe();
      }
    };
    DelegatingComparator<RecipeStatus,Recipe> c=new DelegatingComparator<>(p,recipesComparator);
    Collections.sort(recipeStatuses,c);
  }
}
