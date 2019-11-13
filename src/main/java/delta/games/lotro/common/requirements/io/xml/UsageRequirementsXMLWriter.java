package delta.games.lotro.common.requirements.io.xml;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.requirements.ClassRequirement;
import delta.games.lotro.common.requirements.RaceRequirement;
import delta.games.lotro.common.requirements.UsageRequirement;

/**
 * Writes usage requirements to XML documents.
 * @author DAM
 */
public class UsageRequirementsXMLWriter
{
  /**
   * Write a usage requirement to the given XML stream.
   * @param attrs Storage for needed XML attributes.
   * @param requirements Requirements to store.
   * @throws SAXException If an error occurs.
   */
  public static void write(AttributesImpl attrs, UsageRequirement requirements) throws SAXException
  {
    // Min level
    Integer minLevel=requirements.getMinLevel();
    if (minLevel!=null)
    {
      attrs.addAttribute("","",UsageRequirementXMLConstants.MIN_LEVEL_ATTR,XmlWriter.CDATA,minLevel.toString());
    }
    // Max level
    Integer maxLevel=requirements.getMaxLevel();
    if (maxLevel!=null)
    {
      attrs.addAttribute("","",UsageRequirementXMLConstants.MAX_LEVEL_ATTR,XmlWriter.CDATA,maxLevel.toString());
    }
    // Class requirement
    ClassRequirement classRequirement=requirements.getClassRequirement();
    if (classRequirement!=null)
    {
      String classReqStr=classRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_CLASSES_ATTR,XmlWriter.CDATA,classReqStr);
    }
    // Race requirement
    RaceRequirement raceRequirement=requirements.getRaceRequirement();
    if (raceRequirement!=null)
    {
      String raceReqStr=raceRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_RACES_ATTR,XmlWriter.CDATA,raceReqStr);
    }
  }
}
