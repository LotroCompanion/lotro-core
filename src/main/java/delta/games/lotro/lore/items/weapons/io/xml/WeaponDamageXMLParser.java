package delta.games.lotro.lore.items.weapons.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.weapons.WeaponDamageManager;

/**
 * Parser for weapon damage data stored in XML.
 * @author DAM
 */
public class WeaponDamageXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data.
   */
  public WeaponDamageManager parseXML(File source)
  {
    WeaponDamageManager ret=new WeaponDamageManager();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> weaponTags=DOMParsingTools.getChildTagsByName(root,WeaponDamageXMLConstants.WEAPON_TAG);
      for(Element weaponTag : weaponTags)
      {
        NamedNodeMap attrs=weaponTag.getAttributes();
        // Type
        String weaponTypeKey=DOMParsingTools.getStringAttribute(attrs,WeaponDamageXMLConstants.WEAPON_TYPE_ATTR,null);
        WeaponType type=WeaponType.getWeaponTypeByKey(weaponTypeKey);
        // Variance
        float duration=DOMParsingTools.getFloatAttribute(attrs,WeaponDamageXMLConstants.DAMAGE_VARIANCE_ATTR,0);
        ret.setVariance(type,duration);
      }
    }
    return ret;
  }
}
