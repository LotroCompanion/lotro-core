package delta.games.lotro.lore.relics.melding.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.relics.CountedRelic;
import delta.games.lotro.lore.relics.melding.MeldingInput;
import delta.games.lotro.lore.relics.melding.MeldingOutput;
import delta.games.lotro.lore.relics.melding.RelicMeldingOutputEntry;
import delta.games.lotro.lore.relics.melding.RelicMeldingRecipe;

/**
 * Writes melding recipes to an XML file.
 * @author DAM
 */
public class MeldingRecipesXMLWriter
{
  /**
   * Writes some melding recipes to an XML file.
   * @param outFile Output file.
   * @param recipes Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final List<RelicMeldingRecipe> recipes, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeData(hd,recipes);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write some melding recipes to the given XML stream.
   * @param hd XML output stream.
   * @param recipes Data to write.
   * @throws SAXException If an error occurs.
   */
  private void writeData(TransformerHandler hd, List<RelicMeldingRecipe> recipes) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",MeldingRecipesXMLConstants.MAIN_TAG,attrs);
    for(RelicMeldingRecipe recipe : recipes)
    {
      AttributesImpl recipeAttrs=new AttributesImpl();
      // ID
      int id=recipe.getIdentifier();
      recipeAttrs.addAttribute("","",MeldingRecipesXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Category
      String category=recipe.getCategory();
      recipeAttrs.addAttribute("","",MeldingRecipesXMLConstants.CATEGORY_ATTR,XmlWriter.CDATA,category);
      // Name override
      String nameOverride=recipe.getNameOverride();
      if (nameOverride!=null)
      {
        recipeAttrs.addAttribute("","",MeldingRecipesXMLConstants.NAME_OVERRIDE_ATTR,XmlWriter.CDATA,nameOverride);
      }
      // Icon override
      int iconId=recipe.getIconOverride();
      if (iconId!=0)
      {
        recipeAttrs.addAttribute("","",MeldingRecipesXMLConstants.ICON_OVERRIDE_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
      }
      // Tooltip override
      String tooltipOverride=recipe.getTooltipOverride();
      if (tooltipOverride!=null)
      {
        recipeAttrs.addAttribute("","",MeldingRecipesXMLConstants.TOOLTIP_OVERRIDE_ATTR,XmlWriter.CDATA,tooltipOverride);
      }
      // Cost
      int cost=recipe.getCost();
      recipeAttrs.addAttribute("","",MeldingRecipesXMLConstants.COST_ATTR,XmlWriter.CDATA,String.valueOf(cost));
      hd.startElement("","",MeldingRecipesXMLConstants.RECIPE_TAG,recipeAttrs);

      // Input
      MeldingInput input=recipe.getInput();
      // - relic tiers
      for(Integer tier : input.getNeededTiers())
      {
        AttributesImpl inputTierAttrs=new AttributesImpl();
        // Tier
        inputTierAttrs.addAttribute("","",MeldingRecipesXMLConstants.INPUT_TIER_ATTR,XmlWriter.CDATA,tier.toString());
        // Count
        int count=input.getCountForTier(tier.intValue());
        if (count!=1)
        {
          inputTierAttrs.addAttribute("","",MeldingRecipesXMLConstants.INPUT_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
        }
        hd.startElement("","",MeldingRecipesXMLConstants.INPUT_TAG,inputTierAttrs);
        hd.endElement("","",MeldingRecipesXMLConstants.INPUT_TAG);
      }
      // - specific relics
      for(CountedRelic countedRelic : input.getNeededRelics())
      {
        AttributesImpl inputRelicAttrs=new AttributesImpl();
        // Relic ID
        Relic relic=countedRelic.getRelic();
        int relicId=relic.getIdentifier();
        inputRelicAttrs.addAttribute("","",MeldingRecipesXMLConstants.INPUT_RELIC_ID_ATTR,XmlWriter.CDATA,String.valueOf(relicId));
        // Name
        String relicName=relic.getName();
        inputRelicAttrs.addAttribute("","",MeldingRecipesXMLConstants.INPUT_NAME_ATTR,XmlWriter.CDATA,relicName);
        // Count
        int count=countedRelic.getCount();
        if (count!=1)
        {
          inputRelicAttrs.addAttribute("","",MeldingRecipesXMLConstants.INPUT_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
        }
        hd.startElement("","",MeldingRecipesXMLConstants.INPUT_TAG,inputRelicAttrs);
        hd.endElement("","",MeldingRecipesXMLConstants.INPUT_TAG);
      }
      // Output
      MeldingOutput output=recipe.getOutput();
      for(RelicMeldingOutputEntry entry : output.getPossibleOutputs())
      {
        writeMeldingOutputEntry(hd,entry);
      }
      hd.endElement("","",MeldingRecipesXMLConstants.RECIPE_TAG);
    }
    hd.endElement("","",MeldingRecipesXMLConstants.MAIN_TAG);
 }

  private void writeMeldingOutputEntry(TransformerHandler hd, RelicMeldingOutputEntry entry) throws SAXException
  {
    AttributesImpl outputEntryAttrs=new AttributesImpl();
    String name=null;
    // Relic ID
    Relic outputRelic=entry.getRelic();
    if (outputRelic!=null)
    {
      int relicId=outputRelic.getIdentifier();
      outputEntryAttrs.addAttribute("","",MeldingRecipesXMLConstants.OUTPUT_RELIC_ID_ATTR,XmlWriter.CDATA,String.valueOf(relicId));
      name=outputRelic.getName();
    }
    // Item ID
    Item outputItem=entry.getItem();
    if (outputItem!=null)
    {
      int itemId=outputItem.getIdentifier();
      outputEntryAttrs.addAttribute("","",MeldingRecipesXMLConstants.OUTPUT_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemId));
      name=outputItem.getName();
    }
    // Name
    if (name!=null)
    {
      outputEntryAttrs.addAttribute("","",MeldingRecipesXMLConstants.OUTPUT_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Weight
    int weight=entry.getWeight();
    if (weight!=1)
    {
      outputEntryAttrs.addAttribute("","",MeldingRecipesXMLConstants.OUTPUT_WEIGHT_ATTR,XmlWriter.CDATA,String.valueOf(weight));
    }
    hd.startElement("","",MeldingRecipesXMLConstants.OUTPUT_TAG,outputEntryAttrs);
    hd.endElement("","",MeldingRecipesXMLConstants.OUTPUT_TAG);
  }
}
