package delta.games.lotro.character.skills.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.skills.SkillDescription;

/**
 * Writes skills to XML files.
 * @author DAM
 */
public class SkillDescriptionXMLWriter
{
  /**
   * Write some skills to a XML file.
   * @param toFile File to write to.
   * @param skills Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<SkillDescription> skills)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",SkillDescriptionXMLConstants.SKILLS_TAG,new AttributesImpl());
        for(SkillDescription skill : skills)
        {
          writeSkill(hd,skill);
        }
        hd.endElement("","",SkillDescriptionXMLConstants.SKILLS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeSkill(TransformerHandler hd, SkillDescription skill) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=skill.getIdentifier();
    attrs.addAttribute("","",SkillDescriptionXMLConstants.SKILL_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=skill.getName();
    attrs.addAttribute("","",SkillDescriptionXMLConstants.SKILL_NAME_ATTR,XmlWriter.CDATA,name);
    // Category
    String category=skill.getCategory();
    if (category.length()>0)
    {
      attrs.addAttribute("","",SkillDescriptionXMLConstants.SKILL_CATEGORY_ATTR,XmlWriter.CDATA,category);
    }
    // Icon ID
    int iconId=skill.getIconId();
    attrs.addAttribute("","",SkillDescriptionXMLConstants.SKILL_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    // Description
    String description=skill.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",SkillDescriptionXMLConstants.SKILL_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    hd.startElement("","",SkillDescriptionXMLConstants.SKILL_TAG,attrs);
    hd.endElement("","",SkillDescriptionXMLConstants.SKILL_TAG);
  }
}
