package delta.games.lotro.common.stats.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatType;

/**
 * Parser for stat descriptions stored in XML.
 * @author DAM
 */
public class StatXMLParser
{
  /**
   * Parse a stat descriptions XML file.
   * @param source Source file.
   * @return List of parsed stat descriptions.
   */
  public static List<StatDescription> parseStatDescriptionsFile(File source)
  {
    List<StatDescription> descriptions=new ArrayList<StatDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> classTags=DOMParsingTools.getChildTagsByName(root,StatXMLConstants.STAT_TAG);
      for(Element classTag : classTags)
      {
        StatDescription description=parseStatDescription(classTag);
        descriptions.add(description);
      }
    }
    return descriptions;
  }

  /**
   * Build a stat description from an XML tag.
   * @param root Root XML tag.
   * @return A stat description.
   */
  private static StatDescription parseStatDescription(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,StatXMLConstants.STAT_ID_ATTR,0);
    StatDescription description=new StatDescription(id);
    // Index
    int index=DOMParsingTools.getIntAttribute(attrs,StatXMLConstants.STAT_INDEX_ATTR,-1);
    if (index>=0)
    {
      description.setIndex(Integer.valueOf(index));
    }
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,StatXMLConstants.STAT_INTERNAL_NAME_ATTR,null);
    description.setInternalName(name);
    // Key
    String key=DOMParsingTools.getStringAttribute(attrs,StatXMLConstants.STAT_KEY_ATTR,null);
    description.setKey(key);
    // Legacy key
    String legacyKey=DOMParsingTools.getStringAttribute(attrs,StatXMLConstants.STAT_LEGACY_KEY_ATTR,null);
    description.setLegacyKey(legacyKey);
    // Legacy name
    String legacyName=DOMParsingTools.getStringAttribute(attrs,StatXMLConstants.STAT_LEGACY_NAME_ATTR,null);
    description.setLegacyName(legacyName);
    // Is percentage
    boolean isPercentage=DOMParsingTools.getBooleanAttribute(attrs,StatXMLConstants.STAT_IS_PERCENTAGE_ATTR,false);
    description.setPercentage(isPercentage);
    // Type
    String typeStr=DOMParsingTools.getStringAttribute(attrs,StatXMLConstants.STAT_TYPE_ATTR,null);
    if (typeStr!=null)
    {
      StatType type=StatType.valueOf(typeStr);
      description.setType(type);
    }
    return description;
  }
}
