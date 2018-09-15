package delta.games.lotro.lore.crafting.recipes.comparator;

import java.util.Comparator;

import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Comparator for recipes, using their category.
 * @author DAM
 */
public class RecipeCategoryComparator implements Comparator<Recipe>
{
  @Override
  public int compare(Recipe o1, Recipe o2)
  {
    String category1=o1.getCategory();
    String category2=o2.getCategory();
    return category1.compareTo(category2);
  }
}
