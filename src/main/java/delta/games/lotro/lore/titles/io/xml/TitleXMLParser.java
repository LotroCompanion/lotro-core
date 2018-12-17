package delta.games.lotro.lore.titles.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Parser for titles descriptions stored in XML.
 * @author DAM
 */
public class TitleXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed titles.
   */
  public List<TitleDescription> parseXML(File source)
  {
    List<TitleDescription> ret=new ArrayList<TitleDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> titleTags=DOMParsingTools.getChildTagsByName(root,TitleXMLConstants.TITLE_TAG);
      for(Element titleTag : titleTags)
      {
        TitleDescription title=parseDeed(titleTag);
        ret.add(title);
      }
    }
    return ret;
  }

  private TitleDescription parseDeed(Element root)
  {
    TitleDescription title=new TitleDescription();

    NamedNodeMap attrs=root.getAttributes();

    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,TitleXMLConstants.TITLE_ID_ATTR,0);
    title.setIdentifier(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,TitleXMLConstants.TITLE_NAME_ATTR,null);
    title.setName(name);
    // Icon
    int iconId=DOMParsingTools.getIntAttribute(attrs,TitleXMLConstants.TITLE_ICON_ATTR,0);
    title.setIconId(iconId);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,TitleXMLConstants.TITLE_CATEGORY_ATTR,null);
    title.setCategory(category);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,TitleXMLConstants.TITLE_DESCRIPTION_ATTR,null);
    title.setDescription(description);
    return title;
  }
}
