package delta.games.lotro.lore.agents.mobs.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.MobDivision;
import delta.games.lotro.common.treasure.LootsManager;
import delta.games.lotro.common.treasure.TreasureList;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.lore.agents.io.xml.AgentsXMLIO;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.lore.agents.mobs.MobLoot;
import delta.games.lotro.lore.agents.mobs.MobsManager;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Parser for the mobs stored in XML.
 * @author DAM
 */
public class MobsXMLParser
{
  private SingleLocaleLabelsManager _i18n;
  private LotroEnum<MobDivision> _mobDivision;

  /**
   * Constructor.
   */
  public MobsXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("mobs");
    _mobDivision=LotroEnumsRegistry.getInstance().get(MobDivision.class);
  }

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
    String name=_i18n.getLabel(String.valueOf(id));
    MobDescription ret=new MobDescription(id,name);
    // Division
    Integer mobDivisionCode=DOMParsingTools.getIntegerAttribute(attrs,MobsXMLConstants.DIVISION_ATTR,null);
    if (mobDivisionCode!=null)
    {
      MobDivision mobDivision=_mobDivision.getEntry(mobDivisionCode.intValue());
      ret.setDivision(mobDivision);
    }
    // Classification
    AgentsXMLIO.parseClassificationTag(mobTag,ret.getClassification());
    MobLoot loot=parseMobLoot(mobTag);
    ret.setMobLoot(loot);
    return ret;
  }


  private MobLoot parseMobLoot(Element mobLootTag)
  {
    NamedNodeMap attrs=mobLootTag.getAttributes();

    LootsManager lootsMgr=LootsManager.getInstance();
    // Barter trophy list
    TrophyList barterTrophyList=null;
    int barterTrophyListId=DOMParsingTools.getIntAttribute(attrs,MobsXMLConstants.BARTER_TROPHY_LIST_ID_ATTR,0);
    if (barterTrophyListId!=0)
    {
      barterTrophyList=(TrophyList)lootsMgr.getTables().getItem(barterTrophyListId);
    }
    // Reputation trophy list
    TrophyList reputationTrophyList=null;
    int reputationTrophyListId=DOMParsingTools.getIntAttribute(attrs,MobsXMLConstants.REPUTATION_TROPHY_LIST_ID_ATTR,0);
    if (reputationTrophyListId!=0)
    {
      reputationTrophyList=(TrophyList)lootsMgr.getTables().getItem(reputationTrophyListId);
    }
    // Treasure list override list
    TreasureList treasureList=null;
    int treasureListId=DOMParsingTools.getIntAttribute(attrs,MobsXMLConstants.TREASURE_LIST_OVERRIDE_ID_ATTR,0);
    if (treasureListId!=0)
    {
      treasureList=(TreasureList)lootsMgr.getTables().getItem(treasureListId);
    }
    // Trophy list override
    TrophyList trophyListOverride=null;
    int trophyListOverrideId=DOMParsingTools.getIntAttribute(attrs,MobsXMLConstants.TROPHY_LIST_OVERRIDE_ID_ATTR,0);
    if (trophyListOverrideId!=0)
    {
      trophyListOverride=(TrophyList)lootsMgr.getTables().getItem(trophyListOverrideId);
    }
    MobLoot loot=null;
    if ((barterTrophyList!=null) || (reputationTrophyList!=null) || (treasureList!=null) || (trophyListOverride!=null))
    {
      loot=new MobLoot();
      loot.setBarterTrophy(barterTrophyList);
      loot.setReputationTrophy(reputationTrophyList);
      loot.setTreasureListOverride(treasureList);
      loot.setTrophyListOverride(trophyListOverride);
      // Generates trophy
      boolean generatesTrophy=DOMParsingTools.getBooleanAttribute(attrs,MobsXMLConstants.GENERATES_TROPHY_ATTR,true);
      loot.setGeneratesTrophy(generatesTrophy);
      // Remote lootable
      boolean remoteLootable=DOMParsingTools.getBooleanAttribute(attrs,MobsXMLConstants.REMOTE_LOOTABLE_ATTR,true);
      loot.setRemoteLootable(remoteLootable);
    }
    return loot;
  }
}
