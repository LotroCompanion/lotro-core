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
    if (category1==null)
    {
      if (category2==null) return 0;
      return -1;
    }
    if (category2==null) return 1;
    return category1.compareTo(category2);
  }
}
