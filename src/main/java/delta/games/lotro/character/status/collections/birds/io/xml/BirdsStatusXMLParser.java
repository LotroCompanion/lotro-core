package delta.games.lotro.character.status.collections.birds.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.collections.birds.BirdsStatusManager;

/**
 * Parser for the birds status stored in XML.
 * @author DAM
 */
public class BirdsStatusXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public BirdsStatusManager parseXML(File source)
  {
    BirdsStatusManager status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseStatus(root);
    }
    return status;
  }

  private BirdsStatusManager parseStatus(Element root)
  {
    BirdsStatusManager status=new BirdsStatusManager();
    // Status of birds
    List<Element> statusTags=DOMParsingTools.getChildTagsByName(root,BirdsStatusXMLConstants.BIRD,false);
    for(Element statusTag : statusTags)
    {
      NamedNodeMap attrs=statusTag.getAttributes();
      int id=DOMParsingTools.getIntAttribute(attrs,BirdsStatusXMLConstants.BIRD_ID_ATTR,0);
      status.setKnown(id);
    }
    return status;
  }
}
