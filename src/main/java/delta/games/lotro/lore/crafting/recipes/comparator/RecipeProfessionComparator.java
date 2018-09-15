package delta.games.lotro.lore.crafting.recipes.comparator;

import java.util.Comparator;

import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Comparator for recipes, using their profession.
 * @author DAM
 */
public class RecipeProfessionComparator implements Comparator<Recipe>
{
  @Override
  public int compare(Recipe o1, Recipe o2)
  {
    String profession1=o1.getProfession();
    String profession2=o2.getProfession();
    return profession1.compareTo(profession2);
  }
}
