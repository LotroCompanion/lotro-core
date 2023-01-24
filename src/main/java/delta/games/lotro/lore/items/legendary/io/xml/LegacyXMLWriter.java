package delta.games.lotro.lore.items.legendary.io.xml;

import java.io.File;
import java.util.List;
import java.util.Set;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.common.constraints.ClassAndSlot;
import delta.games.lotro.common.constraints.ClassAndSlotFilter;
import delta.games.lotro.common.effects.io.xml.EffectXMLWriter;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.legendary.AbstractLegacy;
import delta.games.lotro.lore.items.legendary.LegacyType;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.AbstractNonImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegacyTier;
import delta.games.lotro.lore.items.legendary.non_imbued.TieredNonImbuedLegacy;

/**
 * Writes legacies to XML files.
 * @author DAM
 */
public class LegacyXMLWriter
{
  /**
   * Write some legacies to a XML file.
   * @param toFile File to write to.
   * @param legacies Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<? extends AbstractLegacy> legacies)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",LegacyXMLConstants.LEGACIES_TAG,new AttributesImpl());
        for(AbstractLegacy legacy : legacies)
        {
          if (legacy instanceof TieredNonImbuedLegacy)
          {
            writeTieredNonImbuedLegacy(hd,(TieredNonImbuedLegacy)legacy);
          }
          else if (legacy instanceof DefaultNonImbuedLegacy)
          {
            writeDefaultNonImbuedLegacy(hd,(DefaultNonImbuedLegacy)legacy);
          }
          else if (legacy instanceof ImbuedLegacy)
          {
            writeImbuedLegacy(hd,(ImbuedLegacy)legacy);
          }
        }
        hd.endElement("","",LegacyXMLConstants.LEGACIES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeDefaultNonImbuedLegacy(TransformerHandler hd, DefaultNonImbuedLegacy legacy) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedAttributes(attrs,legacy);
    // Shared non-imbued attributes
    writeSharedNonImbuedAttributes(attrs,legacy);

    hd.startElement("","",LegacyXMLConstants.DEFAULT_NON_IMBUED_LEGACY_TAG,attrs);
    // Filter
    writeFilter(hd,legacy);
    // Effect
    EffectXMLWriter.writeEffect(hd,legacy.getEffect());
    hd.endElement("","",LegacyXMLConstants.DEFAULT_NON_IMBUED_LEGACY_TAG);
  }

  private static void writeTieredNonImbuedLegacy(TransformerHandler hd, TieredNonImbuedLegacy legacy) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedAttributes(attrs,legacy);
    // Shared non-imbued attributes
    writeSharedNonImbuedAttributes(attrs,legacy);
    // Stat
    StatDescription stat=legacy.getStat();
    if (stat!=null)
    {
      int id=stat.getIdentifier();
      attrs.addAttribute("","",LegacyXMLConstants.TNIL_STAT_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Major
    boolean major=legacy.isMajor();
    if (major)
    {
      attrs.addAttribute("","",LegacyXMLConstants.TNIL_MAJOR_ATTR,XmlWriter.CDATA,String.valueOf(major));
    }
    hd.startElement("","",LegacyXMLConstants.TIERED_NON_IMBUED_LEGACY_TAG,attrs);

    // Filter
    writeFilter(hd,legacy);
    // Tiers
    for(NonImbuedLegacyTier legacyTier : legacy.getTiers())
    {
      AttributesImpl tierAttrs=new AttributesImpl();
      // Tier
      int tier=legacyTier.getTier();
      tierAttrs.addAttribute("","",LegacyXMLConstants.LEGACY_TIER_TIER_ATTR,XmlWriter.CDATA,String.valueOf(tier));
      // Start rank
      Integer startRank=legacyTier.getStartRank();
      if (startRank!=null)
      {
        tierAttrs.addAttribute("","",LegacyXMLConstants.LEGACY_TIER_START_RANK_ATTR,XmlWriter.CDATA,startRank.toString());
      }
      hd.startElement("","",LegacyXMLConstants.LEGACY_TIER_TAG,tierAttrs);
      // Effect
      EffectXMLWriter.writeEffect(hd,legacyTier.getEffect());
      hd.endElement("","",LegacyXMLConstants.LEGACY_TIER_TAG);
    }
    hd.endElement("","",LegacyXMLConstants.TIERED_NON_IMBUED_LEGACY_TAG);
  }

  private static void writeImbuedLegacy(TransformerHandler hd, ImbuedLegacy legacy) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=legacy.getIdentifier();
    attrs.addAttribute("","",LegacyXMLConstants.LEGACY_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Shared attributes
    writeSharedAttributes(attrs,legacy);
    // Maximum initial level
    int maxInitialLevel=legacy.getMaxInitialLevel();
    attrs.addAttribute("","",LegacyXMLConstants.LEGACY_MAX_INITIAL_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(maxInitialLevel));
    // Maximum level
    int maxLevel=legacy.getMaxLevel();
    attrs.addAttribute("","",LegacyXMLConstants.LEGACY_MAX_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(maxLevel));
    hd.startElement("","",LegacyXMLConstants.LEGACY_TAG,attrs);

    // Allowed types
    Set<WeaponType> types=legacy.getAllowedWeaponTypes();
    if (types!=null)
    {
      for(WeaponType type : WeaponType.getAll())
      {
        if (types.contains(type))
        {
          writeWeaponType(hd,type);
        }
      }
    }
    // Filter
    writeFilter(hd,legacy);
    // Stats
    StatsProvider statsProvider=legacy.getStatsProvider();
    if (statsProvider!=null)
    {
      StatsProviderXMLWriter.writeXml(hd,null,statsProvider,null);
    }
    hd.endElement("","",LegacyXMLConstants.LEGACY_TAG);
  }

  private static void writeWeaponType(TransformerHandler hd, WeaponType type) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Type
    attrs.addAttribute("","",LegacyXMLConstants.WEAPON_TYPE_ATTR,XmlWriter.CDATA,type.getKey());
    hd.startElement("","",LegacyXMLConstants.ALLOWED_WEAPON_TYPE_TAG,attrs);
    hd.endElement("","",LegacyXMLConstants.ALLOWED_WEAPON_TYPE_TAG);
  }

  private static void writeSharedAttributes(AttributesImpl attrs, AbstractLegacy legacy)
  {
    // Type
    LegacyType type=legacy.getType();
    if (type!=null)
    {
      attrs.addAttribute("","",LegacyXMLConstants.LEGACY_TYPE_ATTR,XmlWriter.CDATA,type.name());
    }
    // Icon ID
    int iconId=legacy.getIconId();
    if (iconId!=0)
    {
      attrs.addAttribute("","",LegacyXMLConstants.LEGACY_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    }
  }

  private static void writeSharedNonImbuedAttributes(AttributesImpl attrs, AbstractNonImbuedLegacy legacy)
  {
    // Imbued legacy ID
    int imbuedLegacyId=legacy.getImbuedLegacyId();
    if (imbuedLegacyId!=0)
    {
      attrs.addAttribute("","",LegacyXMLConstants.IMBUED_LEGACY_ID_ATTR,XmlWriter.CDATA,String.valueOf(imbuedLegacyId));
    }
  }

  private static void writeFilter(TransformerHandler hd, AbstractLegacy legacy) throws SAXException
  {
    CompoundFilter<ClassAndSlot> filters=legacy.getClassAndSlotFilter();
    if (filters!=null)
    {
      for(Filter<ClassAndSlot> filter : filters.getFilters())
      {
        ClassAndSlotFilter classAndSlotFilter=(ClassAndSlotFilter)filter;
        AttributesImpl filterAttrs=new AttributesImpl();

        // Character class
        ClassDescription characterClass=classAndSlotFilter.getCharacterClass();
        if (characterClass!=null)
        {
          filterAttrs.addAttribute("","",LegacyXMLConstants.FILTER_CHARACTER_CLASS_ATTR,XmlWriter.CDATA,characterClass.getKey());
        }
        // Slot
        EquipmentLocation slot=classAndSlotFilter.getSlot();
        if (slot!=null)
        {
          filterAttrs.addAttribute("","",LegacyXMLConstants.FILTER_SLOT_ATTR,XmlWriter.CDATA,slot.getKey());
        }
        hd.startElement("","",LegacyXMLConstants.FILTER_TAG,filterAttrs);
        hd.endElement("","",LegacyXMLConstants.FILTER_TAG);
      }
    }
  }
}
