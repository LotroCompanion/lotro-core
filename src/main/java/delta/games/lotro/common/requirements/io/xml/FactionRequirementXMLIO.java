package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.requirements.FactionRequirement;

/**
 * XML I/O for faction requirements.
 * @author DAM
 */
public class FactionRequirementXMLIO implements RequirementSAXWriter<FactionRequirement>,RequirementXMLReader<FactionRequirement>
{
  @Override
  public FactionRequirement readSAX(Attributes attributes)
  {
    String requiredFaction=attributes.getValue(UsageRequirementXMLConstants.REQUIRED_FACTION_ATTR);
    return FactionRequirement.fromString(requiredFaction);
  }

  @Override
  public FactionRequirement readDOM(NamedNodeMap attrs)
  {
    String requiredFaction=DOMParsingTools.getStringAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_FACTION_ATTR,null);
    return FactionRequirement.fromString(requiredFaction);
  }

  @Override
  public void write(AttributesImpl attrs, FactionRequirement factionRequirement)
  {
    if (factionRequirement!=null)
    {
      String factionReqStr=factionRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_FACTION_ATTR,XmlWriter.CDATA,factionReqStr);
    }
  }
}
