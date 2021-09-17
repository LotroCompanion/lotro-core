package delta.games.lotro.lore.agents.mobs.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.treasure.LootsManager;
import delta.games.lotro.common.treasure.TreasureList;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.lore.agents.io.xml.AgentsXMLIO;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.lore.agents.mobs.MobLoot;
import delta.games.lotro.lore.agents.mobs.MobsManager;

/**
 * Parser for the mobs stored in XML.
 * @author DAM
 */
public class MobsXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public MobsManager parseXML(File source)
  {
    MobsManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseMobs(root);
    }
    return ret;
  }

  /**
   * Build a mobs manager from an XML tag.
   * @param rootTag Root tag.
   * @return A mobs manager.
   */
  private MobsManager parseMobs(Element rootTag)
  {
    MobsManager mgr=new MobsManager();

    List<Element> mobTags=DOMParsingTools.getChildTags(rootTag);
    for(Element mobTag : mobTags)
    {
      MobDescription mob=parseMobTag(mobTag);
      mgr.addMob(mob);
    }
    return mgr;
  }

  private MobDescription parseMobTag(Element mobTag)
  {
    NamedNodeMap attrs=mobTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,MobsXMLConstants.ID_ATTR,0);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,MobsXMLConstants.NAME_ATTR,"");
    MobDescription ret=new MobDescription(id,name);
    // Classification
    AgentsXMLIO.parseClassificationTag(mobTag,ret.getClassification());
    // Loot
    Element mobLootTag=DOMParsingTools.getChildTagByName(mobTag,MobsXMLConstants.LOOT_TAG);
    if (mobLootTag!=null)
    {
      MobLoot loot=parseMobLoot(mobLootTag);
      ret.setMobLoot(loot);
    }
    return ret;
  }


  private MobLoot parseMobLoot(Element mobLootTag)
  {
    NamedNodeMap attrs=mobLootTag.getAttributes();

    LootsManager lootsMgr=LootsManager.getInstance();
    MobLoot loot=new MobLoot();
    // Barter trophy list
    int barterTrophyListId=DOMParsingTools.getIntAttribute(attrs,MobsXMLConstants.BARTER_TROPHY_LIST_ID_ATTR,0);
    if (barterTrophyListId!=0)
    {
      TrophyList trophyList=lootsMgr.getTrophyLists().getItem(barterTrophyListId);
      loot.setBarterTrophy(trophyList);
    }
    // Reputation trophy list
    int reputationTrophyListId=DOMParsingTools.getIntAttribute(attrs,MobsXMLConstants.REPUTATION_TROPHY_LIST_ID_ATTR,0);
    if (reputationTrophyListId!=0)
    {
      TrophyList trophyList=lootsMgr.getTrophyLists().getItem(reputationTrophyListId);
      loot.setReputationTrophy(trophyList);
    }
    // Treasure list override list
    int treasureListId=DOMParsingTools.getIntAttribute(attrs,MobsXMLConstants.TREASURE_LIST_OVERRIDE_ID_ATTR,0);
    if (treasureListId!=0)
    {
      TreasureList treasureList=lootsMgr.getTreasureLists().getItem(treasureListId);
      loot.setTreasureListOverride(treasureList);
    }
    // Trophy list override
    int trophyListOverrideId=DOMParsingTools.getIntAttribute(attrs,MobsXMLConstants.TROPHY_LIST_OVERRIDE_ID_ATTR,0);
    if (trophyListOverrideId!=0)
    {
      TrophyList trophyList=lootsMgr.getTrophyLists().getItem(trophyListOverrideId);
      loot.setTrophyListOverride(trophyList);
    }
    // Generates trophy
    boolean generatesTrophy=DOMParsingTools.getBooleanAttribute(attrs,MobsXMLConstants.GENERATES_TROPHY_ATTR,false);
    loot.setGeneratesTrophy(generatesTrophy);
    // Remote lootable
    boolean remoteLootable=DOMParsingTools.getBooleanAttribute(attrs,MobsXMLConstants.REMOTE_LOOTABLE_ATTR,true);
    loot.setRemoteLootable(remoteLootable);
    return loot;
  }
}
