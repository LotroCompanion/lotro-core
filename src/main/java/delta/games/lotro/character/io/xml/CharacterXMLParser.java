package delta.games.lotro.character.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.Character;
import delta.games.lotro.character.CharacterEquipment;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.CharacterEquipment.SlotContents;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLParser;
import delta.games.lotro.character.stats.tomes.TomesSet;
import delta.games.lotro.character.stats.virtues.VirtuesSet;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.VirtueId;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.io.xml.ItemXMLConstants;
import delta.games.lotro.lore.items.io.xml.ItemXMLParser;

/**
 * Parser for character infos stored in XML.
 * @author DAM
 */
public class CharacterXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed character or <code>null</code>.
   */
  public Character parseXML(File source)
  {
    Character c=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      c=parseCharacter(root);
    }
    return c;
  }

  private Character parseCharacter(Element root)
  {
    Character c=new Character();
    // Name
    String name=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_NAME_ATTR,"");
    c.setName(name);
    // Server
    String server=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_SERVER_ATTR,"");
    c.setServer(server);
    // Class
    String characterClass=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_CLASS_ATTR,"");
    CharacterClass cClass=CharacterClass.getByKey(characterClass);
    c.setCharacterClass(cClass);
    // Race
    String race=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_RACE_ATTR,"");
    Race cRace=Race.getByLabel(race); 
    c.setRace(cRace);
    // Region
    String region=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_REGION_ATTR,"");
    c.setRegion(region);
    // Level
    int level=DOMParsingTools.getIntAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_LEVEL_ATTR,0);
    c.setLevel(level);

    // Stats
    Element statsTag=DOMParsingTools.getChildTagByName(root,CharacterXMLConstants.STATS_TAG);
    if (statsTag!=null)
    {
      BasicStatsSet stats=BasicStatsSetXMLParser.parseStats(statsTag);
      c.getStats().setStats(stats);
    }
    // Equipment
    Element equipmentTag=DOMParsingTools.getChildTagByName(root,CharacterXMLConstants.EQUIPMENT_TAG);
    parseEquipment(c,equipmentTag);
    // Virtues
    Element virtuesTag=DOMParsingTools.getChildTagByName(root,CharacterXMLConstants.VIRTUES_TAG);
    parseVirtues(c,virtuesTag);
    // Tomes
    Element tomesTag=DOMParsingTools.getChildTagByName(root,CharacterXMLConstants.TOMES_TAG);
    parseTomes(c,tomesTag);
    return c;
  }

  private void parseEquipment(Character c, Element equipmentTag)
  {
    if (equipmentTag!=null)
    {
      CharacterEquipment equipment=c.getEquipment();
      List<Element> slotTags=DOMParsingTools.getChildTagsByName(equipmentTag,CharacterXMLConstants.SLOT_TAG);
      for(Element slotTag : slotTags)
      {
        NamedNodeMap attrs=slotTag.getAttributes();
        // Slot name
        String name=DOMParsingTools.getStringAttribute(attrs,CharacterXMLConstants.SLOT_NAME_ATTR,"");
        EQUIMENT_SLOT slot=EQUIMENT_SLOT.valueOf(name);
        if (slot!=null)
        {
          // Slots attributes
          String objectURL=DOMParsingTools.getStringAttribute(attrs,CharacterXMLConstants.SLOT_OBJECT_URL_ATTR,"");
          String iconURL=DOMParsingTools.getStringAttribute(attrs,CharacterXMLConstants.SLOT_ICON_URL_ATTR,"");
          SlotContents slotContents=equipment.getSlotContents(slot,true);
          slotContents.setObjectURL(objectURL);
          slotContents.setIconURL(iconURL);
          // Embedded item
          ItemXMLParser itemParser=new ItemXMLParser();
          Element itemTag=DOMParsingTools.getChildTagByName(slotTag,ItemXMLConstants.ITEM_TAG);
          if (itemTag!=null)
          {
            Item item=itemParser.parseItem(itemTag);
            slotContents.setItem(item);
          }
        }
      }
    }
  }

  private void parseVirtues(Character c, Element virtuesTag)
  {
    if (virtuesTag!=null)
    {
      VirtuesSet virtues=c.getVirtues();
      List<Element> virtueTags=DOMParsingTools.getChildTagsByName(virtuesTag,CharacterXMLConstants.VIRTUE_TAG);
      for(Element virtueTag : virtueTags)
      {
        NamedNodeMap attrs=virtueTag.getAttributes();
        String virtueIdStr=DOMParsingTools.getStringAttribute(attrs,CharacterXMLConstants.VIRTUE_ID,"");
        VirtueId virtue=VirtueId.valueOf(virtueIdStr);
        if (virtue!=null)
        {
          int rank=DOMParsingTools.getIntAttribute(attrs,CharacterXMLConstants.VIRTUE_RANK,0);
          virtues.setVirtueValue(virtue,rank);
          int index=DOMParsingTools.getIntAttribute(attrs,CharacterXMLConstants.VIRTUE_INDEX,-1);
          if (index!=-1)
          {
            virtues.setSelectedVirtue(virtue,index);
          }
        }
      }
    }
  }

  private void parseTomes(Character c, Element tomesTag)
  {
    if (tomesTag!=null)
    {
      TomesSet tomes=c.getTomes();
      List<Element> tomeTags=DOMParsingTools.getChildTagsByName(tomesTag,CharacterXMLConstants.TOME_TAG);
      for(Element tomeTag : tomeTags)
      {
        NamedNodeMap attrs=tomeTag.getAttributes();
        String statKey=DOMParsingTools.getStringAttribute(attrs,CharacterXMLConstants.TOME_STAT,"");
        STAT stat=STAT.getByName(statKey);
        if (stat!=null)
        {
          int rank=DOMParsingTools.getIntAttribute(attrs,CharacterXMLConstants.TOME_RANK,0);
          tomes.setTomeRank(stat,rank);
        }
      }
    }
  }
}
