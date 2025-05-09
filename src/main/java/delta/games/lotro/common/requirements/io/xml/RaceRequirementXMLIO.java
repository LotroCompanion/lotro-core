package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.requirements.RaceRequirement;

/**
 * XML I/O for race requirements.
 * @author DAM
 */
public class RaceRequirementXMLIO implements RequirementSAXWriter<RaceRequirement>,RequirementXMLReader<RaceRequirement>
{
  @Override
  public RaceRequirement readSAX(Attributes attributes)
  {
    String requiredRaces=attributes.getValue(UsageRequirementXMLConstants.REQUIRED_RACE_ATTR);
    return RaceRequirement.fromString(requiredRaces);
  }

  @Override
  public RaceRequirement readDOM(NamedNodeMap attrs)
  {
    String requiredRaces=DOMParsingTools.getStringAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_RACE_ATTR,null);
    return RaceRequirement.fromString(requiredRaces);
  }

  @Override
  public void write(AttributesImpl attrs, RaceRequirement raceRequirement)
  {
    if (raceRequirement!=null)
    {
      String raceReqStr=raceRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_RACE_ATTR,XmlWriter.CDATA,raceReqStr);
    }
  }
}
