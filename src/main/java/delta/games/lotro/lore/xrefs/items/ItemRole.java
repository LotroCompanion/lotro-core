package delta.games.lotro.lore.xrefs.items;

/**
 * @author DAM
 */
public enum ItemRole
{
  /**
   * Item found as ingredient in recipe.
   */
  RECIPE_INGREDIENT,
  /**
   * Item found as critical ingredient in recipe.
   */
  RECIPE_CRITICAL_INGREDIENT,
  /**
   * Item found as result in recipe.
   */
  RECIPE_RESULT,
  /**
   * Item found as critical result in recipe.
   */
  RECIPE_CRITICAL_RESULT,
  /**
   * Item provides a recipe.
   */
  RECIPE_PROVIDES_RECIPE,
  /**
   * Item is a member of set.
   */
  SET_MEMBER_OF_SET,
  /**
   * Item is bartered by barterer.
   */
  BARTERER_BARTERED_BY,
  /**
   * Item is sold by vendor.
   */
  VENDOR_SOLD_BY,
  /**
   * Item is a reward of quest.
   */
  QUEST_REWARD,
  /**
   * Item is a reward of deed.
   */
  DEED_REWARD
}
