package delta.games.lotro.lore.relics.melding.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;
import delta.games.lotro.lore.relics.melding.MeldingInput;
import delta.games.lotro.lore.relics.melding.MeldingOutput;
import delta.games.lotro.lore.relics.melding.RelicMeldingRecipe;

/**
 * Parser for the melding recipes stored in XML.
 * @author DAM
 */
public class MeldingRecipesXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(MeldingRecipesXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed recipes or <code>null</code>.
   */
  public List<RelicMeldingRecipe> parseXML(File source)
  {
    List<RelicMeldingRecipe> ret=new ArrayList<RelicMeldingRecipe>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> recipeTags=DOMParsingTools.getChildTagsByName(root,MeldingRecipesXMLConstants.RECIPE_TAG,false);
      for(Element recipeTag : recipeTags)
      {
        RelicMeldingRecipe recipe=parseRecipe(recipeTag);
        ret.add(recipe);
      }
    }
    return ret;
  }

  private RelicMeldingRecipe parseRecipe(Element recipeTag)
  {
    NamedNodeMap attrs=recipeTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,MeldingRecipesXMLConstants.ID_ATTR,0);
    RelicMeldingRecipe ret=new RelicMeldingRecipe(id);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,MeldingRecipesXMLConstants.CATEGORY_ATTR,"");
    ret.setCategory(category);
    // Name override
    String nameOverride=DOMParsingTools.getStringAttribute(attrs,MeldingRecipesXMLConstants.NAME_OVERRIDE_ATTR,null);
    ret.setNameOverride(nameOverride);
    // Icon override
    int iconId=DOMParsingTools.getIntAttribute(attrs,MeldingRecipesXMLConstants.ICON_OVERRIDE_ATTR,0);
    ret.setIconOverride(iconId);
    // Tooltip override
    String tooltipOverride=DOMParsingTools.getStringAttribute(attrs,MeldingRecipesXMLConstants.TOOLTIP_OVERRIDE_ATTR,null);
    ret.setTooltipOverride(tooltipOverride);
    // Cost
    int cost=DOMParsingTools.getIntAttribute(attrs,MeldingRecipesXMLConstants.COST_ATTR,0);
    ret.setCost(cost);

    // Input
    MeldingInput input=ret.getInput();
    List<Element> inputTags=DOMParsingTools.getChildTagsByName(recipeTag,MeldingRecipesXMLConstants.INPUT_TAG);
    for(Element inputTag : inputTags)
    {
      NamedNodeMap inputAttrs=inputTag.getAttributes();
      int tier=DOMParsingTools.getIntAttribute(inputAttrs,MeldingRecipesXMLConstants.INPUT_TIER_ATTR,-1);
      if (tier>=0)
      {
        int count=DOMParsingTools.getIntAttribute(inputAttrs,MeldingRecipesXMLConstants.INPUT_COUNT_ATTR,1);
        input.setTierCount(tier,count);
        continue;
      }
      int relicId=DOMParsingTools.getIntAttribute(inputAttrs,MeldingRecipesXMLConstants.INPUT_RELIC_ID_ATTR,0);
      if (relicId>0)
      {
        int count=DOMParsingTools.getIntAttribute(inputAttrs,MeldingRecipesXMLConstants.INPUT_COUNT_ATTR,1);
        Relic relic=RelicsManager.getInstance().getById(relicId);
        if (relic!=null)
        {
          input.addNeededRelic(relic,count);
        }
        else
        {
          LOGGER.warn("Relic not found: ID="+relicId);
        }
      }
    }
    // Output
    MeldingOutput output=ret.getOutput();
    List<Element> outputTags=DOMParsingTools.getChildTagsByName(recipeTag,MeldingRecipesXMLConstants.OUTPUT_TAG);
    for(Element outputTag : outputTags)
    {
      NamedNodeMap outputAttrs=outputTag.getAttributes();
      // Weight
      int weight=DOMParsingTools.getIntAttribute(outputAttrs,MeldingRecipesXMLConstants.OUTPUT_WEIGHT_ATTR,1);
      // Relic
      int relicId=DOMParsingTools.getIntAttribute(outputAttrs,MeldingRecipesXMLConstants.OUTPUT_RELIC_ID_ATTR,0);
      if (relicId>0)
      {
        Relic relic=RelicsManager.getInstance().getById(relicId);
        if (relic!=null)
        {
          output.addOutput(relic,weight);
        }
        else
        {
          LOGGER.warn("Relic not found: ID="+relicId);
        }
      }
      // Item ID
      int itemId=DOMParsingTools.getIntAttribute(outputAttrs,MeldingRecipesXMLConstants.OUTPUT_ITEM_ID_ATTR,0);
      if (itemId>0)
      {
        Item item=ItemsManager.getInstance().getItem(itemId);
        if (item!=null)
        {
          output.addOutput(item,weight);
        }
        else
        {
          LOGGER.warn("Item not found: ID="+itemId);
        }
      }
    }
    return ret;
  }
}
