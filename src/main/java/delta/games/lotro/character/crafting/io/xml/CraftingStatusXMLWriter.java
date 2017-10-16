package delta.games.lotro.character.crafting.io.xml;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.StreamTools;
import delta.games.lotro.character.crafting.CraftingLevelStatus;
import delta.games.lotro.character.crafting.CraftingLevelTierStatus;
import delta.games.lotro.character.crafting.CraftingStatus;
import delta.games.lotro.character.crafting.GuildStatus;
import delta.games.lotro.character.crafting.ProfessionStatus;
import delta.games.lotro.character.reputation.io.xml.ReputationXMLWriter;
import delta.games.lotro.crafting.CraftingLevel;
import delta.games.lotro.crafting.Profession;
import delta.games.lotro.crafting.Vocation;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Writes crafting status of LOTRO characters to XML files.
 * @author DAM
 */
public class CraftingStatusXMLWriter
{
  private static final Logger _logger=LotroLoggers.getLotroLogger();

  private static final String CDATA="CDATA";

  /**
   * Write the crafting status of a character to a XML file.
   * @param outFile Output file.
   * @param status Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, CraftingStatus status, String encoding)
  {
    boolean ret;
    FileOutputStream fos=null;
    try
    {
      fos=new FileOutputStream(outFile);
      SAXTransformerFactory tf=(SAXTransformerFactory)TransformerFactory.newInstance();
      TransformerHandler hd=tf.newTransformerHandler();
      Transformer serializer=hd.getTransformer();
      serializer.setOutputProperty(OutputKeys.ENCODING,encoding);
      serializer.setOutputProperty(OutputKeys.INDENT,"yes");

      StreamResult streamResult=new StreamResult(fos);
      hd.setResult(streamResult);
      hd.startDocument();
      write(hd,status);
      hd.endDocument();
      ret=true;
    }
    catch (Exception exception)
    {
      _logger.error("",exception);
      ret=false;
    }
    finally
    {
      StreamTools.close(fos);
    }
    return ret;
  }

  private void write(TransformerHandler hd, CraftingStatus status) throws Exception
  {
    AttributesImpl craftingAttrs=new AttributesImpl();
    String name=status.getName();
    if (name!=null)
    {
      craftingAttrs.addAttribute("","",CraftingStatusXMLConstants.CRAFTING_NAME_ATTR,CDATA,name);
    }
    hd.startElement("","",CraftingStatusXMLConstants.CRAFTING_TAG,craftingAttrs);
    // Vocation
    Vocation vocation=status.getVocation();
    if (vocation!=null)
    {
      // Vocation tag
      AttributesImpl vocationAttrs=new AttributesImpl();
      String id=vocation.getIdentifier();
      vocationAttrs.addAttribute("","",CraftingStatusXMLConstants.VOCATION_ID_ATTR,CDATA,id);
      hd.startElement("","",CraftingStatusXMLConstants.VOCATION_TAG,vocationAttrs);
      hd.endElement("","",CraftingStatusXMLConstants.VOCATION_TAG);

      // Professions
      Profession[] professions=vocation.getProfessions();
      for(Profession profession : professions)
      {
        ProfessionStatus professionStatus=status.getProfessionStatus(profession);
        if (professionStatus!=null)
        {
          writeProfessionStatus(hd,professionStatus);
        }
      }

      // Guild status
      GuildStatus guildStatus=status.getGuildStatus();
      Profession guildProfession=guildStatus.getProfession();
      if (guildProfession!=null)
      {
        AttributesImpl guildAttrs=new AttributesImpl();
        guildAttrs.addAttribute("","",CraftingStatusXMLConstants.GUILD_PROFESSION_ATTR,CDATA,guildProfession.getKey());
        hd.startElement("","",CraftingStatusXMLConstants.GUILD_TAG,guildAttrs);
        ReputationXMLWriter.writeFactionData(hd,guildStatus.getFactionData());
        hd.endElement("","",CraftingStatusXMLConstants.GUILD_TAG);
      }
    }
    hd.endElement("","",CraftingStatusXMLConstants.CRAFTING_TAG);
  }

  private void writeProfessionStatus(TransformerHandler hd, ProfessionStatus status) throws Exception
  {
    AttributesImpl professionAttrs=new AttributesImpl();
    String id=status.getProfession().getKey();
    professionAttrs.addAttribute("","",CraftingStatusXMLConstants.PROFESSION_ID_ATTR,CDATA,id);
    Long validityDate=status.getValidityDate();
    if (validityDate!=null)
    {
      professionAttrs.addAttribute("","",CraftingStatusXMLConstants.PROFESSION_VALIDITY_DATE_ATTR,CDATA,validityDate.toString());
    }
    hd.startElement("","",CraftingStatusXMLConstants.PROFESSION_TAG,professionAttrs);

    for(CraftingLevel level : CraftingLevel.ALL_TIERS)
    {
      CraftingLevelStatus levelStatus=status.getLevelStatus(level);
      if (levelStatus!=null)
      {
        AttributesImpl levelAttrs=new AttributesImpl();
        int tier=levelStatus.getLevel().getTier();
        levelAttrs.addAttribute("","",CraftingStatusXMLConstants.LEVEL_TIER_ATTR,CDATA,String.valueOf(tier));
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
    attrs.addAttribute("","",CraftingStatusXMLConstants.XP_ATTR,CDATA,String.valueOf(xp));
    boolean completed=status.isCompleted();
    attrs.addAttribute("","",CraftingStatusXMLConstants.COMPLETED_ATTR,CDATA,String.valueOf(completed));
    long completionDate=status.getCompletionDate();
    attrs.addAttribute("","",CraftingStatusXMLConstants.COMPLETION_DATE_ATTR,CDATA,String.valueOf(completionDate));
    hd.startElement("","",tagName,attrs);
    hd.endElement("","",tagName);
  }
}
