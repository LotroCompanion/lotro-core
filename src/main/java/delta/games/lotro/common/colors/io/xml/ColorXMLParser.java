package delta.games.lotro.common.colors.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.colors.ColorDescription;

/**
 * Parser for color descriptions stored in XML.
 * @author DAM
 */
public class ColorXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed colors.
   */
  public static List<ColorDescription> parseXML(File source)
  {
    List<ColorDescription> ret=new ArrayList<ColorDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> colorTags=DOMParsingTools.getChildTagsByName(root,ColorXMLConstants.COLOR_TAG);
      for(Element colorTag : colorTags)
      {
        ColorDescription color=parseColor(colorTag);
        ret.add(color);
      }
    }
    return ret;
  }

  private static ColorDescription parseColor(Element root)
  {
    ColorDescription color=new ColorDescription();

    NamedNodeMap attrs=root.getAttributes();

    // Code
    float code=DOMParsingTools.getFloatAttribute(attrs,ColorXMLConstants.COLOR_CODE_ATTR,0.0f);
    color.setCode(code);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,ColorXMLConstants.COLOR_NAME_ATTR,null);
    color.setName(name);
    return color;
  }
}
