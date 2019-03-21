package delta.games.lotro.lore.items.legendary.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacy;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegendaryAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegendaryAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.TieredNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitle;

/**
 * Writes legendary attributes to XML documents.
 * @author DAM
 */
public class LegendaryAttrsXMLWriter
{
  /**
   * Write legendary attrs to the given XML stream.
   * @param hd XML output stream.
   * @param legendaryData Legendary data to write.
   * @throws Exception If an error occurs.
   */
  public static void write(TransformerHandler hd, LegendaryAttrs legendaryData) throws Exception
  {
    AttributesImpl legendaryAttrs=new AttributesImpl();
    // Name
    String name=legendaryData.getLegendaryName();
    if (name.length()>0)
    {
      legendaryAttrs.addAttribute("","",LegendaryAttrsXMLConstants.LEGENDARY_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Title
    LegendaryTitle title=legendaryData.getTitle();
    if (title!=null)
    {
      int titleId=title.getIdentifier();
      legendaryAttrs.addAttribute("","",LegendaryAttrsXMLConstants.LEGENDARY_TITLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(titleId));
    }
    hd.startElement("","",LegendaryAttrsXMLConstants.LEGENDARY_TAG,legendaryAttrs);
    // Write passives
    writePassives(hd,legendaryData);
    // Write legacies
    writeLegacies(hd,legendaryData);
    // Write relics
    List<Relic> relics=legendaryData.getRelics();
    writeRelic(hd,relics.get(0),RelicType.SETTING);
    writeRelic(hd,relics.get(1),RelicType.GEM);
    writeRelic(hd,relics.get(2),RelicType.RUNE);
    writeRelic(hd,relics.get(3),RelicType.CRAFTED_RELIC);
    hd.endElement("","",LegendaryAttrsXMLConstants.LEGENDARY_TAG);
  }

  private static void writeRelic(TransformerHandler hd, Relic relic, RelicType type) throws Exception
  {
    if (relic!=null)
    {
      AttributesImpl relicAttrs=new AttributesImpl();
      // ID
      int id=relic.getIdentifier();
      relicAttrs.addAttribute("","",LegendaryAttrsXMLConstants.RELIC_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Type
      relicAttrs.addAttribute("","",LegendaryAttrsXMLConstants.RELIC_TYPE_ATTR,XmlWriter.CDATA,type.name());
      // Name
      String name=relic.getName();
      relicAttrs.addAttribute("","",LegendaryAttrsXMLConstants.RELIC_NAME_ATTR,XmlWriter.CDATA,name);
      hd.startElement("","",LegendaryAttrsXMLConstants.RELIC_TAG,relicAttrs);
      hd.endElement("","",LegendaryAttrsXMLConstants.RELIC_TAG);
    }
  }

  private static void writePassives(TransformerHandler hd, LegendaryAttrs legendaryAttrs) throws Exception
  {
    List<Effect> passives=legendaryAttrs.getPassives();
    for(Effect passive : passives)
    {
      AttributesImpl passiveAttrs=new AttributesImpl();
      // ID
      int id=passive.getIdentifier();
      passiveAttrs.addAttribute("","",LegendaryAttrsXMLConstants.PASSIVE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      hd.startElement("","",LegendaryAttrsXMLConstants.PASSIVE_TAG,passiveAttrs);
      hd.endElement("","",LegendaryAttrsXMLConstants.PASSIVE_TAG);
    }
  }

  private static void writeLegacies(TransformerHandler hd, LegendaryAttrs legendaryAttrs) throws Exception
  {
    writeNonImbuedData(hd,legendaryAttrs);
    writeImbuedData(hd,legendaryAttrs);
  }

  private static void writeNonImbuedData(TransformerHandler hd, LegendaryAttrs legendaryAttrs) throws Exception
  {
    // Non imbued data
    NonImbuedLegendaryAttrs nonImbuedData=legendaryAttrs.getNonImbuedAttrs();

    AttributesImpl nonImbuedAttrs=new AttributesImpl();
    // Legendary item level
    int legendaryItemLevel=nonImbuedData.getLegendaryItemLevel();
    nonImbuedAttrs.addAttribute("","",LegendaryAttrsXMLConstants.NON_IMBUED_LEGENDARY_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(legendaryItemLevel));
    // Number of upgrades
    int nbUpgrades=nonImbuedData.getNbUpgrades();
    nonImbuedAttrs.addAttribute("","",LegendaryAttrsXMLConstants.NON_IMBUED_UPGRADES_ATTR,XmlWriter.CDATA,String.valueOf(nbUpgrades));
    // Points spent
    int pointsSpent=nonImbuedData.getPointsSpent();
    nonImbuedAttrs.addAttribute("","",LegendaryAttrsXMLConstants.NON_IMBUED_POINTS_SPENT_ATTR,XmlWriter.CDATA,String.valueOf(pointsSpent));
    // Points left
    int pointsLeft=nonImbuedData.getPointsLeft();
    nonImbuedAttrs.addAttribute("","",LegendaryAttrsXMLConstants.NON_IMBUED_POINTS_LEFT_ATTR,XmlWriter.CDATA,String.valueOf(pointsLeft));
    hd.startElement("","",LegendaryAttrsXMLConstants.NON_IMBUED_TAG,nonImbuedAttrs);

    // Default legacy
    DefaultNonImbuedLegacyInstance defaultLegacyInstance=nonImbuedData.getDefaultLegacy();
    if (defaultLegacyInstance!=null)
    {
      AttributesImpl defaultLegacyAttrs=new AttributesImpl();
      // ID
      int legacyId=defaultLegacyInstance.getLegacy().getEffect().getIdentifier();
      defaultLegacyAttrs.addAttribute("","",LegendaryAttrsXMLConstants.DEFAULT_LEGACY_ID_ATTR,XmlWriter.CDATA,String.valueOf(legacyId));
      // Rank
      int rank=defaultLegacyInstance.getRank();
      defaultLegacyAttrs.addAttribute("","",LegendaryAttrsXMLConstants.DEFAULT_LEGACY_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
      hd.startElement("","",LegendaryAttrsXMLConstants.DEFAULT_LEGACY_TAG,defaultLegacyAttrs);
      hd.endElement("","",LegendaryAttrsXMLConstants.DEFAULT_LEGACY_TAG);
    }
    // Other legacies (tiered legacies)
    List<TieredNonImbuedLegacyInstance> legacyInstances=nonImbuedData.getLegacies();
    for(TieredNonImbuedLegacyInstance legacyInstance : legacyInstances)
    {
      AttributesImpl legacyAttrs=new AttributesImpl();
      // ID
      int legacyId=legacyInstance.getLegacyTier().getEffect().getIdentifier();
      legacyAttrs.addAttribute("","",LegendaryAttrsXMLConstants.TIERED_LEGACY_ID_ATTR,XmlWriter.CDATA,String.valueOf(legacyId));
      // Rank
      int rank=legacyInstance.getRank();
      legacyAttrs.addAttribute("","",LegendaryAttrsXMLConstants.TIERED_LEGACY_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
      hd.startElement("","",LegendaryAttrsXMLConstants.TIERED_LEGACY_TAG,legacyAttrs);
      hd.endElement("","",LegendaryAttrsXMLConstants.TIERED_LEGACY_TAG);
    }
    hd.endElement("","",LegendaryAttrsXMLConstants.NON_IMBUED_TAG);
  }

  private static void writeImbuedData(TransformerHandler hd, LegendaryAttrs legendaryAttrs) throws Exception
  {
    // Imbued data
    ImbuedLegendaryAttrs imbuedData=legendaryAttrs.getImbuedAttrs();
    if (imbuedData!=null)
    {
      AttributesImpl imbuedAttrs=new AttributesImpl();
      hd.startElement("","",LegendaryAttrsXMLConstants.IMBUED_TAG,imbuedAttrs);
      // Imbued legacies
      List<ImbuedLegacyInstance> imbuedLegacyInstances=imbuedData.getLegacies();
      for(ImbuedLegacyInstance imbuedLegacyInstance : imbuedLegacyInstances)
      {
        ImbuedLegacy imbuedLegacy=imbuedLegacyInstance.getLegacy();
        if (imbuedLegacy!=null)
        {
          AttributesImpl imbuedLegacyInstanceAttrs=new AttributesImpl();
          // ID
          int imbuedLegacyId=imbuedLegacy.getIdentifier();
          imbuedLegacyInstanceAttrs.addAttribute("","",LegendaryAttrsXMLConstants.IMBUED_LEGACY_ID_ATTR,XmlWriter.CDATA,String.valueOf(imbuedLegacyId));
          // XP
          int xp=imbuedLegacyInstance.getXp();
          imbuedLegacyInstanceAttrs.addAttribute("","",LegendaryAttrsXMLConstants.IMBUED_LEGACY_XP_ATTR,XmlWriter.CDATA,String.valueOf(xp));
          // Unlocked levels
          int unlockedLevels=imbuedLegacyInstance.getUnlockedLevels();
          imbuedLegacyInstanceAttrs.addAttribute("","",LegendaryAttrsXMLConstants.IMBUED_LEGACY_UNLOCKED_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(unlockedLevels));
          hd.startElement("","",LegendaryAttrsXMLConstants.IMBUED_LEGACY_TAG,imbuedLegacyInstanceAttrs);
          hd.endElement("","",LegendaryAttrsXMLConstants.IMBUED_LEGACY_TAG);
        }
      }
      hd.endElement("","",LegendaryAttrsXMLConstants.IMBUED_TAG);
    }
  }
}
