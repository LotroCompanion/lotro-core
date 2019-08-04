package delta.games.lotro.lore.items.legendary.io.xml;

import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.lore.items.legendary.LegaciesManager;
import delta.games.lotro.lore.items.legendary.LegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.PassivesManager;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacy;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegaciesManager;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegacyTier;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.TieredNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;
import delta.games.lotro.lore.items.legendary.relics.RelicsSet;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitle;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitlesManager;

/**
 * Parser for legendary instance attributes stored in XML.
 * @author DAM
 */
public class LegendaryInstanceAttrsXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(LegendaryInstanceAttrsXMLParser.class);

  /**
   * Read legendary instance attributes for an item.
   * @param legendaryAttrs Data to write to.
   * @param itemElement Root XML tag.
   */
  public static void read(LegendaryInstanceAttrs legendaryAttrs, Element itemElement)
  {
    readRelics(legendaryAttrs,itemElement);
    Element legendaryElement=DOMParsingTools.getChildTagByName(itemElement,LegendaryInstanceAttrsXMLConstants.LEGENDARY_TAG);
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

  private static void readLegendaryAttributes(LegendaryInstanceAttrs legendaryAttrs, Element legendaryElement)
  {
    NamedNodeMap attrs=legendaryElement.getAttributes();
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,LegendaryInstanceAttrsXMLConstants.LEGENDARY_NAME_ATTR,null);
    if (name!=null)
    {
      legendaryAttrs.setLegendaryName(name);
    }
    // Title
    int titleId=DOMParsingTools.getIntAttribute(attrs,LegendaryInstanceAttrsXMLConstants.LEGENDARY_TITLE_ID_ATTR,0);
    if (titleId!=0)
    {
      LegendaryTitlesManager titlesMgr=LegendaryTitlesManager.getInstance();
      LegendaryTitle title=titlesMgr.getLegendaryTitle(titleId);
      legendaryAttrs.setTitle(title);
    }
  }

  private static void readPassives(LegendaryInstanceAttrs legendaryAttrs, Element legendaryElement)
  {
    List<Element> passiveTags=DOMParsingTools.getChildTagsByName(legendaryElement,LegendaryInstanceAttrsXMLConstants.PASSIVE_TAG);
    if (passiveTags.size()>0)
    {
      PassivesManager passivesMgr=PassivesManager.getInstance();
      for(Element passiveTag : passiveTags)
      {
        NamedNodeMap attrs=passiveTag.getAttributes();
        int passiveId=DOMParsingTools.getIntAttribute(attrs,LegendaryInstanceAttrsXMLConstants.PASSIVE_ID_ATTR,0);
        if (passiveId!=0)
        {
          Effect passive=passivesMgr.getEffect(passiveId);
          legendaryAttrs.addPassive(passive);
        }
      }
    }
  }

  private static void readNonImbuedData(LegendaryInstanceAttrs legendaryAttrs, Element legendaryElement)
  {
    Element nonImbuedTag=DOMParsingTools.getChildTagByName(legendaryElement,LegendaryInstanceAttrsXMLConstants.NON_IMBUED_TAG);
    if (nonImbuedTag!=null)
    {
      NonImbuedLegendaryInstanceAttrs nonImbuedData=legendaryAttrs.getNonImbuedAttrs();
      NamedNodeMap attrs=nonImbuedTag.getAttributes();
      // Legendary level
      int legendaryLevel=DOMParsingTools.getIntAttribute(attrs,LegendaryInstanceAttrsXMLConstants.NON_IMBUED_LEGENDARY_LEVEL_ATTR,0);
      nonImbuedData.setLegendaryItemLevel(legendaryLevel);
      // Number of upgrades
      int upgrades=DOMParsingTools.getIntAttribute(attrs,LegendaryInstanceAttrsXMLConstants.NON_IMBUED_UPGRADES_ATTR,0);
      nonImbuedData.setNbUpgrades(upgrades);
      // Points spent
      int pointsSpent=DOMParsingTools.getIntAttribute(attrs,LegendaryInstanceAttrsXMLConstants.NON_IMBUED_POINTS_SPENT_ATTR,0);
      nonImbuedData.setPointsSpent(pointsSpent);
      // Points left
      int pointsLeft=DOMParsingTools.getIntAttribute(attrs,LegendaryInstanceAttrsXMLConstants.NON_IMBUED_POINTS_LEFT_ATTR,0);
      nonImbuedData.setPointsLeft(pointsLeft);

      // Default legacy
      Element defaultLegacyTag=DOMParsingTools.getChildTagByName(nonImbuedTag,LegendaryInstanceAttrsXMLConstants.DEFAULT_LEGACY_TAG);
      if (defaultLegacyTag!=null)
      {
        NonImbuedLegaciesManager nonImbuedMgr=NonImbuedLegaciesManager.getInstance();
        NamedNodeMap defaultLegacyAttrs=defaultLegacyTag.getAttributes();
        // Default legacy ID
        DefaultNonImbuedLegacyInstance instance=nonImbuedData.getDefaultLegacy();
        int id=DOMParsingTools.getIntAttribute(defaultLegacyAttrs,LegendaryInstanceAttrsXMLConstants.DEFAULT_LEGACY_ID_ATTR,0);
        if (id!=0)
        {
          DefaultNonImbuedLegacy defaultLegacy=nonImbuedMgr.getDefaultLegacy(id);
          if (defaultLegacy!=null)
          {
            instance.setLegacy(defaultLegacy);
          }
          else
          {
            LOGGER.warn("Default non-imbued legacy not found: "+id);
          }
        }
        else
        {
          instance.setLegacy(null);
        }
        // Default legacy rank
        int rank=DOMParsingTools.getIntAttribute(defaultLegacyAttrs,LegendaryInstanceAttrsXMLConstants.DEFAULT_LEGACY_RANK_ATTR,0);
        instance.setRank(rank);
      }
      // Tiered legacies
      List<Element> tieredLegacyTags=DOMParsingTools.getChildTagsByName(nonImbuedTag,LegendaryInstanceAttrsXMLConstants.TIERED_LEGACY_TAG);
      int currentIndex=0;
      for(Element tieredLegacyTag : tieredLegacyTags)
      {
        NonImbuedLegaciesManager nonImbuedMgr=NonImbuedLegaciesManager.getInstance();
        NamedNodeMap tieredLegacyAttrs=tieredLegacyTag.getAttributes();
        // Index
        int index=DOMParsingTools.getIntAttribute(tieredLegacyAttrs,LegendaryInstanceAttrsXMLConstants.TIERED_LEGACY_INDEX_ATTR,currentIndex);
        TieredNonImbuedLegacyInstance instance=nonImbuedData.getLegacy(index);
        // Legacy ID
        int id=DOMParsingTools.getIntAttribute(tieredLegacyAttrs,LegendaryInstanceAttrsXMLConstants.TIERED_LEGACY_ID_ATTR,0);
        if (id!=0)
        {
          NonImbuedLegacyTier legacyTier=nonImbuedMgr.getLegacyTier(id);
          if (legacyTier!=null)
          {
            instance.setLegacyTier(legacyTier);
          }
          else
          {
            LOGGER.warn("Tiered non-imbued legacy not found: "+id);
          }
        }
        else
        {
          instance.setLegacyTier(null);
        }
        // Legacy rank
        int rank=DOMParsingTools.getIntAttribute(tieredLegacyAttrs,LegendaryInstanceAttrsXMLConstants.TIERED_LEGACY_RANK_ATTR,0);
        instance.setRank(rank);
        currentIndex++;
      }
    }
  }

  private static void readImbuedData(LegendaryInstanceAttrs legendaryAttrs, Element legendaryElement)
  {
    Element imbuedTag=DOMParsingTools.getChildTagByName(legendaryElement,LegendaryInstanceAttrsXMLConstants.IMBUED_TAG);
    if (imbuedTag!=null)
    {
      LegaciesManager legaciesMgr=LegaciesManager.getInstance();
      ImbuedLegendaryInstanceAttrs imbuedData=new ImbuedLegendaryInstanceAttrs();
      legendaryAttrs.setImbuedAttrs(imbuedData);
      // Legacies
      List<Element> legacyTags=DOMParsingTools.getChildTagsByName(imbuedTag,LegendaryInstanceAttrsXMLConstants.IMBUED_LEGACY_TAG);
      for(Element legacyTag : legacyTags)
      {
        NamedNodeMap legacyAttrs=legacyTag.getAttributes();
        // Legacy ID
        int id=DOMParsingTools.getIntAttribute(legacyAttrs,LegendaryInstanceAttrsXMLConstants.IMBUED_LEGACY_ID_ATTR,0);
        if (id!=0)
        {
          ImbuedLegacy legacy=legaciesMgr.getLegacy(id);
          if (legacy!=null)
          {
            ImbuedLegacyInstance instance=new ImbuedLegacyInstance();
            instance.setLegacy(legacy);
            // XP
            int xp=DOMParsingTools.getIntAttribute(legacyAttrs,LegendaryInstanceAttrsXMLConstants.IMBUED_LEGACY_XP_ATTR,0);
            instance.setXp(xp);
            // Unlocked levels
            int unlockedLevels=DOMParsingTools.getIntAttribute(legacyAttrs,LegendaryInstanceAttrsXMLConstants.IMBUED_LEGACY_UNLOCKED_LEVEL_ATTR,0);
            instance.setUnlockedLevels(unlockedLevels);
            imbuedData.addLegacy(instance);
          }
          else
          {
            LOGGER.warn("Imbued legacy not found: "+id);
          }
        }
      }
    }
  }

  private static void readRelics(LegendaryInstanceAttrs legendaryAttrs, Element itemElement)
  {
    // Read relics
    RelicsManager relicsMgr=RelicsManager.getInstance();
    List<Element> relicTags=DOMParsingTools.getChildTagsByName(itemElement,LegendaryInstanceAttrsXMLConstants.RELIC_TAG,true);
    for(Element relicTag : relicTags)
    {
      NamedNodeMap attrs=relicTag.getAttributes();
      String typeStr=DOMParsingTools.getStringAttribute(attrs,LegendaryInstanceAttrsXMLConstants.RELIC_TYPE_ATTR,null);
      if (typeStr!=null)
      {
        RelicType type=RelicType.valueOf(typeStr);
        Relic relic=null;
        String name=DOMParsingTools.getStringAttribute(attrs,LegendaryInstanceAttrsXMLConstants.RELIC_NAME_ATTR,null);
        if (name!=null)
        {
          relic=relicsMgr.getByName(name);
        }
        RelicsSet relics=legendaryAttrs.getRelicsSet();
        if (type==RelicType.SETTING) relics.setSetting(relic);
        if (type==RelicType.RUNE) relics.setRune(relic);
        if (type==RelicType.GEM) relics.setGem(relic);
        if (type==RelicType.CRAFTED_RELIC) relics.setCraftedRelic(relic);
      }
    }
  }
}
