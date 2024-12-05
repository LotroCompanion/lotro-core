package delta.games.lotro.lore.agents.io.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.NumericTools;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.enums.AgentClass;
import delta.games.lotro.common.enums.Alignment;
import delta.games.lotro.common.enums.ClassificationFilter;
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
    Alignment alignment=classification.getAlignment();
    if (alignment!=null)
    {
      attrs.addAttribute("","",AgentsXMLConstants.ALIGNMENT_ATTR,XmlWriter.CDATA,String.valueOf(alignment.getCode()));
    }
    // Agent class
    AgentClass agentClass=classification.getAgentClass();
    if (agentClass!=null)
    {
      attrs.addAttribute("","",AgentsXMLConstants.CLASS_ATTR,XmlWriter.CDATA,String.valueOf(agentClass.getCode()));
    }
    // Class filter
    ClassificationFilter classFilter=classification.getClassificationFilter();
    if (classFilter!=null)
    {
      attrs.addAttribute("","",AgentsXMLConstants.CLASS_FILTER_ATTR,XmlWriter.CDATA,String.valueOf(classFilter.getCode()));
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
    LotroEnumsRegistry registry=LotroEnumsRegistry.getInstance();
    // Alignment
    int alignmentCode=DOMParsingTools.getIntAttribute(attrs,AgentsXMLConstants.ALIGNMENT_ATTR,0);
    if (alignmentCode!=0)
    {
      LotroEnum<Alignment> alignmentMgr=registry.get(Alignment.class);
      Alignment alignment=alignmentMgr.getEntry(alignmentCode);
      classification.setAlignment(alignment);
    }
    // Agent class
    int agentClassCode=DOMParsingTools.getIntAttribute(attrs,AgentsXMLConstants.CLASS_ATTR,0);
    if (agentClassCode!=0)
    {
      LotroEnum<AgentClass> agentClassMgr=registry.get(AgentClass.class);
      AgentClass agentClass=agentClassMgr.getEntry(agentClassCode);
      classification.setAgentClass(agentClass);
    }
    // Class filter
    int classFilterCode=DOMParsingTools.getIntAttribute(attrs,AgentsXMLConstants.CLASS_FILTER_ATTR,0);
    if (classFilterCode!=0)
    {
      LotroEnum<ClassificationFilter> classFilterMgr=registry.get(ClassificationFilter.class);
      ClassificationFilter classFilter=classFilterMgr.getEntry(classFilterCode);
      classification.setClassificationFilter(classFilter);
    }
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
    if ((genusCodes!=null) && (!genusCodes.isEmpty()))
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

  /**
   * Parse entity classification data.
   * @param entityClassification Storage.
   * @param attrs Attributes to read from.
   */
  public static void parseEntityClassification(EntityClassification entityClassification, Attributes attrs)
  {
    // Genus
    String genusCodes=SAXParsingTools.getStringAttribute(attrs,AgentsXMLConstants.GENUS_ATTR,null);
    if ((genusCodes!=null) && (!genusCodes.isEmpty())
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
    int speciesCode=SAXParsingTools.getIntAttribute(attrs,AgentsXMLConstants.SPECIES_ATTR,0);
    if (speciesCode>0)
    {
      LotroEnum<Species> speciesMgr=LotroEnumsRegistry.getInstance().get(Species.class);
      Species species=speciesMgr.getEntry(speciesCode);
      entityClassification.setSpecies(species);
    }
    // Sub-species
    int subSpeciesCode=SAXParsingTools.getIntAttribute(attrs,AgentsXMLConstants.SUBSPECIES_ATTR,0);
    if (subSpeciesCode>0)
    {
      LotroEnum<SubSpecies> subSpeciesMgr=LotroEnumsRegistry.getInstance().get(SubSpecies.class);
      SubSpecies subSpecies=subSpeciesMgr.getEntry(subSpeciesCode);
      entityClassification.setSubSpecies(subSpecies);
    }
  }
}
