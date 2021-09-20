package delta.games.lotro.lore.agents.mobs.loot.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.enums.MobType;
import delta.games.lotro.common.enums.Species;
import delta.games.lotro.common.enums.SubSpecies;
import delta.games.lotro.common.treasure.TreasureList;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.lore.agents.mobs.loot.GenericMobLootEntry;
import delta.games.lotro.lore.agents.mobs.loot.GenericMobLootSpec;
import delta.games.lotro.lore.agents.mobs.loot.SpeciesLoot;
import delta.games.lotro.lore.agents.mobs.loot.SpeciesLootsManager;
import delta.games.lotro.lore.agents.mobs.loot.SubSpeciesLoot;

/**
 * Writes generic mob loot data to XML files.
 * @author DAM
 */
public class GenericMobLootXMLWriter
{
  /**
   * Write a file with generic mob loots.
   * @param toFile Output file.
   * @param data Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeMobLootFile(File toFile, SpeciesLootsManager data)
  {
    GenericMobLootXMLWriter writer=new GenericMobLootXMLWriter();
    boolean ok=writer.writeSpeciesLoots(toFile,data,EncodingNames.UTF_8);
    return ok;
  }

  private boolean writeSpeciesLoots(File outFile, final SpeciesLootsManager data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeSpeciesLoots(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeSpeciesLoots(TransformerHandler hd, SpeciesLootsManager data) throws Exception
  {
    hd.startElement("","",GenericMobLootXMLConstants.GENERIC_MOB_LOOT_TAG,new AttributesImpl());
    for(SpeciesLoot loot : data.getLootSpecs())
    {
      writeSpeciesLoot(hd,loot);
    }
    hd.endElement("","",GenericMobLootXMLConstants.GENERIC_MOB_LOOT_TAG);
  }

  private void writeSpeciesLoot(TransformerHandler hd, SpeciesLoot loot) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Species code
    Species species=loot.getSpecies();
    int code=species.getCode();
    attrs.addAttribute("","",GenericMobLootXMLConstants.SPECIES_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Name
    String name=species.getLabel();
    attrs.addAttribute("","",GenericMobLootXMLConstants.SPECIES_NAME_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",GenericMobLootXMLConstants.SPECIES_TAG,attrs);
    for(SubSpeciesLoot subSpeciesLoot : loot.getLootSpecs())
    {
      writeSubSpeciesLoot(hd,subSpeciesLoot);
    }
    hd.endElement("","",GenericMobLootXMLConstants.SPECIES_TAG);
  }

  private void writeSubSpeciesLoot(TransformerHandler hd, SubSpeciesLoot loot) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Subspecies code
    SubSpecies subSpecies=loot.getSubSpecies();
    if (subSpecies!=null)
    {
      int code=subSpecies.getCode();
      attrs.addAttribute("","",GenericMobLootXMLConstants.SUBSPECIES_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
      // Name
      String name=subSpecies.getLabel();
      attrs.addAttribute("","",GenericMobLootXMLConstants.SUBSPECIES_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",GenericMobLootXMLConstants.SUBSPECIES_TAG,attrs);
    for(GenericMobLootSpec mobTypeLoot : loot.getLootSpecs())
    {
      writeMobTypeLoot(hd,mobTypeLoot);
    }
    hd.endElement("","",GenericMobLootXMLConstants.SUBSPECIES_TAG);
  }

  private void writeMobTypeLoot(TransformerHandler hd, GenericMobLootSpec loot) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    MobType mobType=loot.getMobType();
    if (mobType!=null)
    {
      // Mob type code
      int code=mobType.getCode();
      attrs.addAttribute("","",GenericMobLootXMLConstants.MOB_TYPE_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
      // Name
      String name=mobType.getLabel();
      attrs.addAttribute("","",GenericMobLootXMLConstants.MOB_TYPE_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",GenericMobLootXMLConstants.MOB_TYPE_TAG,attrs);
    for(Integer level : loot.getLevels())
    {
      GenericMobLootEntry entry=loot.getEntryForLevel(level.intValue());
      writeEntry(hd,entry);
    }
    hd.endElement("","",GenericMobLootXMLConstants.MOB_TYPE_TAG);
  }

  private void writeEntry(TransformerHandler hd, GenericMobLootEntry loot) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Level
    int level=loot.getLevel();
    attrs.addAttribute("","",GenericMobLootXMLConstants.LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(level));
    // Treasure list
    TreasureList treasureList=loot.getTreasureList();
    if (treasureList!=null)
    {
      attrs.addAttribute("","",GenericMobLootXMLConstants.TREASURE_LIST_ATTR,XmlWriter.CDATA,String.valueOf(treasureList.getIdentifier()));
    }
    TrophyList trophyList=loot.getTrophyList();
    if (trophyList!=null)
    {
      attrs.addAttribute("","",GenericMobLootXMLConstants.TROPHY_LIST_ATTR,XmlWriter.CDATA,String.valueOf(trophyList.getIdentifier()));
    }
    hd.startElement("","",GenericMobLootXMLConstants.LOOT_ENTRY_TAG,attrs);
    hd.endElement("","",GenericMobLootXMLConstants.LOOT_ENTRY_TAG);
  }
}
