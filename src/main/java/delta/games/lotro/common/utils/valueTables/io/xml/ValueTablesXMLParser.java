package delta.games.lotro.common.utils.valueTables.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.utils.valueTables.QualityBasedValuesTable;
import delta.games.lotro.lore.items.ItemQuality;

/**
 * Parser for value tables stored in XML.
 * @author DAM
 */
public class ValueTablesXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data.
   */
  public List<QualityBasedValuesTable> parseXML(File source)
  {
    List<QualityBasedValuesTable> ret=new ArrayList<QualityBasedValuesTable>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> valueTableTags=DOMParsingTools.getChildTagsByName(root,ValueTablesXMLConstants.VALUE_TABLE_TAG);
      for(Element valueTableTag : valueTableTags)
      {
        QualityBasedValuesTable valueTable=parseValueTable(valueTableTag);
        ret.add(valueTable);
      }
    }
    return ret;
  }

  private QualityBasedValuesTable parseValueTable(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    QualityBasedValuesTable table=new QualityBasedValuesTable();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,ValueTablesXMLConstants.VALUE_TABLE_ID_ATTR,0);
    table.setIdentifier(id);
    // Quality factors
    List<Element> qualityTags=DOMParsingTools.getChildTagsByName(root,ValueTablesXMLConstants.QUALITY_TAG);
    for(Element qualityTag : qualityTags)
    {
      NamedNodeMap qualityAttrs=qualityTag.getAttributes();
      String qualityKey=DOMParsingTools.getStringAttribute(qualityAttrs,ValueTablesXMLConstants.QUALITY_KEY_ATTR,null);
      ItemQuality quality=ItemQuality.fromCode(qualityKey);
      float factor=DOMParsingTools.getFloatAttribute(qualityAttrs,ValueTablesXMLConstants.QUALITY_FACTOR_ATTR,0);
      table.addQualityFactor(quality,factor);
    }
    // Levels
    List<Element> baseValueTags=DOMParsingTools.getChildTagsByName(root,ValueTablesXMLConstants.BASE_VALUE_TAG);
    for(Element baseValueTag : baseValueTags)
    {
      NamedNodeMap baseValueAttrs=baseValueTag.getAttributes();
      int level=DOMParsingTools.getIntAttribute(baseValueAttrs,ValueTablesXMLConstants.BASE_VALUE_LEVEL_ATTR,0);
      float factor=DOMParsingTools.getFloatAttribute(baseValueAttrs,ValueTablesXMLConstants.BASE_VALUE_VALUE_ATTR,0);
      table.addBaseValue(level,factor);
    }
    return table;
  }
}
