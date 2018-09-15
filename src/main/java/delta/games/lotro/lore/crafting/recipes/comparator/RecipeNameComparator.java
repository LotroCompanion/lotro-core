package delta.games.lotro.lore.crafting.recipes.comparator;

import java.util.Comparator;

import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Comparator for recipes, using their name.
 * @author DAM
 */
public class RecipeNameComparator implements Comparator<Recipe>
{
  @Override
  public int compare(Recipe o1, Recipe o2)
  {
    String name1=o1.getName();
    String name2=o2.getName();
    return name1.compareTo(name2);
  }
}
