package delta.games.lotro.lore.items.legendary.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.constraints.ClassAndSlot;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.io.xml.EffectXMLConstants;
import delta.games.lotro.common.effects.io.xml.EffectXMLParser;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLParser;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.legendary.AbstractLegacy;
import delta.games.lotro.lore.items.legendary.LegacyType;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.TieredNonImbuedLegacy;

/**
 * Parser for legacies descriptions stored in XML.
 * @author DAM
 */
public class LegacyXMLParser
{
  /**
   * Parse a legacies XML file.
   * @param source Source file.
   * @return List of parsed legacies.
   */
  public static List<AbstractLegacy> parseLegaciesFile(File source)
  {
    List<AbstractLegacy> legacies=new ArrayList<AbstractLegacy>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> legacyTags=DOMParsingTools.getChildTags(root);
      for(Element legacyTag : legacyTags)
      {
        AbstractLegacy legacy=parseLegacy(legacyTag);
        legacies.add(legacy);
      }
    }
    return legacies;
  }

  private static AbstractLegacy parseLegacy(Element root)
  {
    String tag=root.getTagName();
    if (LegacyXMLConstants.DEFAULT_NON_IMBUED_LEGACY_TAG.equals(tag))
    {
      return parseDefaultNonImbuedLegacy(root);
    }
    if (LegacyXMLConstants.TIERED_NON_IMBUED_LEGACY_TAG.equals(tag))
    {
      return parseTieredNonImbuedLegacy(root);
    }
    if (LegacyXMLConstants.LEGACY_TAG.equals(tag))
    {
      return parseImbuedLegacy(root);
    }
    return null;
  }

  /**
   * Build a default non-imbued legacy from an XML tag.
   * @param root Root XML tag.
   * @return A legacy.
   */
  private static DefaultNonImbuedLegacy parseDefaultNonImbuedLegacy(Element root)
  {
    DefaultNonImbuedLegacy legacy=new DefaultNonImbuedLegacy();
    // Shared data
    parseSharedData(root,legacy);
    // Effect
    Element effectTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants.EFFECT_TAG);
    if (effectTag!=null)
    {
      Effect effect=EffectXMLParser.parseEffect(effectTag);
      legacy.setEffect(effect);
    }
    return legacy;
  }

  /**
   * Build a tiered non-imbued legacy from an XML tag.
   * @param root Root XML tag.
   * @return A legacy.
   */
  private static TieredNonImbuedLegacy parseTieredNonImbuedLegacy(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Stat
    int id=DOMParsingTools.getIntAttribute(attrs,LegacyXMLConstants.TNIL_STAT_ATTR,0);
    StatDescription stat=StatsRegistry.getInstance().getById(id);
    TieredNonImbuedLegacy legacy=new TieredNonImbuedLegacy(stat);
    // Shared data
    parseSharedData(root,legacy);
    // Major
    boolean major=DOMParsingTools.getBooleanAttribute(attrs,LegacyXMLConstants.TNIL_MAJOR_ATTR,false);
    legacy.setMajor(major);
    // Tiers
    List<Element> tierTags=DOMParsingTools.getChildTagsByName(root,LegacyXMLConstants.LEGACY_TIER_TAG);
    for(Element tierTag : tierTags)
    {
      NamedNodeMap tierAttrs=tierTag.getAttributes();
      // Tier
      int tier=DOMParsingTools.getIntAttribute(tierAttrs,LegacyXMLConstants.LEGACY_TIER_TIER_ATTR,0);
      // Effect
      Effect effect=null;
      Element effectTag=DOMParsingTools.getChildTagByName(tierTag,EffectXMLConstants.EFFECT_TAG);
      if (effectTag!=null)
      {
        effect=EffectXMLParser.parseEffect(effectTag);
      }
      legacy.addTier(tier,effect);
    }
    return legacy;
  }

  /**
   * Build a legacy from an XML tag.
   * @param root Root XML tag.
   * @return A legacy.
   */
  private static ImbuedLegacy parseImbuedLegacy(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    ImbuedLegacy legacy=new ImbuedLegacy();
    // Shared data
    parseSharedData(root,legacy);
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,LegacyXMLConstants.LEGACY_IDENTIFIER_ATTR,0);
    legacy.setIdentifier(id);
    // Maximum initial level
    int maximumInitialLevel=DOMParsingTools.getIntAttribute(attrs,LegacyXMLConstants.LEGACY_MAX_INITIAL_LEVEL_ATTR,1);
    legacy.setMaxInitialLevel(maximumInitialLevel);
    // Maximum level
    int maxLevel=DOMParsingTools.getIntAttribute(attrs,LegacyXMLConstants.LEGACY_MAX_LEVEL_ATTR,1);
    legacy.setMaxLevel(maxLevel);

    // Stats
    StatsProvider statsProvider=StatsProviderXMLParser.parseStatsProvider(root);
    legacy.setStatsProvider(statsProvider);

    // Types
    Set<WeaponType> types=null;
    List<Element> typeTags=DOMParsingTools.getChildTagsByName(root,LegacyXMLConstants.ALLOWED_WEAPON_TYPE_TAG);
    for(Element typeTag : typeTags)
    {
      String weaponTypeKey=DOMParsingTools.getStringAttribute(typeTag.getAttributes(),LegacyXMLConstants.WEAPON_TYPE_ATTR,null);
      WeaponType weaponType=WeaponType.getWeaponTypeByKey(weaponTypeKey);
      if (weaponType!=null)
      {
        if (types==null)
        {
          types=new HashSet<WeaponType>();
        }
        types.add(weaponType);
      }
    }
    legacy.setAllowedWeaponTypes(types);
    return legacy;
  }

  private static void parseSharedData(Element root, AbstractLegacy legacy)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Type
    String typeStr=DOMParsingTools.getStringAttribute(attrs,LegacyXMLConstants.LEGACY_TYPE_ATTR,null);
    if (typeStr!=null)
    {
      LegacyType type=LegacyType.valueOf(typeStr);
      legacy.setType(type);
    }
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,LegacyXMLConstants.LEGACY_ICON_ID_ATTR,0);
    legacy.setIconId(iconId);
    // Filter
    List<Element> filterTags=DOMParsingTools.getChildTagsByName(root,LegacyXMLConstants.FILTER_TAG);
    for(Element filterTag : filterTags)
    {
      NamedNodeMap filterAttrs=filterTag.getAttributes();
      // Character class
      CharacterClass characterClass=null;
      String characterClassStr=DOMParsingTools.getStringAttribute(filterAttrs,LegacyXMLConstants.FILTER_CHARACTER_CLASS_ATTR,null);
      if (characterClassStr!=null)
      {
        characterClass=CharacterClass.getByKey(characterClassStr);
      }
      // Slot
      EquipmentLocation slot=null;
      String slotStr=DOMParsingTools.getStringAttribute(filterAttrs,LegacyXMLConstants.FILTER_SLOT_ATTR,null);
      if (slotStr!=null)
      {
        slot=EquipmentLocation.getByKey(slotStr);
      }
      // Filter
      ClassAndSlot spec=new ClassAndSlot(characterClass,slot);
      legacy.addAllowedClassAndSlot(spec);
    }
  }
}
