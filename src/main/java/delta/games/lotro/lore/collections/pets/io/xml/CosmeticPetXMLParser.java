package delta.games.lotro.lore.collections.pets.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.agents.EntityClassification;
import delta.games.lotro.lore.agents.io.xml.AgentsXMLIO;
import delta.games.lotro.lore.collections.pets.CosmeticPetDescription;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for pets stored in XML.
 * @author DAM
 */
public class CosmeticPetXMLParser
{
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public CosmeticPetXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("pets");
  }

  /**
   * Parse pets from an XML file.
   * @param source Source file.
   * @return List of parsed pets.
   */
  public List<CosmeticPetDescription> parsePetsFile(File source)
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
  private CosmeticPetDescription parsePet(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,CosmeticPetXMLConstants.PET_IDENTIFIER_ATTR,0);
    CosmeticPetDescription ret=new CosmeticPetDescription(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,CosmeticPetXMLConstants.PET_NAME_ATTR,"");
    name=_i18n.getLabel(String.valueOf(id));
    ret.setName(name);
    // Initial Name
    String initialName=DOMParsingTools.getStringAttribute(attrs,CosmeticPetXMLConstants.PET_INITIAL_NAME_ATTR,"");
    initialName=I18nRuntimeUtils.getLabel(_i18n,initialName);
    ret.setInitialName(initialName);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,CosmeticPetXMLConstants.PET_DESCRIPTION_ATTR,"");
    description=I18nRuntimeUtils.getLabel(_i18n,description);
    ret.setDescription(description);
    // Source description
    String sourceDescription=DOMParsingTools.getStringAttribute(attrs,CosmeticPetXMLConstants.PET_SOURCE_DESCRIPTION_ATTR,"");
    sourceDescription=I18nRuntimeUtils.getLabel(_i18n,sourceDescription);
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
