package delta.games.lotro.lore.crafting.recipes.comparator;

import java.util.Comparator;

import delta.games.lotro.common.enums.CraftingUICategory;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryNameComparator;
import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Comparator for recipes, using their category.
 * @author DAM
 */
public class RecipeCategoryComparator implements Comparator<Recipe>
{
  private LotroEnumEntryNameComparator<CraftingUICategory> _enumComparator=new LotroEnumEntryNameComparator<CraftingUICategory>();

  @Override
  public int compare(Recipe o1, Recipe o2)
  {
    CraftingUICategory category1=o1.getCategory();
    CraftingUICategory category2=o2.getCategory();
    return _enumComparator.compare(category1,category2);
  }
}
