package delta.games.lotro.tools.crafting;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.items.Item;

/**
 * Gather recipes to share from some items.
 * @author DAM
 */
public class RecipesToShare
{
  private List<RecipeSharingElement> _elements;
  private RecipeItems _recipeItems;

  /**
   * Constructor.
   */
  public RecipesToShare()
  {
    _recipeItems=new RecipeItems();
    _elements=new ArrayList<RecipeSharingElement>();
  }

  /**
   * Analyze an item: store item/recipe couple if possible.
   * @param item Item to analyze.
   */
  public void analyze(Item item)
  {
    Recipe recipe=_recipeItems.getRecipeForItem(item);
    if (recipe!=null)
    {
      RecipeSharingElement element=new RecipeSharingElement(item,recipe);
      _elements.add(element);
    }
  }

  /**
   * Get the share-able recipes for a given profession.
   * @param profession Profession to use.
   * @return A list of recipe/item couples.
   */
  public List<RecipeSharingElement> getRecipes(Profession profession)
  {
    List<RecipeSharingElement> ret=new ArrayList<RecipeSharingElement>();
    for(RecipeSharingElement element : _elements)
    {
      Recipe recipe=element.getRecipe();
      Profession recipeProfession=recipe.getProfession();
      if (recipeProfession==profession)
      {
        ret.add(element);
      }
    }
    return ret;
  }
}
