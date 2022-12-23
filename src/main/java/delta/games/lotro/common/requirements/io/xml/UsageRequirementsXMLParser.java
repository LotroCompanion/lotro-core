package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;

import delta.common.utils.NumericTools;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.requirements.ClassRequirement;
import delta.games.lotro.common.requirements.FactionRequirement;
import delta.games.lotro.common.requirements.QuestRequirement;
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
    String requiredClasses=DOMParsingTools.getStringAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_CLASS_ATTR,null);
    ClassRequirement classRequirement=ClassRequirement.fromString(requiredClasses);
    requirements.setClassRequirement(classRequirement);
    // Required races
    String requiredRaces=DOMParsingTools.getStringAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_RACE_ATTR,null);
    RaceRequirement raceRequirement=RaceRequirement.fromString(requiredRaces);
    requirements.setRaceRequirement(raceRequirement);
    // Required faction
    String requiredFaction=DOMParsingTools.getStringAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_FACTION_ATTR,null);
    FactionRequirement factionRequirement=FactionRequirement.fromString(requiredFaction);
    requirements.setFactionRequirement(factionRequirement);
    // Required quest
    String questReqStr=DOMParsingTools.getStringAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_QUEST_ATTR,null);
    QuestRequirement questRequirement=QuestRequirement.fromString(questReqStr);
    requirements.setQuestRequirement(questRequirement);
  }

  /**
   * Parse requirements (SAX mode).
   * @param requirements Storage for loaded data.
   * @param attributes Input data.
   */
  public static void parseRequirements(UsageRequirement requirements, Attributes attributes)
  {
    // Minimum level
    String minimumLevelStr=attributes.getValue(UsageRequirementXMLConstants.MIN_LEVEL_ATTR);
    if (minimumLevelStr!=null)
    {
      requirements.setMinLevel(NumericTools.parseInteger(minimumLevelStr));
    }
    // Maximum level
    String maximumLevelStr=attributes.getValue(UsageRequirementXMLConstants.MAX_LEVEL_ATTR);
    if (maximumLevelStr!=null)
    {
      requirements.setMaxLevel(NumericTools.parseInteger(maximumLevelStr));
    }
    // Required classes
    String requiredClasses=attributes.getValue(UsageRequirementXMLConstants.REQUIRED_CLASS_ATTR);
    ClassRequirement classRequirements=ClassRequirement.fromString(requiredClasses);
    requirements.setClassRequirement(classRequirements);
    // Required races
    String requiredRaces=attributes.getValue(UsageRequirementXMLConstants.REQUIRED_RACE_ATTR);
    RaceRequirement raceRequirements=RaceRequirement.fromString(requiredRaces);
    requirements.setRaceRequirement(raceRequirements);
    // Required faction
    String requiredFaction=attributes.getValue(UsageRequirementXMLConstants.REQUIRED_FACTION_ATTR);
    FactionRequirement factionRequirement=FactionRequirement.fromString(requiredFaction);
    requirements.setFactionRequirement(factionRequirement);
  }
}
