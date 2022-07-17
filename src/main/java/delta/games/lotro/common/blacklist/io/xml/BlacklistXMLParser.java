package delta.games.lotro.common.blacklist.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.blacklist.Blacklist;

/**
 * Parser for the blacklists stored in XML.
 * @author DAM
 */
public class BlacklistXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public Blacklist parseXML(File source)
  {
    Blacklist ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=new Blacklist(source);
      List<Element> elementTags=DOMParsingTools.getChildTagsByName(root,BlacklistXMLConstants.ELEMENT,false);
      for(Element elementTag : elementTags)
      {
        parseElement(ret,elementTag);
      }
    }
    return ret;
  }

  private void parseElement(Blacklist blacklist, Element elementTag)
  {
    NamedNodeMap attrs=elementTag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,BlacklistXMLConstants.ELEMENT_ID_ATTR,0);
    blacklist.add(id);
  }
}
