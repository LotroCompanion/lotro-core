package delta.games.lotro.lore.crafting.recipes.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.enums.CraftingUICategory;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.recipes.CraftingResult;
import delta.games.lotro.lore.crafting.recipes.Ingredient;
import delta.games.lotro.lore.crafting.recipes.IngredientPack;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.crafting.recipes.RecipeVersion;
import delta.games.lotro.lore.items.Item;

/**
 * Writes LOTRO recipes to XML files.
 * @author DAM
 */
public class RecipeXMLWriter
{
  /**
   * Write a recipe to a XML file.
   * @param outFile Output file.
   * @param recipe Recipe to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final Recipe recipe, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,recipe);
      }
    };
    return helper.write(outFile,encoding,writer);
  }

  /**
   * Write recipes to a XML file.
   * @param outFile Output file.
   * @param recipes Recipes to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final List<Recipe> recipes, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",RecipeXMLConstants.RECIPES_TAG,new AttributesImpl());
        for(Recipe recipe : recipes)
        {
          write(hd,recipe);
        }
        hd.endElement("","",RecipeXMLConstants.RECIPES_TAG);
      }
    };
    return helper.write(outFile,encoding,writer);
  }

  private void write(TransformerHandler hd, Recipe recipe) throws SAXException
  {
    AttributesImpl recipeAttrs=new AttributesImpl();

    // ID
    int id=recipe.getIdentifier();
    if (id!=0)
    {
      recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Name
    String name=recipe.getName();
    if (name.length()>0)
    {
      recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Profession
    Profession profession=recipe.getProfession();
    if (profession!=null)
    {
      recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_PROFESSION_ATTR,XmlWriter.CDATA,profession.getKey());
    }
    // Tier
    int tier=recipe.getTier();
    recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_TIER_ATTR,XmlWriter.CDATA,String.valueOf(tier));
    // Category
    CraftingUICategory category=recipe.getCategory();
    if (category!=null)
    {
      recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(category.getCode()));
    }
    // XP
    int xp=recipe.getXP();
    if (xp!=0)
    {
      recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_XP_ATTR,XmlWriter.CDATA,String.valueOf(xp));
    }
    // Cooldown
    int cooldown=recipe.getCooldown();
    if (cooldown>0)
    {
      recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_COOLDOWN_ATTR,XmlWriter.CDATA,String.valueOf(cooldown));
    }
    // Single use
    boolean singleUse=recipe.isOneTimeUse();
    if (singleUse)
    {
      recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_SINGLE_USE_ATTR,XmlWriter.CDATA,String.valueOf(singleUse));
    }
    // Guild
    boolean guildRequired=recipe.isGuildRequired();
    if (guildRequired)
    {
      recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_GUILD_ATTR,XmlWriter.CDATA,String.valueOf(guildRequired));
    }

    hd.startElement("","",RecipeXMLConstants.RECIPE_TAG,recipeAttrs);
    // Recipe scroll
    Item ref=recipe.getRecipeScroll();
    if (ref!=null)
    {
      writeItemRef(hd,ref,RecipeXMLConstants.SCROLL_ITEM_TAG);
    }
    // Ingredient pack
    IngredientPack ingredientPack=recipe.getIngredientPack();
    if (ingredientPack!=null)
    {
      writeIngredientPack(hd,ingredientPack);
    }

    // Versions
    List<RecipeVersion> versions=recipe.getVersions();
    for(RecipeVersion version : versions)
    {
      writeRecipeVersion(hd,version);
    }
    hd.endElement("","",RecipeXMLConstants.RECIPE_TAG);
  }

  private void writeRecipeVersion(TransformerHandler hd, RecipeVersion version) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    Integer baseCriticalChance=version.getBaseCriticalChance();
    if (baseCriticalChance!=null)
    {
      attrs.addAttribute("","",RecipeXMLConstants.RECIPE_RESULT_BASE_CRITICAL_CHANCE_ATTR,XmlWriter.CDATA,baseCriticalChance.toString());
    }
    hd.startElement("","",RecipeXMLConstants.RECIPE_RESULT_TAG,attrs);
    // Ingredients
    List<Ingredient> ingredients=version.getIngredients();
    if (ingredients!=null)
    {
      for(Ingredient ingredient : ingredients)
      {
        writeIngredient(hd,ingredient);
      }
    }
    // Regular result
    CraftingResult regular=version.getRegular();
    if (regular!=null)
    {
      writeCraftingResult(hd,regular);
    }
    // Critical result
    CraftingResult critical=version.getCritical();
    if (critical!=null)
    {
      writeCraftingResult(hd,critical);
    }
    hd.endElement("","",RecipeXMLConstants.RECIPE_RESULT_TAG);
  }

  private void writeIngredient(TransformerHandler hd, Ingredient ingredient) throws SAXException
  {
    AttributesImpl ingredientsAttrs=new AttributesImpl();
    int quantity=ingredient.getQuantity();
    if (quantity!=1)
    {
      ingredientsAttrs.addAttribute("","",RecipeXMLConstants.INGREDIENT_QUANTITY_ATTR,XmlWriter.CDATA,String.valueOf(quantity));
    }
    if (ingredient.isOptional())
    {
      ingredientsAttrs.addAttribute("","",RecipeXMLConstants.INGREDIENT_OPTIONAL_ATTR,XmlWriter.CDATA,"true");
      Integer criticalChanceBonus=ingredient.getCriticalChanceBonus();
      if (criticalChanceBonus!=null)
      {
        ingredientsAttrs.addAttribute("","",RecipeXMLConstants.INGREDIENT_CRITICAL_CHANCE_BONUS_ATTR,XmlWriter.CDATA,criticalChanceBonus.toString());
      }
    }
    hd.startElement("","",RecipeXMLConstants.INGREDIENT_TAG,ingredientsAttrs);
    Item item=ingredient.getItem();
    if (item!=null)
    {
      writeItemRef(hd,item,RecipeXMLConstants.INGREDIENT_ITEM_TAG);
    }
    hd.endElement("","",RecipeXMLConstants.INGREDIENT_TAG);
  }

  private void writeCraftingResult(TransformerHandler hd, CraftingResult result) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    int quantity=result.getQuantity();
    if (quantity!=1)
    {
      attrs.addAttribute("","",RecipeXMLConstants.RESULT_QUANTITY_ATTR,XmlWriter.CDATA,String.valueOf(quantity));
    }
    int itemLevel=result.getItemLevel();
    if (itemLevel!=0)
    {
      attrs.addAttribute("","",RecipeXMLConstants.RESULT_ITEM_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(itemLevel));
    }
    if (result.isCriticalResult())
    {
      attrs.addAttribute("","",RecipeXMLConstants.RESULT_CRITICAL_ATTR,XmlWriter.CDATA,"true");
    }
    hd.startElement("","",RecipeXMLConstants.RESULT_TAG,attrs);
    Item itemResult=result.getItem();
    if (itemResult!=null)
    {
      writeItemRef(hd,itemResult,RecipeXMLConstants.RESULT_ITEM_TAG);
    }
    hd.endElement("","",RecipeXMLConstants.RESULT_TAG);
  }

  private void writeItemRef(TransformerHandler hd, Item ref, String tagName) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    int id=ref.getIdentifier();
    if (id!=0)
    {
      attrs.addAttribute("","",RecipeXMLConstants.RECIPE_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    String name=ref.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",RecipeXMLConstants.RECIPE_ITEM_NAME_ATTR,XmlWriter.CDATA,name);
    }
    String icon=ref.getIcon();
    if (icon!=null)
    {
      attrs.addAttribute("","",RecipeXMLConstants.RECIPE_ITEM_ICON_ATTR,XmlWriter.CDATA,icon);
    }
    hd.startElement("","",tagName,attrs);
    hd.endElement("","",tagName);
  }

  private void writeIngredientPack(TransformerHandler hd, IngredientPack ingredientPack) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Item
    Item ref=ingredientPack.getItem();
    int id=ref.getIdentifier();
    attrs.addAttribute("","",RecipeXMLConstants.RECIPE_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    String name=ref.getName();
    attrs.addAttribute("","",RecipeXMLConstants.RECIPE_ITEM_NAME_ATTR,XmlWriter.CDATA,name);
    // Count
    int count=ingredientPack.getCount();
    if (count!=1)
    {
      attrs.addAttribute("","",RecipeXMLConstants.RECIPE_ITEM_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
    }
    hd.startElement("","",RecipeXMLConstants.INGREDIENT_PACK_TAG,attrs);
    hd.endElement("","",RecipeXMLConstants.INGREDIENT_PACK_TAG);
  }
}
