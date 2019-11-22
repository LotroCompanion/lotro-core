package delta.games.lotro.lore.items.legendary.global.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.ItemQuality;
import delta.games.lotro.lore.items.legendary.global.LegendaryData;
import delta.games.lotro.lore.items.legendary.global.QualityBasedData;

/**
 * Writes data of the legendary system to XML files.
 * @author DAM
 */
public class LegendaryDataXMLWriter
{
  /**
   * Write data of the legendary system to a XML file.
   * @param toFile File to write to.
   * @param data Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final LegendaryData data)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeLegendaryData(hd,data);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeLegendaryData(TransformerHandler hd, LegendaryData data) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Max UI rank
    int maxUiRank=data.getMaxUiRank();
    attrs.addAttribute("","",LegendaryDataXMLConstants.MAX_UI_RANK_ATTR,XmlWriter.CDATA,String.valueOf(maxUiRank));
    // Max main legacy rank
    int maxMainLegacyRank=data.getMaxMainLegacyRank();
    attrs.addAttribute("","",LegendaryDataXMLConstants.MAX_MAIN_LEGACY_RANK_ATTR,XmlWriter.CDATA,String.valueOf(maxMainLegacyRank));
    // Quality-based data
    hd.startElement("","",LegendaryDataXMLConstants.LEGENDARY_TAG,attrs);
    for(ItemQuality quality : ItemQuality.ALL)
    {
      QualityBasedData qualityData=data.getQualityData(quality,false);
      if (qualityData!=null)
      {
        writeQualityData(hd,qualityData);
      }
    }
    hd.endElement("","",LegendaryDataXMLConstants.LEGENDARY_TAG);
  }

  private static void writeQualityData(TransformerHandler hd, QualityBasedData qualityData) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    ItemQuality quality=qualityData.getQuality();
    attrs.addAttribute("","",LegendaryDataXMLConstants.QUALITY_ID_ATTR,XmlWriter.CDATA,quality.getKey());
    hd.startElement("","",LegendaryDataXMLConstants.QUALITY_TAG,attrs);
    // Write start levels
    Map<Integer,Integer> startLevelsMap=qualityData.getStartLevels();
    List<Integer> itemLevels=new ArrayList<Integer>(startLevelsMap.keySet());
    Collections.sort(itemLevels);
    for(Integer itemLevel : itemLevels)
    {
      AttributesImpl startLevelAttrs=new AttributesImpl();
      // Item level
      startLevelAttrs.addAttribute("","",LegendaryDataXMLConstants.START_PROGRESSION_ITEM_LEVEL_ATTR,XmlWriter.CDATA,itemLevel.toString());
      // Start progression level
      Integer startProgressionLevel=startLevelsMap.get(itemLevel);
      startLevelAttrs.addAttribute("","",LegendaryDataXMLConstants.START_PROGRESSION_START_LEVEL_ATTR,XmlWriter.CDATA,startProgressionLevel.toString());
      hd.startElement("","",LegendaryDataXMLConstants.START_PROGRESSION_TAG,startLevelAttrs);
      hd.endElement("","",LegendaryDataXMLConstants.START_PROGRESSION_TAG);
    }
    // Write XP table
    int[] xpTable=qualityData.getXpTable();
    if (xpTable!=null)
    {
      for(int i=1;i<xpTable.length;i++)
      {
        AttributesImpl xpAttrs=new AttributesImpl();
        // Legendary level
        xpAttrs.addAttribute("","",LegendaryDataXMLConstants.XP_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(i));
        // XP
        int xp=xpTable[i];
        xpAttrs.addAttribute("","",LegendaryDataXMLConstants.XP_VALUE_ATTR,XmlWriter.CDATA,String.valueOf(xp));
        hd.startElement("","",LegendaryDataXMLConstants.XP_TAG,xpAttrs);
        hd.endElement("","",LegendaryDataXMLConstants.XP_TAG);
      }
    }
    // Write points tables
    for(EquipmentLocation location : EquipmentLocation.getAll())
    {
      int[] pointsTable=qualityData.getPointsTable(location);
      if (pointsTable!=null)
      {
        AttributesImpl pointsTableAttrs=new AttributesImpl();
        pointsTableAttrs.addAttribute("","",LegendaryDataXMLConstants.POINTS_TABLE_SLOT_ATTR,XmlWriter.CDATA,location.getKey());
        hd.startElement("","",LegendaryDataXMLConstants.POINTS_TABLE_TAG,pointsTableAttrs);
        for(int i=1;i<xpTable.length;i++)
        {
          AttributesImpl pointsAttrs=new AttributesImpl();
          // Legendary level
          pointsAttrs.addAttribute("","",LegendaryDataXMLConstants.POINTS_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(i));
          // XP
          int points=pointsTable[i];
          pointsAttrs.addAttribute("","",LegendaryDataXMLConstants.POINTS_VALUE_ATTR,XmlWriter.CDATA,String.valueOf(points));
          hd.startElement("","",LegendaryDataXMLConstants.POINTS_TAG,pointsAttrs);
          hd.endElement("","",LegendaryDataXMLConstants.POINTS_TAG);
        }
        hd.endElement("","",LegendaryDataXMLConstants.POINTS_TABLE_TAG);
      }
    }
    hd.endElement("","",LegendaryDataXMLConstants.QUALITY_TAG);
  }
}
