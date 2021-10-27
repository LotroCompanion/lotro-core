package delta.games.lotro.character.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.NumericTools;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.CharacterDataSummary;
import delta.games.lotro.character.CharacterEquipment;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.CharacterEquipment.SlotContents;
import delta.games.lotro.character.CharacterSummary;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLParser;
import delta.games.lotro.character.stats.buffs.io.xml.BuffsXMLConstants;
import delta.games.lotro.character.stats.buffs.io.xml.BuffsXMLParser;
import delta.games.lotro.character.stats.tomes.TomesSet;
import delta.games.lotro.character.stats.virtues.VirtuesSet;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.io.xml.ItemXMLConstants;
import delta.games.lotro.lore.items.io.xml.ItemXMLParser;
import delta.games.lotro.utils.PersistenceVersions;

/**
 * Parser for character infos stored in XML.
 * @author DAM
 */
public class CharacterXMLParser
{
  private static final String URL_SEED="http://lorebook.lotro.com/wiki/Special:LotroResource?id=";

  /**
   * Parse the XML file.
   * @param source Source file.
   * @param data Data to write to.
   * @return Parsed character or <code>null</code>.
   */
  public boolean parseXML(File source, CharacterData data)
  {
    boolean ret=false;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseCharacterData(root,data);
    }
    return ret;
  }

  private boolean parseCharacterData(Element root, CharacterData c)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Version
    int version=DOMParsingTools.getIntAttribute(attrs,CharacterXMLConstants.CHARACTER_VERSION_ATTR,1000);
    boolean versionOk=checkVersion(version);
    if (!versionOk)
    {
      return false;
    }
    // Summary
    CharacterDataSummary dataSummary=c.getSummary();
    CharacterSummaryXMLParser.parseCharacterDataSummary(root,dataSummary);
    CharacterSummary summary=new CharacterSummary();
    dataSummary.setSummary(summary);
    Element characterSummaryTag=DOMParsingTools.getChildTagByName(root,CharacterXMLConstants.CHARACTER_SUMMARY_TAG);
    if (characterSummaryTag!=null)
    {
      CharacterSummaryXMLParser.parseCharacterSummary(characterSummaryTag,summary);
    }
    else
    {
      CharacterSummaryXMLParser.parseCharacterSummary(root,summary);
    }
    // Short description
    String shortDescription=DOMParsingTools.getStringAttribute(attrs,CharacterXMLConstants.CHARACTER_SHORT_DESCRIPTION_ATTR,"");
    c.setShortDescription(shortDescription);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,CharacterXMLConstants.CHARACTER_DESCRIPTION_ATTR,"");
    c.setDescription(description);
    // Date
    long date=DOMParsingTools.getLongAttribute(attrs,CharacterXMLConstants.CHARACTER_DATE_ATTR,0);
    c.setDate((date==0)?null:Long.valueOf(date));

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
    // Buffs
    Element buffsTag=DOMParsingTools.getChildTagByName(root,BuffsXMLConstants.BUFFS_TAG);
    BuffsXMLParser.parseBuffs(buffsTag,c.getBuffs());
    // Additional stats
    Element additionalStatsTag=DOMParsingTools.getChildTagByName(root,CharacterXMLConstants.ADDITIONAL_STATS_TAG);
    if (additionalStatsTag!=null)
    {
      BasicStatsSet additionalStats=BasicStatsSetXMLParser.parseStats(additionalStatsTag);
      c.getAdditionalStats().setStats(additionalStats);
    }
    return true;
  }

  private void parseEquipment(CharacterData c, Element equipmentTag)
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
          SlotContents slotContents=equipment.getSlotContents(slot,true);
          if (objectURL!=null)
          {
            Integer itemId=idFromURL(objectURL);
            slotContents.setItemId(itemId);
          }
          int itemId=DOMParsingTools.getIntAttribute(attrs,CharacterXMLConstants.SLOT_ITEM_ID_ATTR,-1);
          if (itemId!=-1)
          {
            slotContents.setItemId(Integer.valueOf(itemId));
          }
          // Embedded item
          ItemXMLParser itemParser=new ItemXMLParser();
          Element itemTag=DOMParsingTools.getChildTagByName(slotTag,ItemXMLConstants.ITEM_TAG);
          if (itemTag!=null)
          {
            ItemInstance<? extends Item> item=itemParser.parseItemInstance(itemTag);
            item.setWearer(c.getSummary());
            slotContents.setItem(item);
          }
        }
      }
    }
  }

  /**
   * Extract item identifier from LOTRO resource URL.
   * @param url URL to use.
   * @return An item identifier or <code>null</code> if URL does not fit.
   */
  public static Integer idFromURL(String url)
  {
    Integer ret=null;
    if ((url!=null) && (url.startsWith(URL_SEED)))
    {
      String idStr=url.substring(URL_SEED.length());
      ret=NumericTools.parseInteger(idStr,true);
    }
    return ret;
  }

  private void parseVirtues(CharacterData c, Element virtuesTag)
  {
    if (virtuesTag!=null)
    {
      VirtuesManager virtuesMgr=VirtuesManager.getInstance();
      VirtuesSet virtues=c.getVirtues();
      List<Element> virtueTags=DOMParsingTools.getChildTagsByName(virtuesTag,CharacterXMLConstants.VIRTUE_TAG);
      for(Element virtueTag : virtueTags)
      {
        NamedNodeMap attrs=virtueTag.getAttributes();
        String virtueIdStr=DOMParsingTools.getStringAttribute(attrs,CharacterXMLConstants.VIRTUE_ID,"");
        VirtueDescription virtue=virtuesMgr.getByKey(virtueIdStr);
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

  private void parseTomes(CharacterData c, Element tomesTag)
  {
    if (tomesTag!=null)
    {
      TomesSet tomes=c.getTomes();
      List<Element> tomeTags=DOMParsingTools.getChildTagsByName(tomesTag,CharacterXMLConstants.TOME_TAG);
      for(Element tomeTag : tomeTags)
      {
        NamedNodeMap attrs=tomeTag.getAttributes();
        String statKey=DOMParsingTools.getStringAttribute(attrs,CharacterXMLConstants.TOME_STAT,"");
        StatDescription stat=StatsRegistry.getInstance().getByKey(statKey);
        if (stat!=null)
        {
          int rank=DOMParsingTools.getIntAttribute(attrs,CharacterXMLConstants.TOME_RANK,0);
          tomes.setTomeRank(stat,rank);
        }
      }
    }
  }

  private boolean checkVersion(int version)
  {
    if (version<=PersistenceVersions.CHARACTER_DATA)
    {
      return true;
    }
    return false;
  }
}
