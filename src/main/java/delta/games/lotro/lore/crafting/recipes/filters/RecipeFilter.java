package delta.games.lotro.lore.crafting.recipes.filters;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.crafting.recipes.filters.RecipeCategoryFilter;
import delta.games.lotro.lore.crafting.recipes.filters.RecipeNameFilter;
import delta.games.lotro.lore.crafting.recipes.filters.RecipeProfessionFilter;
import delta.games.lotro.lore.crafting.recipes.filters.RecipeTierFilter;

/**
 * Recipe filter.
 * @author DAM
 */
public class RecipeFilter implements Filter<Recipe>
{
  private Filter<Recipe> _filter;

  private RecipeNameFilter _nameFilter;
  private RecipeProfessionFilter _professionFilter;
  private RecipeTierFilter _tierFilter;
  private RecipeCategoryFilter _categoryFilter;
  private RecipeIngredientFilter _ingredientFilter;
  private RecipeSingleUseFilter _singleUseFilter;
  private RecipeHasCooldownFilter _hasCooldownFilter;
  private RecipeIsGuildFilter _isGuildFilter;

  /**
   * Constructor.
   */
  public RecipeFilter()
  {
    List<Filter<Recipe>> filters=new ArrayList<Filter<Recipe>>();
    // Name
    _nameFilter=new RecipeNameFilter();
    filters.add(_nameFilter);
    // Profession
    _professionFilter=new RecipeProfessionFilter(null);
    filters.add(_professionFilter);
    // Tier
    _tierFilter=new RecipeTierFilter(null);
    filters.add(_tierFilter);
    // Category
    _categoryFilter=new RecipeCategoryFilter(null);
    filters.add(_categoryFilter);
    // Ingredient
    _ingredientFilter=new RecipeIngredientFilter(null);
    filters.add(_ingredientFilter);
    // Single Use
    _singleUseFilter=new RecipeSingleUseFilter(null);
    filters.add(_singleUseFilter);
    // Cooldown
    _hasCooldownFilter=new RecipeHasCooldownFilter(null);
    filters.add(_hasCooldownFilter);
    // Guild
    _isGuildFilter=new RecipeIsGuildFilter(null);
    filters.add(_isGuildFilter);
    _filter=new CompoundFilter<Recipe>(Operator.AND,filters);
  }

  /**
   * Get the filter on recipe name.
   * @return a recipe name filter.
   */
  public RecipeNameFilter getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on recipe category.
   * @return a recipe category filter.
   */
  public RecipeProfessionFilter getProfessionFilter()
  {
    return _professionFilter;
  }

  /**
   * Get the filter on recipe tier.
   * @return a recipe tier filter.
   */
  public RecipeTierFilter getTierFilter()
  {
    return _tierFilter;
  }

  /**
   * Get the filter on recipe category.
   * @return a recipe category filter.
   */
  public RecipeCategoryFilter getCategoryFilter()
  {
    return _categoryFilter;
  }

  /**
   * Get the filter on ingredient.
   * @return a recipe ingredient filter.
   */
  public RecipeIngredientFilter getIngredientFilter()
  {
    return _ingredientFilter;
  }

  /**
   * Get the filter on 'single use'.
   * @return a recipe single use filter.
   */
  public RecipeSingleUseFilter getSingleUseFilter()
  {
    return _singleUseFilter;
  }

  /**
   * Get the filter on 'cooldown'.
   * @return a recipe cooldown filter.
   */
  public RecipeHasCooldownFilter getCooldownFilter()
  {
    return _hasCooldownFilter;
  }

  /**
   * Get the filter on 'guild'.
   * @return a recipe guild filter.
   */
  public RecipeIsGuildFilter getGuildFilter()
  {
    return _isGuildFilter;
  }

  @Override
  public boolean accept(Recipe item)
  {
    return _filter.accept(item);
  }
}
