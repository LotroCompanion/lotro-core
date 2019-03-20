package delta.games.lotro.lore.items.legendary.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.Effect;
import delta.games.lotro.lore.items.legendary.LegaciesManager;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.PassivesManager;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacy;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegendaryAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegaciesManager;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegacyTier;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegendaryAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.TieredNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitle;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitlesManager;

/**
 * Parser for legendary attributes stored in XML.
 * @author DAM
 */
public class LegendaryAttrsXMLParser
{
  /**
   * Read legendary attributes for an item.
   * @param legendaryAttrs Data to write to.
   * @param itemElement Root XML tag.
   */
  public static void read(LegendaryAttrs legendaryAttrs, Element itemElement)
  {
    readRelics(legendaryAttrs,itemElement);
    Element legendaryElement=DOMParsingTools.getChildTagByName(itemElement,LegendaryAttrsXMLConstants.LEGENDARY_TAG);
    if (legendaryElement!=null)
    {
      // Read legendary attributes
      readLegendaryAttributes(legendaryAttrs,legendaryElement);
      // Read passives
      readPassives(legendaryAttrs,legendaryElement);
      // Read non-imbued data
      readNonImbuedData(legendaryAttrs,legendaryElement);
      // Read imbued data
      readImbuedData(legendaryAttrs,legendaryElement);
    }
  }

  private static void readLegendaryAttributes(LegendaryAttrs legendaryAttrs, Element legendaryElement)
  {
    NamedNodeMap attrs=legendaryElement.getAttributes();
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,LegendaryAttrsXMLConstants.LEGENDARY_NAME_ATTR,null);
    if (name!=null)
    {
      legendaryAttrs.setLegendaryName(name);
    }
    // Title
    int titleId=DOMParsingTools.getIntAttribute(attrs,LegendaryAttrsXMLConstants.LEGENDARY_TITLE_ID_ATTR,0);
    if (titleId!=0)
    {
      LegendaryTitlesManager titlesMgr=LegendaryTitlesManager.getInstance();
      LegendaryTitle title=titlesMgr.getLegendaryTitle(titleId);
      legendaryAttrs.setTitle(title);
    }
  }

  private static void readPassives(LegendaryAttrs legendaryAttrs, Element legendaryElement)
  {
    List<Element> passiveTags=DOMParsingTools.getChildTagsByName(legendaryElement,LegendaryAttrsXMLConstants.PASSIVE_TAG);
    if (passiveTags.size()>0)
    {
      PassivesManager passivesMgr=PassivesManager.getInstance();
      for(Element passiveTag : passiveTags)
      {
        NamedNodeMap attrs=passiveTag.getAttributes();
        int passiveId=DOMParsingTools.getIntAttribute(attrs,LegendaryAttrsXMLConstants.PASSIVE_ID_ATTR,0);
        if (passiveId!=0)
        {
          Effect passive=passivesMgr.getEffect(passiveId);
          legendaryAttrs.addPassive(passive);
        }
      }
    }
  }

  private static void readNonImbuedData(LegendaryAttrs legendaryAttrs, Element legendaryElement)
  {
    Element nonImbuedTag=DOMParsingTools.getChildTagByName(legendaryElement,LegendaryAttrsXMLConstants.NON_IMBUED_TAG);
    if (nonImbuedTag!=null)
    {
      NonImbuedLegendaryAttrs nonImbuedData=legendaryAttrs.getNonImbuedAttrs();
      NamedNodeMap attrs=nonImbuedTag.getAttributes();
      // Legendary level
      int legendaryLevel=DOMParsingTools.getIntAttribute(attrs,LegendaryAttrsXMLConstants.NON_IMBUED_LEGENDARY_LEVEL_ATTR,0);
      nonImbuedData.setLegendaryItemLevel(legendaryLevel);
      // Number of upgrades
      int upgrades=DOMParsingTools.getIntAttribute(attrs,LegendaryAttrsXMLConstants.NON_IMBUED_UPGRADES_ATTR,0);
      nonImbuedData.setNbUpgrades(upgrades);
      // Points spent
      int pointsSpent=DOMParsingTools.getIntAttribute(attrs,LegendaryAttrsXMLConstants.NON_IMBUED_POINTS_SPENT_ATTR,0);
      nonImbuedData.setPointsSpent(pointsSpent);
      // Points left
      int pointsLeft=DOMParsingTools.getIntAttribute(attrs,LegendaryAttrsXMLConstants.NON_IMBUED_POINTS_LEFT_ATTR,0);
      nonImbuedData.setPointsLeft(pointsLeft);

      // Default legacy
      Element defaultLegacyTag=DOMParsingTools.getChildTagByName(nonImbuedTag,LegendaryAttrsXMLConstants.DEFAULT_LEGACY_TAG);
      if (defaultLegacyTag!=null)
      {
        NonImbuedLegaciesManager nonImbuedMgr=NonImbuedLegaciesManager.getInstance();
        NamedNodeMap defaultLegacyAttrs=defaultLegacyTag.getAttributes();
        // Default legacy ID
        int id=DOMParsingTools.getIntAttribute(defaultLegacyAttrs,LegendaryAttrsXMLConstants.DEFAULT_LEGACY_ID_ATTR,0);
        if (id!=0)
        {
          DefaultNonImbuedLegacy defaultLegacy=nonImbuedMgr.getDefaultLegacy(id);
          if (defaultLegacy!=null)
          {
            DefaultNonImbuedLegacyInstance instance=new DefaultNonImbuedLegacyInstance();
            instance.setLegacy(defaultLegacy);
            // Default legacy rank
            int rank=DOMParsingTools.getIntAttribute(defaultLegacyAttrs,LegendaryAttrsXMLConstants.DEFAULT_LEGACY_RANK_ATTR,0);
            instance.setRank(rank);
            nonImbuedData.setDefaultLegacy(instance);
          }
        }
      }
      // Tiered legacies
      List<Element> tieredLegacyTags=DOMParsingTools.getChildTagsByName(nonImbuedTag,LegendaryAttrsXMLConstants.TIERED_LEGACY_TAG);
      for(Element tieredLegacyTag : tieredLegacyTags)
      {
        NonImbuedLegaciesManager nonImbuedMgr=NonImbuedLegaciesManager.getInstance();
        NamedNodeMap tieredLegacyAttrs=tieredLegacyTag.getAttributes();
        // Legacy ID
        int id=DOMParsingTools.getIntAttribute(tieredLegacyAttrs,LegendaryAttrsXMLConstants.TIERED_LEGACY_ID_ATTR,0);
        if (id!=0)
        {
          NonImbuedLegacyTier legacyTier=nonImbuedMgr.getLegacyTier(id);
          if (legacyTier!=null)
          {
            TieredNonImbuedLegacyInstance instance=new TieredNonImbuedLegacyInstance();
            instance.setLegacyTier(legacyTier);
            // Legacy rank
            int rank=DOMParsingTools.getIntAttribute(tieredLegacyAttrs,LegendaryAttrsXMLConstants.TIERED_LEGACY_RANK_ATTR,0);
            instance.setRank(rank);
            nonImbuedData.addLegacy(instance);
          }
        }
      }
    }
  }

  private static void readImbuedData(LegendaryAttrs legendaryAttrs, Element legendaryElement)
  {
    Element imbuedTag=DOMParsingTools.getChildTagByName(legendaryElement,LegendaryAttrsXMLConstants.IMBUED_TAG);
    if (imbuedTag!=null)
    {
      LegaciesManager legaciesMgr=LegaciesManager.getInstance();
      ImbuedLegendaryAttrs imbuedData=new ImbuedLegendaryAttrs();
      legendaryAttrs.setImbuedAttrs(imbuedData);
      // Legacies
      List<Element> legacyTags=DOMParsingTools.getChildTagsByName(imbuedTag,LegendaryAttrsXMLConstants.IMBUED_LEGACY_TAG);
      for(Element legacyTag : legacyTags)
      {
        NamedNodeMap legacyAttrs=legacyTag.getAttributes();
        // Legacy ID
        int id=DOMParsingTools.getIntAttribute(legacyAttrs,LegendaryAttrsXMLConstants.IMBUED_LEGACY_ID_ATTR,0);
        if (id!=0)
        {
          ImbuedLegacy legacy=legaciesMgr.getLegacy(id);
          if (legacy!=null)
          {
            ImbuedLegacyInstance instance=new ImbuedLegacyInstance();
            instance.setLegacy(legacy);
            // XP
            int xp=DOMParsingTools.getIntAttribute(legacyAttrs,LegendaryAttrsXMLConstants.IMBUED_LEGACY_XP_ATTR,0);
            instance.setXp(xp);
            // Unlocked levels
            int unlockedLevels=DOMParsingTools.getIntAttribute(legacyAttrs,LegendaryAttrsXMLConstants.IMBUED_LEGACY_UNLOCKED_LEVEL_ATTR,0);
            instance.setUnlockedLevels(unlockedLevels);
            imbuedData.addLegacy(instance);
          }
        }
      }
    }
  }

  private static void readRelics(LegendaryAttrs legendaryAttrs, Element itemElement)
  {
    // Read relics
    RelicsManager relicsMgr=RelicsManager.getInstance();
    List<Element> relicTags=DOMParsingTools.getChildTagsByName(itemElement,LegendaryAttrsXMLConstants.RELIC_TAG,true);
    for(Element relicTag : relicTags)
    {
      NamedNodeMap attrs=relicTag.getAttributes();
      String typeStr=DOMParsingTools.getStringAttribute(attrs,LegendaryAttrsXMLConstants.RELIC_TYPE_ATTR,null);
      if (typeStr!=null)
      {
        RelicType type=RelicType.valueOf(typeStr);
        Relic relic=null;
        String name=DOMParsingTools.getStringAttribute(attrs,LegendaryAttrsXMLConstants.RELIC_NAME_ATTR,null);
        if (name!=null)
        {
          relic=relicsMgr.getByName(name);
        }
        if (type==RelicType.SETTING) legendaryAttrs.setSetting(relic);
        if (type==RelicType.RUNE) legendaryAttrs.setRune(relic);
        if (type==RelicType.GEM) legendaryAttrs.setGem(relic);
        if (type==RelicType.CRAFTED_RELIC) legendaryAttrs.setCraftedRelic(relic);
      }
    }
  }
}
