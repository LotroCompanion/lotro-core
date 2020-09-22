package delta.games.lotro.lore.instances.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.geo.BlockReference;
import delta.games.lotro.lore.instances.PrivateEncounter;
import delta.games.lotro.lore.instances.PrivateEncountersManager;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;

/**
 * Parser for the private encounters stored in XML.
 * @author DAM
 */
public class PrivateEncountersXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public PrivateEncountersManager parseXML(File source)
  {
    PrivateEncountersManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parsePrivateEncounters(root);
    }
    return ret;
  }

  /**
   * Build a private encounters manager from an XML tag.
   * @param rootTag Root tag.
   * @return A private encounters manager.
   */
  private PrivateEncountersManager parsePrivateEncounters(Element rootTag)
  {
    PrivateEncountersManager mgr=new PrivateEncountersManager();

    // Private encounters AND skirmish private encounters
    List<Element> privateEncounterTags=DOMParsingTools.getChildTags(rootTag);
    for(Element privateEncounterTag : privateEncounterTags)
    {
      PrivateEncounter privateEncounter=parsePrivateEncounter(privateEncounterTag);
      mgr.addPrivateEncounter(privateEncounter);
    }
    return mgr;
  }

  private PrivateEncounter parsePrivateEncounter(Element privateEncounterTag)
  {
    NamedNodeMap attrs=privateEncounterTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,PrivateEncountersXMLConstants.ID_ATTR,0);

    String tagName=privateEncounterTag.getNodeName();
    boolean isSkirmishPE=(PrivateEncountersXMLConstants.SKIRMISH_PRIVATE_ENCOUNTER_TAG.equals(tagName));
    PrivateEncounter ret=null;
    ret=(isSkirmishPE)?new SkirmishPrivateEncounter(id):new PrivateEncounter(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,PrivateEncountersXMLConstants.NAME_ATTR,"");
    ret.setName(name);
    // Content layer
    int contentLayerId=DOMParsingTools.getIntAttribute(attrs,PrivateEncountersXMLConstants.CONTENT_LAYER_ID_ATTR,0);
    ret.setContentLayerId(contentLayerId);
    // Quest ID
    int questId=DOMParsingTools.getIntAttribute(attrs,PrivateEncountersXMLConstants.QUEST_ID_ATTR,0);
    if (questId>0)
    {
      ret.setQuestId(Integer.valueOf(questId));
    }
    // Max players
    int maxPlayers=DOMParsingTools.getIntAttribute(attrs,PrivateEncountersXMLConstants.MAX_PLAYERS_ATTR,0);
    if (maxPlayers>0)
    {
      ret.setMaxPlayers(Integer.valueOf(maxPlayers));
    }
    if (isSkirmishPE)
    {
      SkirmishPrivateEncounter skirmishPE=(SkirmishPrivateEncounter)ret;
      // Level scaling
      int levelScaling=DOMParsingTools.getIntAttribute(attrs,PrivateEncountersXMLConstants.LEVEL_SCALING_ATTR,0);
      if (levelScaling>0)
      {
        skirmishPE.setLevelScaling(Integer.valueOf(levelScaling));
      }
    }
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,PrivateEncountersXMLConstants.DESCRIPTION_ATTR,"");
    ret.setDescription(description);

    // Blocks
    List<Element> blockTags=DOMParsingTools.getChildTagsByName(privateEncounterTag,PrivateEncountersXMLConstants.BLOCK_TAG);
    for(Element blockTag : blockTags)
    {
      NamedNodeMap blockAttrs=blockTag.getAttributes();
      BlockReference block=new BlockReference();
      // Region
      int region=DOMParsingTools.getIntAttribute(blockAttrs,PrivateEncountersXMLConstants.BLOCK_REGION_ATTR,0);
      block.setRegion(region);
      // X
      int x=DOMParsingTools.getIntAttribute(blockAttrs,PrivateEncountersXMLConstants.BLOCK_X_ATTR,0);
      // Y
      int y=DOMParsingTools.getIntAttribute(blockAttrs,PrivateEncountersXMLConstants.BLOCK_Y_ATTR,0);
      block.setBlock(x,y);
      ret.addBlock(block);
    }
    // Quests to bestow
    List<Element> questToBestowTags=DOMParsingTools.getChildTagsByName(privateEncounterTag,PrivateEncountersXMLConstants.QUEST_TO_BESTOW_TAG);
    for(Element questToBestowTag : questToBestowTags)
    {
      NamedNodeMap questToBestowAttrs=questToBestowTag.getAttributes();
      // Quest ID
      int questToBestowId=DOMParsingTools.getIntAttribute(questToBestowAttrs,PrivateEncountersXMLConstants.QUEST_ID_ATTR,0);
      ret.addQuestToBestow(questToBestowId);
    }
    return ret;
  }
}
