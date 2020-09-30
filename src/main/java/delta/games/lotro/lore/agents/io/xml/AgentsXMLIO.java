package delta.games.lotro.lore.agents.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.agents.AgentClassification;
import delta.games.lotro.lore.agents.EntityClassification;

/**
 * XML I/O utility methods for agents.
 * @author DAM
 */
public class AgentsXMLIO
{
  /**
   * Write classification attributes into the given attributes storage.
   * @param attrs Storage.
   * @param classification Classification data.
   */
  public static void writeClassification(AttributesImpl attrs, AgentClassification classification)
  {
    // Alignment
    String alignment=classification.getAlignment();
    if (alignment.length()>0)
    {
      attrs.addAttribute("","",AgentsXMLConstants.ALIGNMENT_ATTR,XmlWriter.CDATA,alignment);
    }
    // Agent class
    String agentClass=classification.getAgentClass();
    if (agentClass.length()>0)
    {
      attrs.addAttribute("","",AgentsXMLConstants.CLASS_ATTR,XmlWriter.CDATA,agentClass);
    }
    // Class filter
    String classFilter=classification.getClassificationFilter();
    if (classFilter.length()>0)
    {
      attrs.addAttribute("","",AgentsXMLConstants.CLASS_FILTER_ATTR,XmlWriter.CDATA,classFilter);
    }

    EntityClassification entityClassification=classification.getEntityClassification();
    // Genus
    String genus=entityClassification.getGenus();
    if (genus.length()>0)
    {
      attrs.addAttribute("","",AgentsXMLConstants.GENUS_ATTR,XmlWriter.CDATA,genus);
    }
    // Species
    String species=entityClassification.getSpecies();
    if (species.length()>0)
    {
      attrs.addAttribute("","",AgentsXMLConstants.SPECIES_ATTR,XmlWriter.CDATA,species);
    }
    // Subspecies
    String subSpecies=entityClassification.getSubSpecies();
    if (subSpecies.length()>0)
    {
      attrs.addAttribute("","",AgentsXMLConstants.SUBSPECIES_ATTR,XmlWriter.CDATA,subSpecies);
    }
  }

  /**
   * Parse classification data from the given tag.
   * @param classificationTag Input tag.
   * @param classification Storage for parsed data.
   */
  public static void parseClassificationTag(Element classificationTag, AgentClassification classification)
  {
    NamedNodeMap attrs=classificationTag.getAttributes();
    // Alignment
    String alignment=DOMParsingTools.getStringAttribute(attrs,AgentsXMLConstants.ALIGNMENT_ATTR,"");
    classification.setAlignment(alignment);
    // Agent class
    String agentClass=DOMParsingTools.getStringAttribute(attrs,AgentsXMLConstants.CLASS_ATTR,"");
    classification.setAgentClass(agentClass);
    // Class filter
    String classFilter=DOMParsingTools.getStringAttribute(attrs,AgentsXMLConstants.CLASS_FILTER_ATTR,"");
    classification.setClassificationFilter(classFilter);

    EntityClassification entityClassification=classification.getEntityClassification();
    // Genus
    String genus=DOMParsingTools.getStringAttribute(attrs,AgentsXMLConstants.GENUS_ATTR,"");
    entityClassification.setGenus(genus);
    // Species
    String species=DOMParsingTools.getStringAttribute(attrs,AgentsXMLConstants.SPECIES_ATTR,"");
    entityClassification.setSpecies(species);
    // Genus
    String subSpecies=DOMParsingTools.getStringAttribute(attrs,AgentsXMLConstants.SUBSPECIES_ATTR,"");
    entityClassification.setSubSpecies(subSpecies);
  }
}
