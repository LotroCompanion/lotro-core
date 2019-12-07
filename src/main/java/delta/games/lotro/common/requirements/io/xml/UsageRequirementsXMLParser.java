package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.requirements.ClassRequirement;
import delta.games.lotro.common.requirements.RaceRequirement;
import delta.games.lotro.common.requirements.UsageRequirement;

/**
 * Read usage requirements from XML documents.
 * @author DAM
 */
public class UsageRequirementsXMLParser
{
  /**
   * Load usage requirements from an XML tag.
   * @param requirements Storage for loaded data.
   * @param tag Tag to use.
   */
  public static void parseRequirements(UsageRequirement requirements, Element tag)
  {
    NamedNodeMap attrs=tag.getAttributes();
    // Minimum level
    int minimumLevel=DOMParsingTools.getIntAttribute(attrs,UsageRequirementXMLConstants.MIN_LEVEL_ATTR,-1);
    if (minimumLevel!=-1)
    {
      requirements.setMinLevel(Integer.valueOf(minimumLevel));
    }
    // Maximum level
    int maximumLevel=DOMParsingTools.getIntAttribute(attrs,UsageRequirementXMLConstants.MAX_LEVEL_ATTR,-1);
    if (maximumLevel!=-1)
    {
      requirements.setMaxLevel(Integer.valueOf(maximumLevel));
    }
    // Required classes
    String classKeys=DOMParsingTools.getStringAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_CLASS_ATTR,null);
    ClassRequirement classRequirement=ClassRequirement.fromString(classKeys);
    requirements.setClassRequirement(classRequirement);
    // Required races
    String raceKeys=DOMParsingTools.getStringAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_RACES_ATTR,null);
    RaceRequirement raceRequirement=RaceRequirement.fromString(raceKeys);
    requirements.setRaceRequirement(raceRequirement);
  }
}
