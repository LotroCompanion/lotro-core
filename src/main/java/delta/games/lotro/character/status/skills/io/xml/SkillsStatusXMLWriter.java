package delta.games.lotro.character.status.skills.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.status.skills.SkillStatus;
import delta.games.lotro.character.status.skills.SkillsStatusManager;

/**
 * Writes a skills status to an XML file.
 * @author DAM
 */
public class SkillsStatusXMLWriter
{
  /**
   * Write a status to an XML file.
   * @param outFile Output file.
   * @param status Status to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final SkillsStatusManager status, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeStatus(hd,status);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a status to the given XML stream.
   * @param hd XML output stream.
   * @param statusMgr Status to write.
   * @throws SAXException If an error occurs.
   */
  private void writeStatus(TransformerHandler hd, SkillsStatusManager statusMgr) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",SkillsStatusXMLConstants.SKILLS_STATUS_TAG,attrs);

    List<SkillStatus> skillsStatuses=statusMgr.getAll();
    for(SkillStatus skillStatus : skillsStatuses)
    {
      AttributesImpl statusAttrs=new AttributesImpl();
      SkillDescription skill=skillStatus.getSkill();
      // ID
      int skillID=skill.getIdentifier();
      statusAttrs.addAttribute("","",SkillsStatusXMLConstants.SKILL_ID_ATTR,XmlWriter.CDATA,String.valueOf(skillID));
      // Name
      String name=skill.getName();
      statusAttrs.addAttribute("","",SkillsStatusXMLConstants.SKILL_NAME_ATTR,XmlWriter.CDATA,name);
      // Available
      boolean available=skillStatus.isAvailable();
      if (available)
      {
        statusAttrs.addAttribute("","",SkillsStatusXMLConstants.SKILL_AVAILABLE_ATTR,XmlWriter.CDATA,String.valueOf(available));
      }
      hd.startElement("","",SkillsStatusXMLConstants.SKILL,statusAttrs);
      hd.endElement("","",SkillsStatusXMLConstants.SKILL);
    }
    hd.endElement("","",SkillsStatusXMLConstants.SKILLS_STATUS_TAG);
  }
}
