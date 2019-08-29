package delta.games.lotro.lore.items.legendary.global.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.ItemQuality;
import delta.games.lotro.lore.items.legendary.global.LegendaryData;
import delta.games.lotro.lore.items.legendary.global.QualityBasedData;

/**
 * Parser for data of the legendary system stored in XML.
 * @author DAM
 */
public class LegendaryDataXMLParser
{
  /**
   * Parse a legendary data XML file.
   * @param source Source file.
   * @return Parsed data.
   */
  public static LegendaryData parseLegendaryDataFile(File source)
  {
    LegendaryData ret=new LegendaryData();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> qualityTags=DOMParsingTools.getChildTagsByName(root,LegendaryDataXMLConstants.QUALITY_TAG);
      for(Element qualityTag : qualityTags)
      {
        parseQualityData(qualityTag,ret);
      }
    }
    return ret;
  }

  private static QualityBasedData parseQualityData(Element root,LegendaryData data)
  {
    String qualityStr=DOMParsingTools.getStringAttribute(root.getAttributes(),LegendaryDataXMLConstants.QUALITY_ID_ATTR,null);
    ItemQuality quality=ItemQuality.fromCode(qualityStr);
    QualityBasedData qualityData=data.getQualityData(quality,true);
    // Start levels
    parseStartLevels(root,qualityData);
    // XP table
    int[] xpTable=parseTable(root,LegendaryDataXMLConstants.XP_TAG,LegendaryDataXMLConstants.XP_LEVEL_ATTR,LegendaryDataXMLConstants.XP_VALUE_ATTR);
    qualityData.setXpTable(xpTable);
    // Points tables
    List<Element> pointsTableTags=DOMParsingTools.getChildTagsByName(root,LegendaryDataXMLConstants.POINTS_TABLE_TAG);
    for(Element pointsTableTag : pointsTableTags)
    {
      String slotStr=DOMParsingTools.getStringAttribute(pointsTableTag.getAttributes(),LegendaryDataXMLConstants.POINTS_TABLE_SLOT_ATTR,null);
      EquipmentLocation slot=EquipmentLocation.getByKey(slotStr);
      int[] points=parseTable(pointsTableTag,LegendaryDataXMLConstants.POINTS_TAG,LegendaryDataXMLConstants.POINTS_LEVEL_ATTR,LegendaryDataXMLConstants.POINTS_VALUE_ATTR);
      qualityData.setPointsTable(slot,points);
    }
    return qualityData;
  }

  private static void parseStartLevels(Element root, QualityBasedData qualityData)
  {
    List<Element> startProgressionTags=DOMParsingTools.getChildTagsByName(root,LegendaryDataXMLConstants.START_PROGRESSION_TAG);
    for(Element startProgressionTag : startProgressionTags)
    {
      NamedNodeMap attrs=startProgressionTag.getAttributes();
      int itemLevel=DOMParsingTools.getIntAttribute(attrs,LegendaryDataXMLConstants.START_PROGRESSION_ITEM_LEVEL_ATTR,0);
      int startLevel=DOMParsingTools.getIntAttribute(attrs,LegendaryDataXMLConstants.START_PROGRESSION_START_LEVEL_ATTR,0);
      qualityData.addStartProgressionLevel(itemLevel,startLevel);
    }
  }

  private static int[] parseTable(Element root, String tagName, String xAttr, String yAttr)
  {
    int[] ret=null;
    List<Element> tags=DOMParsingTools.getChildTagsByName(root,tagName);
    int nbValues=tags.size();
    if (nbValues>0)
    {
      ret=new int[nbValues+1];
      for(Element tag : tags)
      {
        NamedNodeMap attrs=tag.getAttributes();
        int x=DOMParsingTools.getIntAttribute(attrs,xAttr,0);
        int y=DOMParsingTools.getIntAttribute(attrs,yAttr,0);
        ret[x]=y;
      }
    }
    return ret;
  }
}
