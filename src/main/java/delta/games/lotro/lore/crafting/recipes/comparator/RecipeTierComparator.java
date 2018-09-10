package delta.games.lotro.lore.crafting.recipes.comparator;

import java.util.Comparator;

import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Comparator for recipes, using their tier.
 * @author DAM
 */
public class RecipeTierComparator implements Comparator<Recipe>
{
  @Override
  public int compare(Recipe o1, Recipe o2)
  {
    return o1.getTier()-o2.getTier();
  }
}
