package delta.games.lotro.lore.crafting.recipes.comparator;

import java.util.Comparator;

import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.ProfessionComparator;
import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Comparator for recipes, using their profession.
 * @author DAM
 */
public class RecipeProfessionComparator implements Comparator<Recipe>
{
  private ProfessionComparator _professionComparator=new ProfessionComparator();

  @Override
  public int compare(Recipe o1, Recipe o2)
  {
    Profession profession1=o1.getProfession();
    Profession profession2=o2.getProfession();
    return _professionComparator.compare(profession1,profession2);
  }
}
