package delta.games.lotro.lore.agents.io.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.NumericTools;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.enums.Genus;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.Species;
import delta.games.lotro.common.enums.SubSpecies;
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
    // Entity classification
    EntityClassification entityClassification=classification.getEntityClassification();
    writeEntityClassification(attrs,entityClassification);
  }

  /**
   * Write entity classification data.
   * @param attrs Attributes to write to.
   * @param entityClassification Input data.
   */
  public static void writeEntityClassification(AttributesImpl attrs, EntityClassification entityClassification)
  {
    // Genus
    String genus=entityClassification.getGenusPersistenceString();
    if (genus!=null)
    {
      attrs.addAttribute("","",AgentsXMLConstants.GENUS_ATTR,XmlWriter.CDATA,genus);
    }
    // Species
    Species species=entityClassification.getSpecies();
    if (species!=null)
    {
      attrs.addAttribute("","",AgentsXMLConstants.SPECIES_ATTR,XmlWriter.CDATA,String.valueOf(species.getCode()));
    }
    // Subspecies
    SubSpecies subSpecies=entityClassification.getSubSpecies();
    if (subSpecies!=null)
    {
      attrs.addAttribute("","",AgentsXMLConstants.SUBSPECIES_ATTR,XmlWriter.CDATA,String.valueOf(subSpecies.getCode()));
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
    // Entity classification
    EntityClassification entityClassification=classification.getEntityClassification();
    parseEntityClassification(entityClassification,attrs);
  }

  /**
   * Parse entity classification data.
   * @param entityClassification Storage.
   * @param attrs Attributes to read from.
   */
  public static void parseEntityClassification(EntityClassification entityClassification, NamedNodeMap attrs)
  {
    // Genus
    String genusCodes=DOMParsingTools.getStringAttribute(attrs,AgentsXMLConstants.GENUS_ATTR,null);
    if ((genusCodes!=null) && (genusCodes.length()>0))
    {
      LotroEnum<Genus> genusMgr=LotroEnumsRegistry.getInstance().get(Genus.class);
      List<Genus> genuses=new ArrayList<Genus>();
      String[] codes=genusCodes.split(",");
      for(String code : codes)
      {
        Genus genus=genusMgr.getEntry(NumericTools.parseInt(code,0));
        genuses.add(genus);
      }
      entityClassification.setGenus(genuses);
    }
    // Species
    int speciesCode=DOMParsingTools.getIntAttribute(attrs,AgentsXMLConstants.SPECIES_ATTR,0);
    if (speciesCode>0)
    {
      LotroEnum<Species> speciesMgr=LotroEnumsRegistry.getInstance().get(Species.class);
      Species species=speciesMgr.getEntry(speciesCode);
      entityClassification.setSpecies(species);
    }
    // Sub-species
    int subSpeciesCode=DOMParsingTools.getIntAttribute(attrs,AgentsXMLConstants.SUBSPECIES_ATTR,0);
    if (subSpeciesCode>0)
    {
      LotroEnum<SubSpecies> subSpeciesMgr=LotroEnumsRegistry.getInstance().get(SubSpecies.class);
      SubSpecies subSpecies=subSpeciesMgr.getEntry(subSpeciesCode);
      entityClassification.setSubSpecies(subSpecies);
    }
  }
}
