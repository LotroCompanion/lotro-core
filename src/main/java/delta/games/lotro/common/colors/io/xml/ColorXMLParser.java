package delta.games.lotro.common.colors.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Parser for color descriptions stored in XML.
 * @author DAM
 */
public class ColorXMLParser
{
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public ColorXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("colors");
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed colors.
   */
  public List<ColorDescription> parseXML(File source)
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

  private ColorDescription parseColor(Element root)
  {
    ColorDescription color=new ColorDescription();

    NamedNodeMap attrs=root.getAttributes();

    // Code
    int code=DOMParsingTools.getIntAttribute(attrs,ColorXMLConstants.COLOR_CODE_ATTR,0);
    color.setIntCode(code);
    // Float code
    float floatCode=DOMParsingTools.getFloatAttribute(attrs,ColorXMLConstants.COLOR_FLOAT_CODE_ATTR,0.0f);
    color.setCode(floatCode);
    // Name
    String name=_i18n.getLabel(String.valueOf(code));
    color.setName(name);
    return color;
  }
}
