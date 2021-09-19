package delta.games.lotro.lore.collections.pets.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.agents.EntityClassification;
import delta.games.lotro.lore.agents.io.xml.AgentsXMLIO;
import delta.games.lotro.lore.collections.pets.CosmeticPetDescription;

/**
 * Parser for pets stored in XML.
 * @author DAM
 */
public class CosmeticPetXMLParser
{
  /**
   * Parse pets from an XML file.
   * @param source Source file.
   * @return List of parsed pets.
   */
  public static List<CosmeticPetDescription> parsePetsFile(File source)
  {
    List<CosmeticPetDescription> pets=new ArrayList<CosmeticPetDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> petTags=DOMParsingTools.getChildTagsByName(root,CosmeticPetXMLConstants.PET_TAG);
      for(Element petTag : petTags)
      {
        CosmeticPetDescription pet=parsePet(petTag);
        pets.add(pet);
      }
    }
    return pets;
  }

  /**
   * Build a pet from an XML tag.
   * @param root Root XML tag.
   * @return A pet.
   */
  private static CosmeticPetDescription parsePet(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,CosmeticPetXMLConstants.PET_IDENTIFIER_ATTR,0);
    CosmeticPetDescription ret=new CosmeticPetDescription(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,CosmeticPetXMLConstants.PET_NAME_ATTR,"");
    ret.setName(name);
    // Initial Name
    String initialName=DOMParsingTools.getStringAttribute(attrs,CosmeticPetXMLConstants.PET_INITIAL_NAME_ATTR,"");
    ret.setInitialName(initialName);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,CosmeticPetXMLConstants.PET_DESCRIPTION_ATTR,"");
    ret.setDescription(description);
    // Source description
    String sourceDescription=DOMParsingTools.getStringAttribute(attrs,CosmeticPetXMLConstants.PET_SOURCE_DESCRIPTION_ATTR,"");
    ret.setSourceDescription(sourceDescription);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,CosmeticPetXMLConstants.PET_ICON_ID_ATTR,0);
    ret.setIconId(iconId);
    // Entity classification
    EntityClassification classification=ret.getClassification();
    AgentsXMLIO.parseEntityClassification(classification,attrs);
    return ret;
  }
}
