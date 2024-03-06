package delta.games.lotro.character.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.CharacterDataSummary;
import delta.games.lotro.character.CharacterSummary;
import delta.games.lotro.character.gear.CharacterGear;
import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.character.gear.GearSlotContents;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLWriter;
import delta.games.lotro.character.stats.buffs.BuffsManager;
import delta.games.lotro.character.stats.buffs.io.xml.BuffsXMLWriter;
import delta.games.lotro.character.stats.tomes.StatTomesManager;
import delta.games.lotro.character.stats.tomes.TomesSet;
import delta.games.lotro.character.stats.virtues.VirtuesSet;
import delta.games.lotro.character.status.traitTree.TraitTreeStatus;
import delta.games.lotro.character.status.traitTree.io.xml.TraitTreeStatusXMLWriter;
import delta.games.lotro.character.status.traits.TraitsStatus;
import delta.games.lotro.character.status.traits.shared.SlottedTraitsStatus;
import delta.games.lotro.character.status.traits.shared.io.xml.SlottedTraitsStatusXMLConstants;
import delta.games.lotro.character.status.traits.shared.io.xml.SlottedTraitsStatusXMLWriter;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.io.xml.ItemInstanceXMLWriter;
import delta.games.lotro.utils.PersistenceVersions;

/**
 * Writes LOTRO characters to XML files.
 * @author DAM
 */
public class CharacterXMLWriter
{
    /**
   * Write a character to a XML file.
   * @param outFile Output file.
   * @param character Character to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final CharacterData character, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,character);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void write(TransformerHandler hd, CharacterData character) throws Exception
  {
    AttributesImpl characterAttrs=new AttributesImpl();
    // Version
    String versionStr=String.valueOf(PersistenceVersions.CHARACTER_DATA);
    characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_VERSION_ATTR,XmlWriter.CDATA,versionStr);
    // Summary
    CharacterDataSummary dataSummary=character.getSummary();
    CharacterSummaryXMLWriter.writeDataSummary(characterAttrs,dataSummary);
    // Short description
    String shortDescription=character.getShortDescription();
    if ((shortDescription!=null) && (shortDescription.length()>0))
    {
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_SHORT_DESCRIPTION_ATTR,XmlWriter.CDATA,shortDescription);
    }
    // Description
    String description=character.getDescription();
    if ((description!=null) && (description.length()>0))
    {
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    // Date
    Long date=character.getDate();
    if (date!=null)
    {
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_DATE_ATTR,XmlWriter.CDATA,date.toString());
    }
    hd.startElement("","",CharacterXMLConstants.CHARACTER_TAG,characterAttrs);

    // Summary
    CharacterSummary summary=dataSummary.getSummary();
    if (summary!=null)
    {
      AttributesImpl summaryAttrs=new AttributesImpl();
      CharacterSummaryXMLWriter.writeCharacterSummary(summaryAttrs, summary);
      hd.startElement("","",CharacterXMLConstants.CHARACTER_SUMMARY_TAG,summaryAttrs);
      hd.endElement("","",CharacterXMLConstants.CHARACTER_SUMMARY_TAG);
    }

    // Stats
    BasicStatsSet stats=character.getStats();
    BasicStatsSetXMLWriter.write(hd,CharacterXMLConstants.STATS_TAG,stats,true);
    // Equipment
    CharacterGear equipment=character.getEquipment();
    writeEquipment(hd,equipment);
    // Virtues
    VirtuesSet virtues=character.getVirtues();
    writeVirtues(hd,virtues);
    // Tomes
    TomesSet tomes=character.getTomes();
    writeTomes(hd,tomes);
    // Traits
    // - trait tree
    TraitsStatus traitsStatus=character.getTraits();
    TraitTreeStatus traitTree=traitsStatus.getTraitTreeStatus();
    TraitTreeStatusXMLWriter.write(hd,traitTree);
    // - racial traits
    SlottedTraitsStatus racialTraitsStatus=traitsStatus.getRacialTraitsStatus();
    SlottedTraitsStatusXMLWriter.writeSlottedTraitsStatus(hd,racialTraitsStatus,SlottedTraitsStatusXMLConstants.RACIAL_TRAITS_STATUS_TAG);
    // Buffs
    BuffsManager buffs=character.getBuffs();
    BuffsXMLWriter.write(hd,buffs);
    // Additional stats
    BasicStatsSet additionalStats=character.getAdditionalStats();
    BasicStatsSetXMLWriter.write(hd,CharacterXMLConstants.ADDITIONAL_STATS_TAG,additionalStats);
    hd.endElement("","",CharacterXMLConstants.CHARACTER_TAG);
  }

  private void writeEquipment(TransformerHandler hd, CharacterGear equipment) throws Exception
  {
    AttributesImpl fieldAtts=new AttributesImpl();
    hd.startElement("","",CharacterXMLConstants.EQUIPMENT_TAG,fieldAtts);
    GearSlot[] slots=GearSlot.getAll();
    for(GearSlot slot : slots)
    {
      GearSlotContents slotContents=equipment.getSlotContents(slot,false);
      if (slotContents!=null)
      {
        writeEquipmentSlot(hd,slotContents);
      }
    }
    hd.endElement("","",CharacterXMLConstants.EQUIPMENT_TAG);
  }

  private void writeEquipmentSlot(TransformerHandler hd, GearSlotContents slotContents) throws Exception
  {
    GearSlot slot=slotContents.getSlot();
    if (slot!=null)
    {
      AttributesImpl slotAtts=new AttributesImpl();
      slotAtts.addAttribute("","",CharacterXMLConstants.SLOT_NAME_ATTR,XmlWriter.CDATA,slot.getKey());
      hd.startElement("","",CharacterXMLConstants.SLOT_TAG,slotAtts);
      ItemInstance<? extends Item> item=slotContents.getItem();
      if (item!=null)
      {
        ItemInstanceXMLWriter writer=new ItemInstanceXMLWriter();
        writer.writeItemInstance(hd,item);
      }
      hd.endElement("","",CharacterXMLConstants.SLOT_TAG);
    }
  }

  private void writeVirtues(TransformerHandler hd, VirtuesSet virtuesSet) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",CharacterXMLConstants.VIRTUES_TAG,attrs);
    List<VirtueDescription> virtues=VirtuesManager.getInstance().getAll();
    for(VirtueDescription virtue : virtues)
    {
      AttributesImpl virtueAttrs=new AttributesImpl();
      virtueAttrs.addAttribute("","",CharacterXMLConstants.VIRTUE_ID,XmlWriter.CDATA,virtue.getPersistenceKey());
      int rank=virtuesSet.getVirtueRank(virtue);
      virtueAttrs.addAttribute("","",CharacterXMLConstants.VIRTUE_RANK,XmlWriter.CDATA,String.valueOf(rank));
      Integer index=virtuesSet.getVirtueIndex(virtue);
      if (index!=null)
      {
        virtueAttrs.addAttribute("","",CharacterXMLConstants.VIRTUE_INDEX,XmlWriter.CDATA,index.toString());
      }
      hd.startElement("","",CharacterXMLConstants.VIRTUE_TAG,virtueAttrs);
      hd.endElement("","",CharacterXMLConstants.VIRTUE_TAG);
    }
    hd.endElement("","",CharacterXMLConstants.VIRTUES_TAG);
  }

  private void writeTomes(TransformerHandler hd, TomesSet tomes) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",CharacterXMLConstants.TOMES_TAG,attrs);
    for(StatDescription stat : StatTomesManager.getInstance().getStats())
    {
      AttributesImpl tomeAttrs=new AttributesImpl();
      tomeAttrs.addAttribute("","",CharacterXMLConstants.TOME_STAT,XmlWriter.CDATA,stat.getPersistenceKey());
      int rank=tomes.getTomeRank(stat);
      tomeAttrs.addAttribute("","",CharacterXMLConstants.TOME_RANK,XmlWriter.CDATA,String.valueOf(rank));
      hd.startElement("","",CharacterXMLConstants.TOME_TAG,tomeAttrs);
      hd.endElement("","",CharacterXMLConstants.TOME_TAG);
    }
    hd.endElement("","",CharacterXMLConstants.TOMES_TAG);
  }
}
