package delta.games.lotro.common.enums.directory.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.enums.directory.LotroEnumDescription;
import delta.games.lotro.common.enums.directory.LotroEnumsDirectory;
import delta.games.lotro.utils.PerfUtils;

/**
 * Parser for the enums directory stored in XML.
 * @author DAM
 */
public class EnumsDirectoryXMLParser
{
  /**
   * Parse the contents of the enums directory.
   * @param source Source file.
   * @return the loaded enum or <code>null</code> if not found.
   */
  public LotroEnumsDirectory parseXML(File source)
  {
    Element root=DOMParsingTools.parse(source);
    if (root==null)
    {
      return null;
    }
    long now=System.currentTimeMillis();
    LotroEnumsDirectory ret=new LotroEnumsDirectory();
    List<Element> enumTags=DOMParsingTools.getChildTagsByName(root,EnumsDirectoryXMLConstants.ENUM_TAG);
    for(Element enumTag : enumTags)
    {
      NamedNodeMap enumAttrs=enumTag.getAttributes();
      int id=DOMParsingTools.getIntAttribute(enumAttrs,EnumsDirectoryXMLConstants.ENUM_ID_ATTR,0);
      String name=DOMParsingTools.getStringAttribute(enumAttrs,EnumsDirectoryXMLConstants.ENUM_NAME_ATTR,"");
      String className=DOMParsingTools.getStringAttribute(enumAttrs,EnumsDirectoryXMLConstants.ENUM_CLASSNAME_ATTR,"");
      LotroEnumDescription description=new LotroEnumDescription(id,name,className);
      ret.registerEnum(description);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog("the enums directory",duration);
    return ret;
  }
}
