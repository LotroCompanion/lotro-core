package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.requirements.ProfessionRequirement;

/**
 * XML I/O for crafting requirements.
 * @author DAM
 */
public class CraftingRequirementXMLIO implements RequirementSAXWriter<ProfessionRequirement>,RequirementXMLReader<ProfessionRequirement>
{
  @Override
  public ProfessionRequirement readSAX(Attributes attributes)
  {
    String requiredProfession=attributes.getValue(UsageRequirementXMLConstants.REQUIRED_PROFESSION_ATTR);
    return ProfessionRequirement.fromString(requiredProfession);
  }

  @Override
  public ProfessionRequirement readDOM(NamedNodeMap attrs)
  {
    String professionReqStr=DOMParsingTools.getStringAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_PROFESSION_ATTR,null);
    return ProfessionRequirement.fromString(professionReqStr);
  }

  @Override
  public void write(AttributesImpl attrs, ProfessionRequirement professionRequirement)
  {
    if (professionRequirement!=null)
    {
      String professionReqStr=professionRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_PROFESSION_ATTR,XmlWriter.CDATA,professionReqStr);
    }
  }
}
