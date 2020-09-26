package delta.games.lotro.lore.instances.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.geo.BlockReference;
import delta.games.lotro.lore.instances.PrivateEncounter;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;
import delta.games.lotro.lore.instances.ZoneAndMap;

/**
 * Writes private encounters to XML files.
 * @author DAM
 */
public class PrivateEncountersXMLWriter
{
  /**
   * Write a file with private encounters.
   * @param toFile Output file.
   * @param data Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writePrivateEncountersFile(File toFile, List<PrivateEncounter> data)
  {
    PrivateEncountersXMLWriter writer=new PrivateEncountersXMLWriter();
    boolean ok=writer.writePrivateEncounters(toFile,data,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write private encounter to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writePrivateEncounters(File outFile, final List<PrivateEncounter> data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writePrivateEncounters(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writePrivateEncounters(TransformerHandler hd, List<PrivateEncounter> data) throws Exception
  {
    hd.startElement("","",PrivateEncountersXMLConstants.PRIVATE_ENCOUNTERS_TAG,new AttributesImpl());
    for(PrivateEncounter privateEncounter : data)
    {
      writePrivateEncounter(hd,privateEncounter);
    }
    hd.endElement("","",PrivateEncountersXMLConstants.PRIVATE_ENCOUNTERS_TAG);
  }

  private void writePrivateEncounter(TransformerHandler hd, PrivateEncounter privateEncounter) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    boolean isSkirmishPE=(privateEncounter instanceof SkirmishPrivateEncounter);
    String tagName=isSkirmishPE?PrivateEncountersXMLConstants.SKIRMISH_PRIVATE_ENCOUNTER_TAG:PrivateEncountersXMLConstants.PRIVATE_ENCOUNTER_TAG;
    // In-game identifier
    int id=privateEncounter.getIdentifier();
    attrs.addAttribute("","",PrivateEncountersXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=privateEncounter.getName();
    attrs.addAttribute("","",PrivateEncountersXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    // Content layer
    int contentLayerId=privateEncounter.getContentLayerId();
    attrs.addAttribute("","",PrivateEncountersXMLConstants.CONTENT_LAYER_ID_ATTR,XmlWriter.CDATA,String.valueOf(contentLayerId));
    // Quest ID
    Integer questId=privateEncounter.getQuestId();
    if (questId!=null)
    {
      attrs.addAttribute("","",PrivateEncountersXMLConstants.QUEST_ID_ATTR,XmlWriter.CDATA,questId.toString());
    }
    // Max players
    Integer maxPlayers=privateEncounter.getMaxPlayers();
    if (maxPlayers!=null)
    {
      attrs.addAttribute("","",PrivateEncountersXMLConstants.MAX_PLAYERS_ATTR,XmlWriter.CDATA,maxPlayers.toString());
    }
    if (isSkirmishPE)
    {
      SkirmishPrivateEncounter skirmishPE=(SkirmishPrivateEncounter)privateEncounter;
      // Level scaling
      Integer levelScaling=skirmishPE.getLevelScaling();
      if (levelScaling!=null)
      {
        attrs.addAttribute("","",PrivateEncountersXMLConstants.LEVEL_SCALING_ATTR,XmlWriter.CDATA,levelScaling.toString());
      }
    }
    // Description
    String description=privateEncounter.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",PrivateEncountersXMLConstants.DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    hd.startElement("","",tagName,attrs);
    // Blocks
    for(BlockReference block : privateEncounter.getBlocks())
    {
      AttributesImpl blockAttrs=new AttributesImpl();
      // Region
      int region=block.getRegion();
      blockAttrs.addAttribute("","",PrivateEncountersXMLConstants.BLOCK_REGION_ATTR,XmlWriter.CDATA,String.valueOf(region));
      // X
      int x=block.getBlockX();
      blockAttrs.addAttribute("","",PrivateEncountersXMLConstants.BLOCK_X_ATTR,XmlWriter.CDATA,String.valueOf(x));
      // Y
      int y=block.getBlockY();
      blockAttrs.addAttribute("","",PrivateEncountersXMLConstants.BLOCK_Y_ATTR,XmlWriter.CDATA,String.valueOf(y));
      hd.startElement("","",PrivateEncountersXMLConstants.BLOCK_TAG,blockAttrs);
      hd.endElement("","",PrivateEncountersXMLConstants.BLOCK_TAG);
    }
    // Zone and map items
    for(ZoneAndMap zoneAndMap : privateEncounter.getZoneAndMapItems())
    {
      AttributesImpl zoneAndMapAttrs=new AttributesImpl();
      // Zone ID
      int zoneId=zoneAndMap.getZoneId();
      zoneAndMapAttrs.addAttribute("","",PrivateEncountersXMLConstants.ZONE_ID_ATTR,XmlWriter.CDATA,String.valueOf(zoneId));
      // Basemap ID
      Integer basemapId=zoneAndMap.getMapId();
      if (basemapId!=null)
      {
        zoneAndMapAttrs.addAttribute("","",PrivateEncountersXMLConstants.BASEMAP_ID_ATTR,XmlWriter.CDATA,basemapId.toString());
      }
      hd.startElement("","",PrivateEncountersXMLConstants.ZONE_AND_MAP_TAG,zoneAndMapAttrs);
      hd.endElement("","",PrivateEncountersXMLConstants.ZONE_AND_MAP_TAG);
    }
    // Quests to bestow
    for(Integer questToBestowId : privateEncounter.getQuestsToBestow())
    {
      AttributesImpl questToBestowAttrs=new AttributesImpl();
      questToBestowAttrs.addAttribute("","",PrivateEncountersXMLConstants.QUEST_ID_ATTR,XmlWriter.CDATA,questToBestowId.toString());
      hd.startElement("","",PrivateEncountersXMLConstants.QUEST_TO_BESTOW_TAG,questToBestowAttrs);
      hd.endElement("","",PrivateEncountersXMLConstants.QUEST_TO_BESTOW_TAG);
    }
    hd.endElement("","",tagName);
  }
}
