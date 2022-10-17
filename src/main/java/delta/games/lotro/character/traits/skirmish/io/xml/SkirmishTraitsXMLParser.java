package delta.games.lotro.character.traits.skirmish.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;

/**
 * Parser for the skirmish traits directory stored in XML.
 * @author DAM
 */
public class SkirmishTraitsXMLParser
{
  /**
   * Parse a traits XML file.
   * @param source Source file.
   * @return List of parsed traits.
   */
  public static List<TraitDescription> parseTraitsFile(File source)
  {
    List<TraitDescription> traits=new ArrayList<TraitDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> traitTags=DOMParsingTools.getChildTagsByName(root,SkirmishTraitsXMLConstants.TRAIT_TAG);
      for(Element traitTag : traitTags)
      {
        NamedNodeMap attrs=traitTag.getAttributes();
        int id=DOMParsingTools.getIntAttribute(attrs,SkirmishTraitsXMLConstants.TRAIT_IDENTIFIER_ATTR,0);
        TraitDescription trait=TraitsManager.getInstance().getTrait(id);
        traits.add(trait);
      }
    }
    return traits;
  }
}
