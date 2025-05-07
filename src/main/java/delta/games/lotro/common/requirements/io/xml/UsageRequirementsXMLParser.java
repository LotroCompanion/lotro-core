package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;

import delta.common.utils.NumericTools;
import delta.common.utils.xml.DOMParsingTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectsManager;
import delta.games.lotro.common.requirements.ClassRequirement;
import delta.games.lotro.common.requirements.EffectRequirement;
import delta.games.lotro.common.requirements.FactionRequirement;
import delta.games.lotro.common.requirements.GloryRankRequirement;
import delta.games.lotro.common.requirements.ProfessionRequirement;
import delta.games.lotro.common.requirements.QuestRequirement;
import delta.games.lotro.common.requirements.RaceRequirement;
import delta.games.lotro.common.requirements.TraitRequirement;
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
    // Level
    Integer minimumLevel=DOMParsingTools.getIntegerAttribute(attrs,UsageRequirementXMLConstants.MIN_LEVEL_ATTR,null);
    Integer maximumLevel=DOMParsingTools.getIntegerAttribute(attrs,UsageRequirementXMLConstants.MAX_LEVEL_ATTR,null);
    requirements.setLevelRange(minimumLevel,maximumLevel);
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
    // Required profession
    String professionReqStr=DOMParsingTools.getStringAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_PROFESSION_ATTR,null);
    ProfessionRequirement professionRequirement=ProfessionRequirement.fromString(professionReqStr);
    requirements.setProfessionRequirement(professionRequirement);
  }

  /**
   * Parse requirements (SAX mode).
   * @param requirements Storage for loaded data.
   * @param attributes Input data.
   */
  public static void parseRequirements(UsageRequirement requirements, Attributes attributes)
  {
    // Level range
    Integer minLevel=SAXParsingTools.getIntegerAttribute(attributes,UsageRequirementXMLConstants.MIN_LEVEL_ATTR,null);
    Integer maxLevel=SAXParsingTools.getIntegerAttribute(attributes,UsageRequirementXMLConstants.MAX_LEVEL_ATTR,null);
    requirements.setLevelRange(minLevel,maxLevel);
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
    // Required profession
    String requiredProfession=attributes.getValue(UsageRequirementXMLConstants.REQUIRED_PROFESSION_ATTR);
    ProfessionRequirement professionRequirement=ProfessionRequirement.fromString(requiredProfession);
    requirements.setProfessionRequirement(professionRequirement);
    // Required glory rank
    String requiredGloryRankStr=attributes.getValue(UsageRequirementXMLConstants.REQUIRED_GLORY_RANK_ATTR);
    if (requiredGloryRankStr!=null)
    {
      int rank=NumericTools.parseInt(requiredGloryRankStr,0);
      GloryRankRequirement gloryRankRequirement=new GloryRankRequirement();
      gloryRankRequirement.setRank(rank);
      requirements.setGloryRankRequirement(gloryRankRequirement);
    }
    // Required effect
    String effectIDStr=attributes.getValue(UsageRequirementXMLConstants.REQUIRED_EFFECT_ATTR);
    if (effectIDStr!=null)
    {
      int effectID=NumericTools.parseInt(effectIDStr,0);
      Effect effect=EffectsManager.getInstance().getEffectById(effectID);
      if (effect!=null)
      {
        EffectRequirement effectRequirement=new EffectRequirement(effect);
        requirements.setEffectRequirement(effectRequirement);
      }
    }
    // Required trait
    String traitIDStr=attributes.getValue(UsageRequirementXMLConstants.REQUIRED_TRAIT_ATTR);
    if (traitIDStr!=null)
    {
      int traitID=NumericTools.parseInt(traitIDStr,0);
      TraitDescription trait=TraitsManager.getInstance().getTrait(traitID);
      if (trait!=null)
      {
        TraitRequirement traitRequirement=new TraitRequirement(trait);
        requirements.setTraitRequirement(traitRequirement);
      }
    }
  }
}
