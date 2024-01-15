package delta.games.lotro.character.traits.prerequisites.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.prerequisites.AbstractTraitPrerequisite;
import delta.games.lotro.character.traits.prerequisites.CompoundTraitPrerequisite;
import delta.games.lotro.character.traits.prerequisites.SimpleTraitPrerequisite;
import delta.games.lotro.character.traits.prerequisites.TraitLogicOperator;
import delta.games.lotro.utils.Proxy;

/**
 * Writes trait pre-requisites to XML documents.
 * @author DAM
 */
public class TraitPrerequisitesXMLWriter
{
  /**
   * Write a trait pre-requisite.
   * @param hd Output stream.
   * @param traitPrerequisite Data to write.
   * @throws SAXException If an error occurs.
   */
  public void writeTraitPrerequisite(TransformerHandler hd, AbstractTraitPrerequisite traitPrerequisite) throws SAXException
  {
    if (traitPrerequisite instanceof CompoundTraitPrerequisite)
    {
      writeCompoundTraitPrequisite(hd,(CompoundTraitPrerequisite)traitPrerequisite);
    }
    else if (traitPrerequisite instanceof SimpleTraitPrerequisite)
    {
      writeSimpleTraitPrequisite(hd,(SimpleTraitPrerequisite)traitPrerequisite);
    }
  }

  private void writeCompoundTraitPrequisite(TransformerHandler hd, CompoundTraitPrerequisite compoundPrerequisite) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Operator
    TraitLogicOperator operator=compoundPrerequisite.getOperator();
    attrs.addAttribute("","",TraitPrerequisitesXMLConstants.OPERATOR_ATTR,XmlWriter.CDATA,operator.name());
    hd.startElement("","",TraitPrerequisitesXMLConstants.COMPOUND_PREREQUISITE_TAG,attrs);
    // Child pre-requisites
    for(AbstractTraitPrerequisite traitPrerequisite : compoundPrerequisite.getPrerequisites())
    {
      writeTraitPrerequisite(hd,traitPrerequisite);
    }
    hd.endElement("","",TraitPrerequisitesXMLConstants.COMPOUND_PREREQUISITE_TAG);
  }

  private void writeSimpleTraitPrequisite(TransformerHandler hd, SimpleTraitPrerequisite traitPrerequisite) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    Proxy<TraitDescription> proxy=traitPrerequisite.getTraitProxy();
    // ID
    int id=proxy.getId();
    attrs.addAttribute("","",TraitPrerequisitesXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=proxy.getName();
    if ((name!=null) && (!name.isEmpty()))
    {
      attrs.addAttribute("","",TraitPrerequisitesXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",TraitPrerequisitesXMLConstants.SIMPLE_PREREQUISITE_TAG,attrs);
    hd.endElement("","",TraitPrerequisitesXMLConstants.SIMPLE_PREREQUISITE_TAG);
  }
}
