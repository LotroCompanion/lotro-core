package delta.games.lotro.character.crafting.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.crafting.CraftingLevelStatus;
import delta.games.lotro.character.crafting.CraftingLevelTierStatus;
import delta.games.lotro.character.crafting.CraftingStatus;
import delta.games.lotro.character.crafting.GuildStatus;
import delta.games.lotro.character.crafting.ProfessionStatus;
import delta.games.lotro.character.reputation.io.xml.ReputationXMLWriter;
import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.crafting.CraftingSystem;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.Professions;
import delta.games.lotro.lore.crafting.Vocation;

/**
 * Writes crafting status of LOTRO characters to XML files.
 * @author DAM
 */
public class CraftingStatusXMLWriter
{
  /**
   * Write the crafting status of a character to a XML file.
   * @param outFile Output file.
   * @param status Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final CraftingStatus status, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,status);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void write(TransformerHandler hd, CraftingStatus status) throws Exception
  {
    AttributesImpl craftingAttrs=new AttributesImpl();
    String name=status.getName();
    if (name!=null)
    {
      craftingAttrs.addAttribute("","",CraftingStatusXMLConstants.CRAFTING_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",CraftingStatusXMLConstants.CRAFTING_TAG,craftingAttrs);
    // Vocation
    Vocation vocation=status.getVocation();
    if (vocation!=null)
    {
      // Vocation tag
      AttributesImpl vocationAttrs=new AttributesImpl();
      String id=vocation.getKey();
      vocationAttrs.addAttribute("","",CraftingStatusXMLConstants.VOCATION_ID_ATTR,XmlWriter.CDATA,id);
      hd.startElement("","",CraftingStatusXMLConstants.VOCATION_TAG,vocationAttrs);
      hd.endElement("","",CraftingStatusXMLConstants.VOCATION_TAG);

      // Professions
      for(Profession profession : vocation.getProfessions())
      {
        ProfessionStatus professionStatus=status.getProfessionStatus(profession);
        if (professionStatus!=null)
        {
          writeProfessionStatus(hd,professionStatus);
        }
      }

      // Guild status
      Professions professions=CraftingSystem.getInstance().getData().getProfessionsRegistry();
      for(Profession profession : professions.getAll())
      {
        GuildStatus guildStatus=status.getGuildStatus(profession,false);
        if (guildStatus!=null)
        {
          AttributesImpl guildAttrs=new AttributesImpl();
          Profession guildProfession=guildStatus.getProfession();
          if (guildProfession!=null)
          {
            // Profession
            guildAttrs.addAttribute("","",CraftingStatusXMLConstants.GUILD_PROFESSION_ATTR,XmlWriter.CDATA,guildProfession.getKey());
            hd.startElement("","",CraftingStatusXMLConstants.GUILD_TAG,guildAttrs);
            ReputationXMLWriter.writeFactionStatus(hd,guildStatus.getFactionStatus());
            hd.endElement("","",CraftingStatusXMLConstants.GUILD_TAG);
          }
        }
      }
    }
    hd.endElement("","",CraftingStatusXMLConstants.CRAFTING_TAG);
  }

  private void writeProfessionStatus(TransformerHandler hd, ProfessionStatus status) throws Exception
  {
    AttributesImpl professionAttrs=new AttributesImpl();
    Profession profession=status.getProfession();
    String id=profession.getKey();
    professionAttrs.addAttribute("","",CraftingStatusXMLConstants.PROFESSION_ID_ATTR,XmlWriter.CDATA,id);
    Long validityDate=status.getValidityDate();
    if (validityDate!=null)
    {
      professionAttrs.addAttribute("","",CraftingStatusXMLConstants.PROFESSION_VALIDITY_DATE_ATTR,XmlWriter.CDATA,validityDate.toString());
    }
    hd.startElement("","",CraftingStatusXMLConstants.PROFESSION_TAG,professionAttrs);

    for(CraftingLevel level : profession.getLevels())
    {
      CraftingLevelStatus levelStatus=status.getLevelStatus(level);
      if (levelStatus!=null)
      {
        AttributesImpl levelAttrs=new AttributesImpl();
        int tier=levelStatus.getLevel().getTier();
        levelAttrs.addAttribute("","",CraftingStatusXMLConstants.LEVEL_TIER_ATTR,XmlWriter.CDATA,String.valueOf(tier));
        hd.startElement("","",CraftingStatusXMLConstants.LEVEL_TAG,levelAttrs);

        // Proficiency
        writeCraftingLevelTierStatus(hd,CraftingStatusXMLConstants.PROFICIENCY_TAG,levelStatus.getProficiency());
        // Mastery
        writeCraftingLevelTierStatus(hd,CraftingStatusXMLConstants.MASTERY_TAG,levelStatus.getMastery());

        hd.endElement("","",CraftingStatusXMLConstants.LEVEL_TAG);
      }
    }

    hd.endElement("","",CraftingStatusXMLConstants.PROFESSION_TAG);
  }

  private void writeCraftingLevelTierStatus(TransformerHandler hd, String tagName, CraftingLevelTierStatus status) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    int xp=status.getAcquiredXP();
    attrs.addAttribute("","",CraftingStatusXMLConstants.XP_ATTR,XmlWriter.CDATA,String.valueOf(xp));
    boolean completed=status.isCompleted();
    attrs.addAttribute("","",CraftingStatusXMLConstants.COMPLETED_ATTR,XmlWriter.CDATA,String.valueOf(completed));
    long completionDate=status.getCompletionDate();
    attrs.addAttribute("","",CraftingStatusXMLConstants.COMPLETION_DATE_ATTR,XmlWriter.CDATA,String.valueOf(completionDate));
    hd.startElement("","",tagName,attrs);
    hd.endElement("","",tagName);
  }
}
