package delta.games.lotro.lore.reputation.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;
import delta.games.lotro.lore.reputation.FactionsRegistry;
import delta.games.lotro.lore.reputation.ReputationDeed;

/**
 * Writes factions to XML files.
 * @author DAM
 */
public class FactionsXMLWriter
{
  private static final String CDATA="CDATA";

  /**
   * Write a file with factions.
   * @param toFile Output file.
   * @param factions Registry of factions to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeFactionsFile(File toFile, FactionsRegistry factions)
  {
    FactionsXMLWriter writer=new FactionsXMLWriter();
    boolean ok=writer.writeFactions(toFile,factions,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write factions to a XML file.
   * @param outFile Output file.
   * @param registry Registry of factions to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeFactions(File outFile, final FactionsRegistry registry, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeFactions(hd,registry);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeFactions(TransformerHandler hd, FactionsRegistry registry) throws Exception
  {
    AttributesImpl emptyAttrs=new AttributesImpl();
    hd.startElement("","",FactionsXMLConstants.FACTIONS_TAG,emptyAttrs);
    // Factions
    List<Faction> factions=registry.getAll();
    for(Faction faction : factions)
    {
      writeFaction(hd,faction);
    }
    // Deeds
    List<ReputationDeed> deeds=registry.getReputationDeeds();
    for(ReputationDeed deed : deeds)
    {
      writeReputationDeed(hd,deed);
    }
    hd.endElement("","",FactionsXMLConstants.FACTIONS_TAG);
  }

  private void writeFaction(TransformerHandler hd, Faction faction) throws Exception
  {
    AttributesImpl factionAttrs=new AttributesImpl();
    // Identifier
    int id=faction.getIdentifier();
    factionAttrs.addAttribute("","",FactionsXMLConstants.FACTION_ID_ATTR,CDATA,String.valueOf(id));
    // Faction key
    String key=faction.getLegacyKey();
    if (key!=null)
    {
      factionAttrs.addAttribute("","",FactionsXMLConstants.FACTION_KEY_ATTR,CDATA,key);
    }
    // Faction name
    String name=faction.getName();
    factionAttrs.addAttribute("","",FactionsXMLConstants.FACTION_NAME_ATTR,CDATA,name);
    // Category
    String category=faction.getCategory();
    if (category!=null)
    {
      factionAttrs.addAttribute("","",FactionsXMLConstants.FACTION_CATEGORY_ATTR,CDATA,category);
    }
    // Is guild?
    boolean isGuild=faction.isGuildFaction();
    if (isGuild)
    {
      factionAttrs.addAttribute("","",FactionsXMLConstants.FACTION_GUILD_ATTR,CDATA,String.valueOf(isGuild));
    }
    // Lowest tier
    int lowestTier=faction.getLowestTier();
    if (lowestTier!=0)
    {
      factionAttrs.addAttribute("","",FactionsXMLConstants.FACTION_LOWEST_TIER_ATTR,CDATA,String.valueOf(lowestTier));
    }
    // Initial tier
    int initialTier=faction.getInitialTier();
    if (initialTier!=0)
    {
      factionAttrs.addAttribute("","",FactionsXMLConstants.FACTION_INITIAL_TIER_ATTR,CDATA,String.valueOf(initialTier));
    }
    // Highest tier
    int highestTier=faction.getHighestTier();
    if (highestTier!=0)
    {
      factionAttrs.addAttribute("","",FactionsXMLConstants.FACTION_HIGHEST_TIER_ATTR,CDATA,String.valueOf(highestTier));
    }
    // Property names:
    // - current tier
    String currentTierPropertyName=faction.getCurrentTierPropertyName();
    if (currentTierPropertyName!=null)
    {
      factionAttrs.addAttribute("","",FactionsXMLConstants.FACTION_CURRENT_TIER_PROPERTY_ATTR,CDATA,currentTierPropertyName);
    }
    // - current reputation
    String currentReputationPropertyName=faction.getCurrentReputationPropertyName();
    if (currentReputationPropertyName!=null)
    {
      factionAttrs.addAttribute("","",FactionsXMLConstants.FACTION_CURRENT_REPUTATION_PROPERTY_ATTR,CDATA,currentReputationPropertyName);
    }
    // Description
    String description=faction.getDescription();
    if (description.length()>0)
    {
      factionAttrs.addAttribute("","",FactionsXMLConstants.FACTION_DESCRIPTION_ATTR,CDATA,description);
    }
    hd.startElement("","",FactionsXMLConstants.FACTION_TAG,factionAttrs);
    // Levels
    FactionLevel[] levels=faction.getLevels();
    for(FactionLevel level : levels)
    {
      AttributesImpl levelAttrs=new AttributesImpl();
      // Tier
      int tier=level.getTier();
      levelAttrs.addAttribute("","",FactionsXMLConstants.FACTION_LEVEL_TIER_ATTR,CDATA,String.valueOf(tier));
      // Level key
      String levelKey=level.getLegacyKey();
      if (levelKey!=null)
      {
        levelAttrs.addAttribute("","",FactionsXMLConstants.FACTION_LEVEL_KEY_ATTR,CDATA,levelKey);
      }
      // Level name
      String levelName=level.getName();
      levelAttrs.addAttribute("","",FactionsXMLConstants.FACTION_LEVEL_NAME_ATTR,CDATA,levelName);
      // LOTRO points
      int lotroPoints=level.getLotroPoints();
      levelAttrs.addAttribute("","",FactionsXMLConstants.FACTION_LEVEL_LOTRO_POINTS_ATTR,CDATA,String.valueOf(lotroPoints));
      // Required XP
      int requiredXp=level.getRequiredReputation();
      levelAttrs.addAttribute("","",FactionsXMLConstants.FACTION_LEVEL_REQUIRED_REPUTATION_ATTR,CDATA,String.valueOf(requiredXp));
      // Deed key
      String deedKey=level.getDeedKey();
      if (deedKey!=null)
      {
        levelAttrs.addAttribute("","",FactionsXMLConstants.FACTION_LEVEL_DEED_KEY_ATTR,CDATA,deedKey);
      }
      hd.startElement("","",FactionsXMLConstants.LEVEL_TAG,levelAttrs);
      hd.endElement("","",FactionsXMLConstants.LEVEL_TAG);
    }
    hd.endElement("","",FactionsXMLConstants.FACTION_TAG);
  }

  private void writeReputationDeed(TransformerHandler hd, ReputationDeed deed) throws Exception
  {
    AttributesImpl deedAttrs=new AttributesImpl();
    // Deed name
    String name=deed.getName();
    deedAttrs.addAttribute("","",FactionsXMLConstants.DEED_NAME_ATTR,CDATA,name);
    // LOTRO points
    int lotroPoints=deed.getLotroPoints();
    deedAttrs.addAttribute("","",FactionsXMLConstants.DEED_LOTRO_POINTS_ATTR,CDATA,String.valueOf(lotroPoints));
    hd.startElement("","",FactionsXMLConstants.DEED_TAG,deedAttrs);
    // Deed factions
    List<Faction> factions=deed.getFactions();
    if (factions.size()>0)
    {
      for(Faction faction : factions)
      {
        AttributesImpl deedFactionAttrs=new AttributesImpl();
        int factionId=faction.getIdentifier();
        deedFactionAttrs.addAttribute("","",FactionsXMLConstants.DEED_FACTION_ID_ATTR,CDATA,String.valueOf(factionId));
        hd.startElement("","",FactionsXMLConstants.DEED_FACTION_TAG,deedFactionAttrs);
        hd.endElement("","",FactionsXMLConstants.DEED_FACTION_TAG);
      }
    }
    hd.endElement("","",FactionsXMLConstants.DEED_TAG);
  }
}
