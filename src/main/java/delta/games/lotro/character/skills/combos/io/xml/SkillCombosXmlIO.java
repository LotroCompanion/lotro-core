package delta.games.lotro.character.skills.combos.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.combos.SkillComboElement;
import delta.games.lotro.character.skills.combos.SkillCombos;
import delta.games.lotro.utils.Proxy;

/**
 * XML I/O for skill combos.
 * @author DAM
 */
public class SkillCombosXmlIO
{
 /**
   * Write skill combos.
   * @param hd Output
   * @param combos Attack.
   * @throws SAXException If an error occurs.
   */
  public static void writeSkillCombos(TransformerHandler hd, SkillCombos combos) throws SAXException
  {
    if (combos==null)
    {
      return;
    }
    AttributesImpl attrs=new AttributesImpl();
    // Property identifier
    int propertyID=combos.getComboPropertyID();
    attrs.addAttribute("","",SkillCombosXMLConstants.COMBOS_PROPERTY_ID_ATTR,XmlWriter.CDATA,String.valueOf(propertyID));
    hd.startElement("","",SkillCombosXMLConstants.COMBOS_TAG,attrs);
    // Combo elements
    for(SkillComboElement element : combos.getElements())
    {
      AttributesImpl elementAttrs=new AttributesImpl();
      // Value
      int value=element.getValue();
      elementAttrs.addAttribute("","",SkillCombosXMLConstants.COMBO_VALUE_ATTR,XmlWriter.CDATA,String.valueOf(value));
      // Skill
      Proxy<SkillDescription> proxy=element.getSkill();
      // - ID
      int skillID=proxy.getId();
      elementAttrs.addAttribute("","",SkillCombosXMLConstants.COMBO_SKILL_ID_ATTR,XmlWriter.CDATA,String.valueOf(skillID));
      // - name
      String skillName=proxy.getName();
      elementAttrs.addAttribute("","",SkillCombosXMLConstants.COMBO_SKILL_NAME_ATTR,XmlWriter.CDATA,skillName);
      hd.startElement("","",SkillCombosXMLConstants.COMBO_TAG,elementAttrs);
      hd.endElement("","",SkillCombosXMLConstants.COMBO_TAG);
    }
    hd.endElement("","",SkillCombosXMLConstants.COMBOS_TAG);
  }

  /**
   * Read skill combos.
   * @param root Skill tag.
   * @return the loaded combos.
   */
  public static SkillCombos readSkillCombos(Element root)
  {
    Element skillCombosTag=DOMParsingTools.getChildTagByName(root,SkillCombosXMLConstants.COMBOS_TAG);
    if (skillCombosTag==null)
    {
      return null;
    }
    NamedNodeMap attrs=skillCombosTag.getAttributes();
    // Property ID
    int propertyID=DOMParsingTools.getIntAttribute(attrs,SkillCombosXMLConstants.COMBOS_PROPERTY_ID_ATTR,0);
    SkillCombos ret=new SkillCombos(propertyID);
    // Elements
    List<Element> comboTags=DOMParsingTools.getChildTagsByName(skillCombosTag,SkillCombosXMLConstants.COMBO_TAG);
    for(Element comboTag : comboTags)
    {
      NamedNodeMap elementAttrs=comboTag.getAttributes();
      // Value
      int value=DOMParsingTools.getIntAttribute(elementAttrs,SkillCombosXMLConstants.COMBO_VALUE_ATTR,0);
      // Skill ID
      int skillID=DOMParsingTools.getIntAttribute(elementAttrs,SkillCombosXMLConstants.COMBO_SKILL_ID_ATTR,0);
      // Skill name
      String name=DOMParsingTools.getStringAttribute(elementAttrs,SkillCombosXMLConstants.COMBO_SKILL_NAME_ATTR,"");
      Proxy<SkillDescription> proxy=new Proxy<SkillDescription>();
      proxy.setId(skillID);
      proxy.setName(name);
      SkillComboElement element=new SkillComboElement(value,proxy);
      ret.addElement(element);
    }
    return ret;
  }
}
