package delta.games.lotro.character.titles.io.xml;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.titles.TitleStatus;
import delta.games.lotro.character.titles.TitlesStatusManager;
import delta.games.lotro.lore.titles.TitleDescription;
import delta.games.lotro.lore.titles.TitlesManager;

/**
 * Parser for the titles status stored in XML.
 * @author DAM
 */
public class TitlesStatusXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(TitlesStatusXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public TitlesStatusManager parseXML(File source)
  {
    TitlesStatusManager status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseStatus(root);
    }
    return status;
  }

  private TitlesStatusManager parseStatus(Element root)
  {
    TitlesStatusManager status=new TitlesStatusManager();
    List<Element> statusTags=DOMParsingTools.getChildTagsByName(root,TitlesStatusXMLConstants.TITLE,false);
    for(Element statusTag : statusTags)
    {
      parseTitleStatus(status,statusTag);
    }
    return status;
  }

  private void parseTitleStatus(TitlesStatusManager status, Element statusTag)
  {
    NamedNodeMap attrs=statusTag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,TitlesStatusXMLConstants.TITLE_ID_ATTR,0);
    // Create status
    TitleDescription title=TitlesManager.getInstance().getTitle(id);
    if (title==null)
    {
      // Unknown title!
      LOGGER.warn("Unknown title: "+id);
      return;
    }
    TitleStatus newStatus=status.get(title,true);
    // Acquisition date
    long acquisitionDate=DOMParsingTools.getLongAttribute(attrs,TitlesStatusXMLConstants.ACQUISITION_DATE_ATTR,0);
    if (acquisitionDate!=0)
    {
      newStatus.setAcquisitionDate(Long.valueOf(acquisitionDate));
    }
  }
}
