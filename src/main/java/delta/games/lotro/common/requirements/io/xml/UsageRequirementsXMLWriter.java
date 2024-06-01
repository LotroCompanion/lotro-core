package delta.games.lotro.common.requirements.io.xml;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
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
 * Writes usage requirements to XML documents.
 * @author DAM
 */
public class UsageRequirementsXMLWriter
{
  /**
   * Write a usage requirement to the given XML stream.
   * @param attrs Storage for needed XML attributes.
   * @param requirements Requirements to store.
   */
  public static void write(AttributesImpl attrs, UsageRequirement requirements)
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
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_CLASS_ATTR,XmlWriter.CDATA,classReqStr);
    }
    // Race requirement
    RaceRequirement raceRequirement=requirements.getRaceRequirement();
    if (raceRequirement!=null)
    {
      String raceReqStr=raceRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_RACE_ATTR,XmlWriter.CDATA,raceReqStr);
    }
    // Faction requirement
    FactionRequirement factionRequirement=requirements.getFactionRequirement();
    if (factionRequirement!=null)
    {
      String factionReqStr=factionRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_FACTION_ATTR,XmlWriter.CDATA,factionReqStr);
    }
    // Quest requirement
    QuestRequirement questRequirement=requirements.getQuestRequirement();
    if (questRequirement!=null)
    {
      String questReqStr=questRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_QUEST_ATTR,XmlWriter.CDATA,questReqStr);
    }
    // Profession requirement
    ProfessionRequirement professionRequirement=requirements.getProfessionRequirement();
    if (professionRequirement!=null)
    {
      String professionReqStr=professionRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_PROFESSION_ATTR,XmlWriter.CDATA,professionReqStr);
    }
    // Glory rank requirement
    GloryRankRequirement gloryRankRequirement=requirements.getGloryRankRequirement();
    if (gloryRankRequirement!=null)
    {
      int rank=gloryRankRequirement.getRank();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_GLORY_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
    }
    // Effect requirement
    EffectRequirement effectRequirement=requirements.getEffectRequirement();
    if (effectRequirement!=null)
    {
      int effectID=effectRequirement.getEffect().getIdentifier();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_EFFECT_ATTR,XmlWriter.CDATA,String.valueOf(effectID));
    }
    // Trait requirement
    TraitRequirement traitRequirement=requirements.getTraitRequirement();
    if (traitRequirement!=null)
    {
      int traitID=traitRequirement.getTrait().getIdentifier();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_TRAIT_ATTR,XmlWriter.CDATA,String.valueOf(traitID));
    }
  }
}
