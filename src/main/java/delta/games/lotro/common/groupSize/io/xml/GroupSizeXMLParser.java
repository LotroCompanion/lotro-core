package delta.games.lotro.common.groupSize.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.groupSize.GroupSize;

/**
 * Parser for group sizes stored in XML.
 * @author DAM
 */
public class GroupSizeXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed group sizes.
   */
  public static List<GroupSize> parseXML(File source)
  {
    List<GroupSize> ret=new ArrayList<GroupSize>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> groupSizeTags=DOMParsingTools.getChildTagsByName(root,GroupSizeXMLConstants.GROUP_SIZE_TAG);
      for(Element groupSizeTag : groupSizeTags)
      {
        GroupSize groupSize=parseGroupSize(groupSizeTag);
        ret.add(groupSize);
      }
    }
    return ret;
  }

  private static GroupSize parseGroupSize(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Code
    int code=DOMParsingTools.getIntAttribute(attrs,GroupSizeXMLConstants.GROUP_SIZE_CODE_ATTR,0);
    // Key
    String key=DOMParsingTools.getStringAttribute(attrs,GroupSizeXMLConstants.GROUP_SIZE_KEY_ATTR,"");
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,GroupSizeXMLConstants.GROUP_SIZE_NAME_ATTR,"");
    return new GroupSize(code,key,name);
  }
}
