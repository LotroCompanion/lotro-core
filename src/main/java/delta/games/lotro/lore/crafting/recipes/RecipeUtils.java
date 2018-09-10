package delta.games.lotro.lore.crafting.recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import delta.common.utils.collections.CompoundComparator;
import delta.games.lotro.lore.crafting.recipes.comparator.RecipeNameComparator;
import delta.games.lotro.lore.crafting.recipes.comparator.RecipeProfessionComparator;
import delta.games.lotro.lore.crafting.recipes.comparator.RecipeTierComparator;

/**
 * Utility methods for recipes.
 * @author DAM
 */
public class RecipeUtils
{
  /**
   * Sort some recipes by profession/tier/name.
   * @param recipes Recipes to sort.
   */
  public static void sort(List<Recipe> recipes)
  {
    List<Comparator<Recipe>> comparators=new ArrayList<Comparator<Recipe>>();
    comparators.add(new RecipeProfessionComparator());
    comparators.add(new RecipeTierComparator());
    comparators.add(new RecipeNameComparator());
    CompoundComparator<Recipe> comparator=new CompoundComparator<Recipe>(comparators);
    Collections.sort(recipes,comparator);
  }
}
