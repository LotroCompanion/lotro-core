package delta.games.lotro.lore.crafting.recipes.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.lore.crafting.recipes.CraftingResult;
import delta.games.lotro.lore.crafting.recipes.Ingredient;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.crafting.recipes.RecipeVersion;
import delta.games.lotro.lore.items.ItemProxy;

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

  private void write(TransformerHandler hd, Recipe recipe) throws Exception
  {
    AttributesImpl recipeAttrs=new AttributesImpl();

    // ID
    int id=recipe.getIdentifier();
    if (id!=0)
    {
      recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Lorebook wiki key
    String key=recipe.getKey();
    if (key!=null)
    {
      recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_KEY_ATTR,XmlWriter.CDATA,key);
    }
    // Name
    String name=recipe.getName();
    if (name!=null)
    {
      recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Profession
    String profession=recipe.getProfession();
    if (profession!=null)
    {
      recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_PROFESSION_ATTR,XmlWriter.CDATA,profession);
    }
    // Tier
    int tier=recipe.getTier();
    recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_TIER_ATTR,XmlWriter.CDATA,String.valueOf(tier));
    // Category
    String category=recipe.getCategory();
    if (category!=null)
    {
      recipeAttrs.addAttribute("","",RecipeXMLConstants.RECIPE_CATEGORY_ATTR,XmlWriter.CDATA,category);
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

    hd.startElement("","",RecipeXMLConstants.RECIPE_TAG,recipeAttrs);
    ItemProxy ref=recipe.getRecipeScroll();
    if (ref!=null)
    {
      writeItemRef(hd,ref,RecipeXMLConstants.SCROLL_ITEM_TAG);
    }
    // Ingredients
    List<Ingredient> ingredients=recipe.getIngredients();
    if (ingredients!=null)
    {
      for(Ingredient ingredient : ingredients)
      {
        AttributesImpl attrs=new AttributesImpl();
        int quantity=ingredient.getQuantity();
        if (quantity!=1)
        {
          attrs.addAttribute("","",RecipeXMLConstants.INGREDIENT_QUANTITY_ATTR,XmlWriter.CDATA,String.valueOf(quantity));
        }
        if (ingredient.isOptional())
        {
          attrs.addAttribute("","",RecipeXMLConstants.INGREDIENT_OPTIONAL_ATTR,XmlWriter.CDATA,"true");
        }
        hd.startElement("","",RecipeXMLConstants.INGREDIENT_TAG,attrs);
        ItemProxy item=ingredient.getItem();
        if (item!=null)
        {
          writeItemRef(hd,item,RecipeXMLConstants.INGREDIENT_ITEM_TAG);
        }
        hd.endElement("","",RecipeXMLConstants.INGREDIENT_TAG);
      }
    }
    
    // Results
    List<RecipeVersion> results=recipe.getVersions();
    if (results!=null)
    {
      for(RecipeVersion result : results)
      {
        hd.startElement("","",RecipeXMLConstants.RECIPE_RESULT_TAG,new AttributesImpl());
        CraftingResult regular=result.getRegular();
        if (regular!=null)
        {
          writeCraftingResult(hd,regular);
        }
        CraftingResult critical=result.getCritical();
        if (critical!=null)
        {
          writeCraftingResult(hd,critical);
        }
        hd.endElement("","",RecipeXMLConstants.RECIPE_RESULT_TAG);
      }
    }
    hd.endElement("","",RecipeXMLConstants.RECIPE_TAG);
  }

  private void writeCraftingResult(TransformerHandler hd, CraftingResult result) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    int quantity=result.getQuantity();
    if (quantity!=1)
    {
      attrs.addAttribute("","",RecipeXMLConstants.RESULT_QUANTITY_ATTR,XmlWriter.CDATA,String.valueOf(quantity));
    }
    if (result.isCriticalResult())
    {
      attrs.addAttribute("","",RecipeXMLConstants.RESULT_CRITICAL_ATTR,XmlWriter.CDATA,"true");
    }
    hd.startElement("","",RecipeXMLConstants.RESULT_TAG,attrs);
    ItemProxy itemResult=result.getItem();
    if (itemResult!=null)
    {
      writeItemRef(hd,itemResult,RecipeXMLConstants.RESULT_ITEM_TAG);
    }
    hd.endElement("","",RecipeXMLConstants.RESULT_TAG);
  }

  private void writeItemRef(TransformerHandler hd, ItemProxy ref, String tagName) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    int id=ref.getId();
    if (id!=0)
    {
      attrs.addAttribute("","",RecipeXMLConstants.RECIPE_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    String key=ref.getItemKey();
    if (key!=null)
    {
      attrs.addAttribute("","",RecipeXMLConstants.RECIPE_ITEM_KEY_ATTR,XmlWriter.CDATA,key);
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
}
