package delta.games.lotro.common.inductions.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.inductions.Induction;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.properties.io.ModPropertyListIO;

/**
 * Parser for inductions stored in XML.
 * @author DAM
 */
public class InductionXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed colors.
   */
  public List<Induction> parseXML(File source)
  {
    List<Induction> ret=new ArrayList<Induction>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> inductionTags=DOMParsingTools.getChildTagsByName(root,InductionXMLConstants.INDUCTION_TAG);
      for(Element inductionTag : inductionTags)
      {
        Induction induction=parseInduction(inductionTag);
        ret.add(induction);
      }
    }
    return ret;
  }

  private Induction parseInduction(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,InductionXMLConstants.INIDUCTION_IDENTIFIER_ATTR,0);
    Induction induction=new Induction(id);
    // Duration
    float duration=DOMParsingTools.getFloatAttribute(attrs,InductionXMLConstants.INDUCTION_DURATION_ATTR,0.0f);
    induction.setDuration(duration);
    // Add modifiers
    String addModifiersStr=DOMParsingTools.getStringAttribute(attrs,InductionXMLConstants.INDUCTION_ADD_MODIFIERS_ATTR,null);
    ModPropertyList addMods=ModPropertyListIO.fromPersistedString(addModifiersStr);
    induction.setAddMods(addMods);
    // Multiply modifiers
    String multiplyModifiersStr=DOMParsingTools.getStringAttribute(attrs,InductionXMLConstants.INDUCTION_MULTIPLY_MODIFIERS_ATTR,null);
    ModPropertyList multiplyModifiers=ModPropertyListIO.fromPersistedString(multiplyModifiersStr);
    induction.setMultiplyMods(multiplyModifiers);
    return induction;
  }
}
