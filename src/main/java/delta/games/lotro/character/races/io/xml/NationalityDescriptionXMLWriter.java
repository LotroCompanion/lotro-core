package delta.games.lotro.character.races.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.races.NationalityDescription;

/**
 * Writes nationality descriptions to XML files.
 * @author DAM
 */
public class NationalityDescriptionXMLWriter
{
  /**
   * Write some nationality descriptions to a XML file.
   * @param toFile File to write to.
   * @param descriptions Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<NationalityDescription> descriptions)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",NationalityDescriptionXMLConstants.NATIONALITIES_TAG,new AttributesImpl());
        for(NationalityDescription description : descriptions)
        {
          writeNationalityDescription(hd,description);
        }
        hd.endElement("","",NationalityDescriptionXMLConstants.NATIONALITIES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeNationalityDescription(TransformerHandler hd, NationalityDescription nationalityDescription) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Code
    int code=nationalityDescription.getIdentifier();
    attrs.addAttribute("","",NationalityDescriptionXMLConstants.NATIONALITY_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Name
    String name=nationalityDescription.getName();
    if (name.length()>0)
    {
      attrs.addAttribute("","",NationalityDescriptionXMLConstants.NATIONALITY_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Description
    String description=nationalityDescription.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",NationalityDescriptionXMLConstants.NATIONALITY_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    // Icon ID
    int iconID=nationalityDescription.getIconID();
    attrs.addAttribute("","",NationalityDescriptionXMLConstants.NATIONALITY_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconID));
    // Male guideline
    String maleGuideline=nationalityDescription.getNamingGuidelineMale();
    if (maleGuideline.length()>0)
    {
      attrs.addAttribute("","",NationalityDescriptionXMLConstants.NATIONALITY_MALE_GUIDELINE_ATTR,XmlWriter.CDATA,maleGuideline);
    }
    // Female guideline
    String femaleGuideline=nationalityDescription.getNamingGuidelineFemale();
    if (femaleGuideline.length()>0)
    {
      attrs.addAttribute("","",NationalityDescriptionXMLConstants.NATIONALITY_FEMALE_GUIDELINE_ATTR,XmlWriter.CDATA,femaleGuideline);
    }
    // Title ID
    Integer titleID=nationalityDescription.getTitleID();
    if (titleID!=null)
    {
      attrs.addAttribute("","",NationalityDescriptionXMLConstants.NATIONALITY_TITLE_ID_ATTR,XmlWriter.CDATA,titleID.toString());
    }
    hd.startElement("","",NationalityDescriptionXMLConstants.NATIONALITY_TAG,attrs);
    // Aliases
    List<String> aliases=nationalityDescription.getAliases();
    for(String alias : aliases)
    {
      AttributesImpl aliasAttrs=new AttributesImpl();
      aliasAttrs.addAttribute("","",NationalityDescriptionXMLConstants.ALIAS_NAME_ATTR,XmlWriter.CDATA,alias);
      hd.startElement("","",NationalityDescriptionXMLConstants.ALIAS_TAG,aliasAttrs);
      hd.endElement("","",NationalityDescriptionXMLConstants.ALIAS_TAG);
    }
    hd.endElement("","",NationalityDescriptionXMLConstants.NATIONALITY_TAG);
  }
}
