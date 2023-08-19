package delta.games.lotro.lore.items.weapons.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.weapons.WeaponSpeedTables;
import delta.games.lotro.lore.items.weapons.WeaponSpeedTable;
import delta.games.lotro.lore.items.weapons.WeaponSpeedEntry;

/**
 * Parser for weapon speed tables stored in XML.
 * @author DAM
 */
public class WeaponSpeedTablesXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data.
   */
  public WeaponSpeedTables parseXML(File source)
  {
    WeaponSpeedTables ret=new WeaponSpeedTables();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> valueTableTags=DOMParsingTools.getChildTagsByName(root,WeaponSpeedTablesXMLConstants.SPEED_TABLE_TAG);
      for(Element valueTableTag : valueTableTags)
      {
        WeaponSpeedTable weaponSpeedTable=parseSpeedTable(valueTableTag);
        ret.addWeaponSpeed(weaponSpeedTable);
      }
    }
    return ret;
  }

  private WeaponSpeedTable parseSpeedTable(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Type
    String weaponTypeKey=DOMParsingTools.getStringAttribute(attrs,WeaponSpeedTablesXMLConstants.SPEED_TABLE_TYPE_ATTR,null);
    WeaponType type=WeaponType.getWeaponTypeByKey(weaponTypeKey);
    WeaponSpeedTable table=new WeaponSpeedTable(type);
    // Entries
    List<Element> entryTags=DOMParsingTools.getChildTagsByName(root,WeaponSpeedTablesXMLConstants.ENTRY_TAG);
    for(Element entryTag : entryTags)
    {
      NamedNodeMap entryAttrs=entryTag.getAttributes();
      int code=DOMParsingTools.getIntAttribute(entryAttrs,WeaponSpeedTablesXMLConstants.ENTRY_CODE_ATTR,0);
      float duration=DOMParsingTools.getFloatAttribute(entryAttrs,WeaponSpeedTablesXMLConstants.ENTRY_DURATION_ATTR,0);
      float modifier=DOMParsingTools.getFloatAttribute(entryAttrs,WeaponSpeedTablesXMLConstants.ENTRY_MODIFIER_ATTR,0);
      WeaponSpeedEntry entry=new WeaponSpeedEntry(code,duration,modifier);
      table.addSpeedEntry(entry);
    }
    return table;
  }
}
