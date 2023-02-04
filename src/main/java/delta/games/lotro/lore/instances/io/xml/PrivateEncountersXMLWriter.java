package delta.games.lotro.lore.instances.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.enums.Difficulty;
import delta.games.lotro.common.enums.GroupSize;
import delta.games.lotro.common.enums.WJEncounterCategory;
import delta.games.lotro.common.enums.WJEncounterType;
import delta.games.lotro.lore.instances.InstanceMapDescription;
import delta.games.lotro.lore.instances.PrivateEncounter;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;
import delta.games.lotro.lore.maps.MapDescription;
import delta.games.lotro.lore.maps.io.xml.MapDescriptionXMLWriter;

/**
 * Writes private encounters to XML files.
 * @author DAM
 */
public class PrivateEncountersXMLWriter
{
  private static final Logger LOGGER=Logger.getLogger(PrivateEncountersXMLWriter.class);

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
      // Category
      WJEncounterCategory category=skirmishPE.getCategory();
      if (category!=null)
      {
        attrs.addAttribute("","",PrivateEncountersXMLConstants.CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(category.getCode()));
      }
      // Type
      WJEncounterType type=skirmishPE.getType();
      if (type!=null)
      {
        attrs.addAttribute("","",PrivateEncountersXMLConstants.TYPE_ATTR,XmlWriter.CDATA,String.valueOf(type.getCode()));
      }
      // Min/max level
      int minLevel=skirmishPE.getMinLevelScale();
      attrs.addAttribute("","",PrivateEncountersXMLConstants.MIN_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel));
      int maxLevel=skirmishPE.getMaxLevelScale();
      attrs.addAttribute("","",PrivateEncountersXMLConstants.MAX_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(maxLevel));
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
    if (isSkirmishPE)
    {
      SkirmishPrivateEncounter skirmishPE=(SkirmishPrivateEncounter)privateEncounter;
      // Difficulty tiers
      for(Difficulty difficultyTier : skirmishPE.getDifficultyTiers())
      {
        AttributesImpl difficultyTierAttrs=new AttributesImpl();
        // Code
        int code=difficultyTier.getCode();
        difficultyTierAttrs.addAttribute("","",PrivateEncountersXMLConstants.DIFFICULTY_TIER_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
        // Name
        String difficultyName=difficultyTier.getLabel();
        difficultyTierAttrs.addAttribute("","",PrivateEncountersXMLConstants.DIFFICULTY_TIER_NAME_ATTR,XmlWriter.CDATA,difficultyName);
        hd.startElement("","",PrivateEncountersXMLConstants.DIFFICULTY_TIER_TAG,difficultyTierAttrs);
        hd.endElement("","",PrivateEncountersXMLConstants.DIFFICULTY_TIER_TAG);
      }
      // Group sizes
      for(GroupSize groupSize : skirmishPE.getGroupSizes())
      {
        AttributesImpl groupSizeAttrs=new AttributesImpl();
        // Key
        String groupSizeKey=groupSize.getKey();
        groupSizeAttrs.addAttribute("","",PrivateEncountersXMLConstants.GROUP_SIZE_KEY_ATTR,XmlWriter.CDATA,groupSizeKey);
        hd.startElement("","",PrivateEncountersXMLConstants.GROUP_SIZE_TAG,groupSizeAttrs);
        hd.endElement("","",PrivateEncountersXMLConstants.GROUP_SIZE_TAG);
      }
    }
    // Additional content layers
    for(Integer additionalContentLayer : privateEncounter.getAdditionalContentLayers())
    {
      AttributesImpl clAttrs=new AttributesImpl();
      // ID
      clAttrs.addAttribute("","",PrivateEncountersXMLConstants.CONTENT_LAYER_ID_ATTR,XmlWriter.CDATA,additionalContentLayer.toString());
      hd.startElement("","",PrivateEncountersXMLConstants.CONTENT_LAYER_TAG,clAttrs);
      hd.endElement("","",PrivateEncountersXMLConstants.CONTENT_LAYER_TAG);
    }

    // Maps
    for(InstanceMapDescription map : privateEncounter.getMapDescriptions())
    {
      hd.startElement("","",PrivateEncountersXMLConstants.INSTANCE_MAP_TAG,new AttributesImpl());
      MapDescription basemap=map.getMap();
      if (basemap!=null)
      {
        MapDescriptionXMLWriter.writeMapDescription(hd,basemap);
      }
      else
      {
        LOGGER.warn("No basemap for instance map!");
      }
      // Zones
      for(Integer zoneId : map.getZoneIds())
      {
        AttributesImpl zoneAttrs=new AttributesImpl();
        // Zone ID
        zoneAttrs.addAttribute("","",PrivateEncountersXMLConstants.ZONE_ID_ATTR,XmlWriter.CDATA,zoneId.toString());
        hd.startElement("","",PrivateEncountersXMLConstants.ZONE_TAG,zoneAttrs);
        hd.endElement("","",PrivateEncountersXMLConstants.ZONE_TAG);
      }
      hd.endElement("","",PrivateEncountersXMLConstants.INSTANCE_MAP_TAG);
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
