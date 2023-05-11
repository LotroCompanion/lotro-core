package delta.games.lotro.lore.crafting.recipes.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.enums.CraftingUICategory;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.lore.crafting.CraftingUtils;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.recipes.CraftingResult;
import delta.games.lotro.lore.crafting.recipes.Ingredient;
import delta.games.lotro.lore.crafting.recipes.IngredientPack;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.crafting.recipes.RecipeVersion;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Parser for quest descriptions stored in XML.
 * @author DAM
 */
public class RecipeXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(RecipeXMLParser.class);

  private LotroEnum<CraftingUICategory> _categoryEnum;
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public RecipeXMLParser()
  {
    _categoryEnum=LotroEnumsRegistry.getInstance().get(CraftingUICategory.class);
    _i18n=I18nFacade.getLabelsMgr("recipes");
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed recipe or <code>null</code>.
   */
  public Recipe parseXML(File source)
  {
    Recipe recipe=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      recipe=parseRecipe(root);
    }
    return recipe;
  }

  /**
   * Load some recipes from an XML file.
   * @param source Source file.
   * @return A possibly empty but not <code>null</code> list of recipes.
   */
  public List<Recipe> loadRecipes(File source)
  {
    List<Recipe> recipes=new ArrayList<Recipe>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> recipeTags=DOMParsingTools.getChildTagsByName(root,RecipeXMLConstants.RECIPE_TAG);
      for(Element recipeTag : recipeTags)
      {
        Recipe recipe=parseRecipe(recipeTag);
        recipes.add(recipe);
      }
    }
    return recipes;
  }

  private Recipe parseRecipe(Element root)
  {
    Recipe r=new Recipe();

    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,RecipeXMLConstants.RECIPE_ID_ATTR,0);
    r.setIdentifier(id);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    r.setName(name);
    // Profession
    String professionName=DOMParsingTools.getStringAttribute(attrs,RecipeXMLConstants.RECIPE_PROFESSION_ATTR,null);
    Profession profession=CraftingUtils.getProfessionByKey(professionName);
    r.setProfession(profession);
    // Tier
    int tier=DOMParsingTools.getIntAttribute(attrs,RecipeXMLConstants.RECIPE_TIER_ATTR,0);
    r.setTier(tier);
    // Category
    int categoryCode=DOMParsingTools.getIntAttribute(attrs,RecipeXMLConstants.RECIPE_CATEGORY_ATTR,0);
    CraftingUICategory category=_categoryEnum.getEntry(categoryCode);
    r.setCategory(category);
    // XP
    int xp=DOMParsingTools.getIntAttribute(attrs,RecipeXMLConstants.RECIPE_XP_ATTR,0);
    r.setXP(xp);
    // Cooldown
    int cooldown=DOMParsingTools.getIntAttribute(attrs,RecipeXMLConstants.RECIPE_COOLDOWN_ATTR,-1);
    r.setCooldown(cooldown);
    // Single use
    boolean singleUse=DOMParsingTools.getBooleanAttribute(attrs,RecipeXMLConstants.RECIPE_SINGLE_USE_ATTR,false);
    r.setOneTimeUse(singleUse);
    // Guild required
    boolean guildRequired=DOMParsingTools.getBooleanAttribute(attrs,RecipeXMLConstants.RECIPE_GUILD_ATTR,false);
    r.setGuildRequired(guildRequired);

    // Recipe scroll
    Element scrollItemElement=DOMParsingTools.getChildTagByName(root,RecipeXMLConstants.SCROLL_ITEM_TAG);
    if (scrollItemElement!=null)
    {
      Item ref=parseItemRef(scrollItemElement);
      r.setRecipeScroll(ref);
    }
    // Ingredient pack
    Element ingredientPackTag=DOMParsingTools.getChildTagByName(root,RecipeXMLConstants.INGREDIENT_PACK_TAG);
    if (ingredientPackTag!=null)
    {
      IngredientPack ingredientPack=parseIngredientPack(ingredientPackTag);
      r.setIngredientPack(ingredientPack);
    }

    // Versions
    List<RecipeVersion> versions=new ArrayList<RecipeVersion>();
    List<Element> versionElements=DOMParsingTools.getChildTagsByName(root,RecipeXMLConstants.RECIPE_RESULT_TAG);
    for(Element versionElement : versionElements)
    {
      RecipeVersion version=parseRecipeVersion(versionElement);
      versions.add(version);
    }
    r.setVersions(versions);
    return r;
  }

  private RecipeVersion parseRecipeVersion(Element versionElement)
  {
    RecipeVersion version=new RecipeVersion();
    NamedNodeMap versionAttrs=versionElement.getAttributes();
    // Base critical chance
    int baseCriticalChance=DOMParsingTools.getIntAttribute(versionAttrs,RecipeXMLConstants.RECIPE_RESULT_BASE_CRITICAL_CHANCE_ATTR,0);
    version.setBaseCriticalChance((baseCriticalChance!=0)?Integer.valueOf(baseCriticalChance):null);

    // Ingredients
    List<Ingredient> ingredients=new ArrayList<Ingredient>();
    List<Element> ingredientElements=DOMParsingTools.getChildTagsByName(versionElement,RecipeXMLConstants.INGREDIENT_TAG);
    for(Element ingredientElement : ingredientElements)
    {
      Ingredient ingredient=parseIngredient(ingredientElement);
      ingredients.add(ingredient);
    }
    version.setIngredients(ingredients);

    // Results
    List<Element> resultElements=DOMParsingTools.getChildTagsByName(versionElement,RecipeXMLConstants.RESULT_TAG);
    for(Element resultElement : resultElements)
    {
      CraftingResult result=parseResult(resultElement);
      boolean isCritical=result.isCriticalResult();
      if (isCritical)
      {
        version.setCritical(result);
      }
      else
      {
        version.setRegular(result);
      }
    }
    return version;
  }

  private Ingredient parseIngredient(Element ingredientElement)
  {
    Ingredient ingredient=new Ingredient();
    NamedNodeMap ingredientAttrs=ingredientElement.getAttributes();
    // Quantity
    int quantity=DOMParsingTools.getIntAttribute(ingredientAttrs,RecipeXMLConstants.INGREDIENT_QUANTITY_ATTR,1);
    ingredient.setQuantity(quantity);
    // Optional
    boolean optional=DOMParsingTools.getBooleanAttribute(ingredientAttrs,RecipeXMLConstants.INGREDIENT_OPTIONAL_ATTR,false);
    ingredient.setOptional(optional);
    if (optional)
    {
      // Critical chance bonus
      int criticalChanceBonus=DOMParsingTools.getIntAttribute(ingredientAttrs,RecipeXMLConstants.INGREDIENT_CRITICAL_CHANCE_BONUS_ATTR,0);
      ingredient.setCriticalChanceBonus((criticalChanceBonus!=0)?Integer.valueOf(criticalChanceBonus):null);
    }
    // Item reference
    Element ingredientItemRef=DOMParsingTools.getChildTagByName(ingredientElement,RecipeXMLConstants.INGREDIENT_ITEM_TAG);
    if (ingredientItemRef!=null)
    {
      Item ingredientRef=parseItemRef(ingredientItemRef);
      ingredient.setItem(ingredientRef);
    }
    return ingredient;
  }

  private CraftingResult parseResult(Element resultElement)
  {
    NamedNodeMap attrs=resultElement.getAttributes();
    CraftingResult result=new CraftingResult();
    // Quantity
    int quantity=DOMParsingTools.getIntAttribute(attrs,RecipeXMLConstants.RESULT_QUANTITY_ATTR,1);
    result.setQuantity(quantity);
    // Critical
    boolean critical=DOMParsingTools.getBooleanAttribute(attrs,RecipeXMLConstants.RESULT_CRITICAL_ATTR,false);
    result.setCriticalResult(critical);
    // Item reference
    Element resultItemRefElement=DOMParsingTools.getChildTagByName(resultElement,RecipeXMLConstants.RESULT_ITEM_TAG);
    if (resultItemRefElement!=null)
    {
      Item resultItemRef=parseItemRef(resultItemRefElement);
      result.setItem(resultItemRef);
    }
    return result;
  }

  private Item parseItemRef(Element itemRef)
  {
    NamedNodeMap attrs=itemRef.getAttributes();
    // Item id
    int id=DOMParsingTools.getIntAttribute(attrs,RecipeXMLConstants.RECIPE_ITEM_ID_ATTR,0);
    Item item=ItemsManager.getInstance().getItem(id);
    if (item==null)
    {
      LOGGER.warn("Unknown item: "+item);
    }
    return item;
  }

  private IngredientPack parseIngredientPack(Element ingredientPackTag)
  {
    NamedNodeMap attrs=ingredientPackTag.getAttributes();
    // Item ID
    int id=DOMParsingTools.getIntAttribute(attrs,RecipeXMLConstants.RECIPE_ITEM_ID_ATTR,0);
    Item item=ItemsManager.getInstance().getItem(id);
    if (item==null)
    {
      LOGGER.warn("Unknown item: "+item);
      return null;
    }
    // Count
    int count=DOMParsingTools.getIntAttribute(attrs,RecipeXMLConstants.RECIPE_ITEM_COUNT_ATTR,1);
    return new IngredientPack(item,count);
  }
}
