package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.requirements.TraitRequirement;

/**
 * XML I/O for trait requirements.
 * @author DAM
 */
public class TraitRequirementXMLIO implements RequirementSAXWriter<TraitRequirement>,RequirementXMLReader<TraitRequirement>
{
  @Override
  public TraitRequirement readSAX(Attributes attributes)
  {
    Integer traitID=SAXParsingTools.getIntegerAttribute(attributes,UsageRequirementXMLConstants.REQUIRED_TRAIT_ATTR,null);
    return buildRequirement(traitID);
  }

  @Override
  public TraitRequirement readDOM(NamedNodeMap attrs)
  {
    Integer traitID=DOMParsingTools.getIntegerAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_TRAIT_ATTR,null);
    return buildRequirement(traitID);
  }

  private TraitRequirement buildRequirement(Integer traitID)
  {
    TraitRequirement traitRequirement=null;
    if (traitID!=null)
    {
      TraitDescription trait=TraitsManager.getInstance().getTrait(traitID.intValue());
      if (trait!=null)
      {
        traitRequirement=new TraitRequirement(trait);
      }
    }
    return traitRequirement;
  }

  @Override
  public void write(AttributesImpl attrs, TraitRequirement traitRequirement)
  {
    if (traitRequirement!=null)
    {
      int traitID=traitRequirement.getTrait().getIdentifier();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_TRAIT_ATTR,XmlWriter.CDATA,String.valueOf(traitID));
    }
  }
}
