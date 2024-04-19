package delta.games.lotro.character.xp.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.xp.XPTable;

/**
 * Parser for XP tables stored in XML.
 * @author DAM
 */
public class XPTableXMLParser
{
  /**
   * Parse a legendary data XML file.
   * @param source Source file.
   * @return Parsed data.
   */
  public static XPTable parseXPTableFile(File source)
  {
    XPTable ret=new XPTable();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      long[] xpTable=parseTable(root,XPTableXMLConstants.ENTRY_TAG,XPTableXMLConstants.XP_LEVEL_ATTR,XPTableXMLConstants.XP_VALUE_ATTR);
      ret.setXpTable(xpTable);
    }
    return ret;
  }

  private static long[] parseTable(Element root, String tagName, String xAttr, String yAttr)
  {
    long[] ret=null;
    List<Element> tags=DOMParsingTools.getChildTagsByName(root,tagName);
    int nbValues=tags.size();
    if (nbValues>0)
    {
      ret=new long[nbValues+1];
      for(Element tag : tags)
      {
        NamedNodeMap attrs=tag.getAttributes();
        int x=DOMParsingTools.getIntAttribute(attrs,xAttr,0);
        long y=DOMParsingTools.getLongAttribute(attrs,yAttr,0);
        ret[x]=y;
      }
    }
    return ret;
  }
}
