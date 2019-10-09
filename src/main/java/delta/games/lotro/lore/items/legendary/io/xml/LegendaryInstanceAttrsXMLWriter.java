package delta.games.lotro.lore.items.legendary.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.lore.items.legendary.LegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacy;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegacyTier;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.TieredNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitle;

/**
 * Writes legendary instance attributes to XML documents.
 * @author DAM
 */
public class LegendaryInstanceAttrsXMLWriter
{
  /**
   * Write legendary instance attrs to the given XML stream.
   * @param hd XML output stream.
   * @param legendaryData Legendary data to write.
   * @throws Exception If an error occurs.
   */
  public static void write(TransformerHandler hd, LegendaryInstanceAttrs legendaryData) throws Exception
  {
    AttributesImpl legendaryAttrs=new AttributesImpl();
    // Name
    String name=legendaryData.getLegendaryName();
    if (name.length()>0)
    {
      legendaryAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.LEGENDARY_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Title
    LegendaryTitle title=legendaryData.getTitle();
    if (title!=null)
    {
      int titleId=title.getIdentifier();
      legendaryAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.LEGENDARY_TITLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(titleId));
    }
    // Imbued
    boolean imbued=legendaryData.isImbued();
    legendaryAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.LEGENDARY_IMBUED_ATTR,XmlWriter.CDATA,String.valueOf(imbued));
    hd.startElement("","",LegendaryInstanceAttrsXMLConstants.LEGENDARY_TAG,legendaryAttrs);
    // Write passives
    writePassives(hd,legendaryData);
    // Write legacies
    writeLegacies(hd,legendaryData);
    // Write relics
    List<Relic> relics=legendaryData.getRelicsSet().getRelics();
    writeRelic(hd,relics.get(0),RelicType.SETTING);
    writeRelic(hd,relics.get(1),RelicType.GEM);
    writeRelic(hd,relics.get(2),RelicType.RUNE);
    writeRelic(hd,relics.get(3),RelicType.CRAFTED_RELIC);
    hd.endElement("","",LegendaryInstanceAttrsXMLConstants.LEGENDARY_TAG);
  }

  private static void writeRelic(TransformerHandler hd, Relic relic, RelicType type) throws Exception
  {
    if (relic!=null)
    {
      AttributesImpl relicAttrs=new AttributesImpl();
      // ID
      int id=relic.getIdentifier();
      relicAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.RELIC_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Type
      relicAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.RELIC_TYPE_ATTR,XmlWriter.CDATA,type.name());
      // Name
      String name=relic.getName();
      relicAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.RELIC_NAME_ATTR,XmlWriter.CDATA,name);
      hd.startElement("","",LegendaryInstanceAttrsXMLConstants.RELIC_TAG,relicAttrs);
      hd.endElement("","",LegendaryInstanceAttrsXMLConstants.RELIC_TAG);
    }
  }

  private static void writePassives(TransformerHandler hd, LegendaryInstanceAttrs legendaryAttrs) throws Exception
  {
    List<Effect> passives=legendaryAttrs.getPassives();
    for(Effect passive : passives)
    {
      AttributesImpl passiveAttrs=new AttributesImpl();
      // ID
      int id=passive.getIdentifier();
      passiveAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.PASSIVE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      hd.startElement("","",LegendaryInstanceAttrsXMLConstants.PASSIVE_TAG,passiveAttrs);
      hd.endElement("","",LegendaryInstanceAttrsXMLConstants.PASSIVE_TAG);
    }
  }

  private static void writeLegacies(TransformerHandler hd, LegendaryInstanceAttrs legendaryAttrs) throws Exception
  {
    writeNonImbuedData(hd,legendaryAttrs);
    writeImbuedData(hd,legendaryAttrs);
  }

  private static void writeNonImbuedData(TransformerHandler hd, LegendaryInstanceAttrs legendaryAttrs) throws Exception
  {
    // Non imbued data
    NonImbuedLegendaryInstanceAttrs nonImbuedData=legendaryAttrs.getNonImbuedAttrs();

    AttributesImpl nonImbuedAttrs=new AttributesImpl();
    // Legendary item level
    int legendaryItemLevel=nonImbuedData.getLegendaryItemLevel();
    nonImbuedAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.NON_IMBUED_LEGENDARY_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(legendaryItemLevel));
    // Number of upgrades
    int nbUpgrades=nonImbuedData.getNbUpgrades();
    nonImbuedAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.NON_IMBUED_UPGRADES_ATTR,XmlWriter.CDATA,String.valueOf(nbUpgrades));
    // Points spent
    int pointsSpent=nonImbuedData.getPointsSpent();
    nonImbuedAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.NON_IMBUED_POINTS_SPENT_ATTR,XmlWriter.CDATA,String.valueOf(pointsSpent));
    // Points left
    int pointsLeft=nonImbuedData.getPointsLeft();
    nonImbuedAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.NON_IMBUED_POINTS_LEFT_ATTR,XmlWriter.CDATA,String.valueOf(pointsLeft));
    hd.startElement("","",LegendaryInstanceAttrsXMLConstants.NON_IMBUED_TAG,nonImbuedAttrs);

    // Default legacy
    DefaultNonImbuedLegacyInstance defaultLegacyInstance=nonImbuedData.getDefaultLegacy();
    {
      AttributesImpl defaultLegacyAttrs=new AttributesImpl();
      DefaultNonImbuedLegacy defaultLegacy=defaultLegacyInstance.getLegacy();
      if (defaultLegacy!=null)
      {
        // ID
        int legacyId=defaultLegacy.getEffect().getIdentifier();
        defaultLegacyAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.DEFAULT_LEGACY_ID_ATTR,XmlWriter.CDATA,String.valueOf(legacyId));
      }
      // Rank
      int rank=defaultLegacyInstance.getRank();
      defaultLegacyAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.DEFAULT_LEGACY_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
      // UI rank
      Integer uiRank=defaultLegacyInstance.getUiRank();
      if (uiRank!=null)
      {
        defaultLegacyAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.DEFAULT_LEGACY_UI_RANK_ATTR,XmlWriter.CDATA,uiRank.toString());
      }
      hd.startElement("","",LegendaryInstanceAttrsXMLConstants.DEFAULT_LEGACY_TAG,defaultLegacyAttrs);
      hd.endElement("","",LegendaryInstanceAttrsXMLConstants.DEFAULT_LEGACY_TAG);
    }
    // Other legacies (tiered legacies)
    List<TieredNonImbuedLegacyInstance> legacyInstances=nonImbuedData.getLegacies();
    int index=0;
    for(TieredNonImbuedLegacyInstance legacyInstance : legacyInstances)
    {
      NonImbuedLegacyTier tier=legacyInstance.getLegacyTier();
      if (tier!=null)
      {
        AttributesImpl legacyAttrs=new AttributesImpl();
        // Index
        legacyAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.TIERED_LEGACY_INDEX_ATTR,XmlWriter.CDATA,String.valueOf(index));
        // ID
        int legacyId=tier.getEffect().getIdentifier();
        legacyAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.TIERED_LEGACY_ID_ATTR,XmlWriter.CDATA,String.valueOf(legacyId));
        // Rank
        int rank=legacyInstance.getRank();
        legacyAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.TIERED_LEGACY_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
        // UI rank
        Integer uiRank=legacyInstance.getUiRank();
        if (uiRank!=null)
        {
          legacyAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.TIERED_LEGACY_UI_RANK_ATTR,XmlWriter.CDATA,uiRank.toString());
        }
        hd.startElement("","",LegendaryInstanceAttrsXMLConstants.TIERED_LEGACY_TAG,legacyAttrs);
        hd.endElement("","",LegendaryInstanceAttrsXMLConstants.TIERED_LEGACY_TAG);
      }
      index++;
    }
    hd.endElement("","",LegendaryInstanceAttrsXMLConstants.NON_IMBUED_TAG);
  }

  private static void writeImbuedData(TransformerHandler hd, LegendaryInstanceAttrs legendaryAttrs) throws Exception
  {
    // Imbued data
    ImbuedLegendaryInstanceAttrs imbuedData=legendaryAttrs.getImbuedAttrs();
    if (imbuedData!=null)
    {
      AttributesImpl imbuedAttrs=new AttributesImpl();
      hd.startElement("","",LegendaryInstanceAttrsXMLConstants.IMBUED_TAG,imbuedAttrs);
      // Imbued legacies
      List<ImbuedLegacyInstance> imbuedLegacyInstances=imbuedData.getLegacies();
      int index=0;
      for(ImbuedLegacyInstance imbuedLegacyInstance : imbuedLegacyInstances)
      {
        ImbuedLegacy imbuedLegacy=imbuedLegacyInstance.getLegacy();
        if (imbuedLegacy!=null)
        {
          AttributesImpl imbuedLegacyInstanceAttrs=new AttributesImpl();
          // Index
          imbuedLegacyInstanceAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.IMBUED_LEGACY_INDEX_ATTR,XmlWriter.CDATA,String.valueOf(index));
          // ID
          int imbuedLegacyId=imbuedLegacy.getIdentifier();
          imbuedLegacyInstanceAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.IMBUED_LEGACY_ID_ATTR,XmlWriter.CDATA,String.valueOf(imbuedLegacyId));
          // XP
          int xp=imbuedLegacyInstance.getXp();
          imbuedLegacyInstanceAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.IMBUED_LEGACY_XP_ATTR,XmlWriter.CDATA,String.valueOf(xp));
          // Unlocked levels
          int unlockedLevels=imbuedLegacyInstance.getUnlockedLevels();
          imbuedLegacyInstanceAttrs.addAttribute("","",LegendaryInstanceAttrsXMLConstants.IMBUED_LEGACY_UNLOCKED_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(unlockedLevels));
          hd.startElement("","",LegendaryInstanceAttrsXMLConstants.IMBUED_LEGACY_TAG,imbuedLegacyInstanceAttrs);
          hd.endElement("","",LegendaryInstanceAttrsXMLConstants.IMBUED_LEGACY_TAG);
        }
        index++;
      }
      hd.endElement("","",LegendaryInstanceAttrsXMLConstants.IMBUED_TAG);
    }
  }
}
