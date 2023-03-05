package delta.games.lotro.lore.titles.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Writes LOTRO titles to XML files.
 * @author DAM
 */
public class TitleXMLWriter
{
  /**
   * Write a file with titles.
   * @param toFile Output file.
   * @param titles Titles to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeTitlesFile(File toFile, List<TitleDescription> titles)
  {
    TitleXMLWriter writer=new TitleXMLWriter();
    Collections.sort(titles,new IdentifiableComparator<TitleDescription>());
    boolean ok=writer.writeTitles(toFile,titles,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write titles to a XML file.
   * @param outFile Output file.
   * @param titles Titles to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeTitles(File outFile, final List<TitleDescription> titles, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeTitles(hd,titles);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeTitles(TransformerHandler hd, List<TitleDescription> titles) throws SAXException
  {
    hd.startElement("","",TitleXMLConstants.TITLES_TAG,new AttributesImpl());
    for(TitleDescription title : titles)
    {
      writeTitle(hd,title);
    }
    hd.endElement("","",TitleXMLConstants.TITLES_TAG);
  }

  private void writeTitle(TransformerHandler hd, TitleDescription title) throws SAXException
  {
    AttributesImpl titleAttrs=new AttributesImpl();

    // In-game identifier
    int id=title.getIdentifier();
    if (id!=0)
    {
      titleAttrs.addAttribute("","",TitleXMLConstants.TITLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Name
    String name=title.getName();
    if (name.length()>0)
    {
      titleAttrs.addAttribute("","",TitleXMLConstants.TITLE_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Raw name
    String rawName=title.getRawName();
    if (rawName.length()>0)
    {
      titleAttrs.addAttribute("","",TitleXMLConstants.TITLE_RAW_NAME_ATTR,XmlWriter.CDATA,rawName);
    }
    // Icon identifier
    int iconId=title.getIconId();
    if (iconId!=0)
    {
      titleAttrs.addAttribute("","",TitleXMLConstants.TITLE_ICON_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    }
    // Category
    String category=title.getCategory();
    if (category.length()>0)
    {
      titleAttrs.addAttribute("","",TitleXMLConstants.TITLE_CATEGORY_ATTR,XmlWriter.CDATA,category);
    }
    // Exclusion group
    String exclusionGroup=title.getExclusionGroup();
    if (exclusionGroup!=null)
    {
      titleAttrs.addAttribute("","",TitleXMLConstants.TITLE_EXCLUSION_GROUP_ATTR,XmlWriter.CDATA,exclusionGroup);
    }
    // Priority
    Integer priority=title.getPriority();
    if (priority!=null)
    {
      titleAttrs.addAttribute("","",TitleXMLConstants.TITLE_PRIORITY_ATTR,XmlWriter.CDATA,priority.toString());
    }
    // Description
    String description=title.getDescription();
    if (description!=null)
    {
      titleAttrs.addAttribute("","",TitleXMLConstants.TITLE_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    hd.startElement("","",TitleXMLConstants.TITLE_TAG,titleAttrs);
    hd.endElement("","",TitleXMLConstants.TITLE_TAG);
  }
}
