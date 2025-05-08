package delta.games.lotro.common.requirements.io.xml;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.requirements.ClassRequirement;
import delta.games.lotro.common.requirements.EffectRequirement;
import delta.games.lotro.common.requirements.FactionRequirement;
import delta.games.lotro.common.requirements.GloryRankRequirement;
import delta.games.lotro.common.requirements.LevelRangeRequirement;
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
    // Level requirement
    LevelRangeRequirement requirement=requirements.getLevelRequirement();
    write(attrs,requirement);
    // Class requirement
    ClassRequirement classRequirement=requirements.getClassRequirement();
    write(attrs,classRequirement);
    // Race requirement
    RaceRequirement raceRequirement=requirements.getRaceRequirement();
    write(attrs,raceRequirement);
    // Faction requirement
    FactionRequirement factionRequirement=requirements.getFactionRequirement();
    write(attrs,factionRequirement);
    // Quest requirement
    QuestRequirement questRequirement=requirements.getQuestRequirement();
    write(attrs,questRequirement);
    // Profession requirement
    ProfessionRequirement professionRequirement=requirements.getProfessionRequirement();
    write(attrs,professionRequirement);
    // Glory rank requirement
    GloryRankRequirement gloryRankRequirement=requirements.getGloryRankRequirement();
    write(attrs,gloryRankRequirement);
    // Effect requirement
    EffectRequirement effectRequirement=requirements.getEffectRequirement();
    write(attrs,effectRequirement);
    // Trait requirement
    TraitRequirement traitRequirement=requirements.getTraitRequirement();
    write(attrs,traitRequirement);
  }

  /**
   * Write a level requirement.
   * @param attrs Storage.
   * @param levelRequirement Requirement.
   */
  public static void write(AttributesImpl attrs, LevelRangeRequirement levelRequirement)
  {
    if (levelRequirement!=null)
    {
      // Min level
      Integer minLevel=levelRequirement.getMinLevel();
      if (minLevel!=null)
      {
        attrs.addAttribute("","",UsageRequirementXMLConstants.MIN_LEVEL_ATTR,XmlWriter.CDATA,minLevel.toString());
      }
      // Max level
      Integer maxLevel=levelRequirement.getMaxLevel();
      if (maxLevel!=null)
      {
        attrs.addAttribute("","",UsageRequirementXMLConstants.MAX_LEVEL_ATTR,XmlWriter.CDATA,maxLevel.toString());
      }
    }
  }

  /**
   * Write a class requirement.
   * @param attrs Storage.
   * @param classRequirement Requirement.
   */
  public static void write(AttributesImpl attrs, ClassRequirement classRequirement)
  {
    if (classRequirement!=null)
    {
      String classReqStr=classRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_CLASS_ATTR,XmlWriter.CDATA,classReqStr);
    }
  }

  /**
   * Write a race requirement.
   * @param attrs Storage.
   * @param raceRequirement Requirement.
   */
  public static void write(AttributesImpl attrs, RaceRequirement raceRequirement)
  {
    if (raceRequirement!=null)
    {
      String raceReqStr=raceRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_RACE_ATTR,XmlWriter.CDATA,raceReqStr);
    }
  }

  /**
   * Write a faction requirement.
   * @param attrs Storage.
   * @param factionRequirement Requirement.
   */
  public static void write(AttributesImpl attrs, FactionRequirement factionRequirement)
  {
    if (factionRequirement!=null)
    {
      String factionReqStr=factionRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_FACTION_ATTR,XmlWriter.CDATA,factionReqStr);
    }
  }

  /**
   * Write a quest requirement.
   * @param attrs Storage.
   * @param questRequirement Requirement.
   */
  public static void write(AttributesImpl attrs, QuestRequirement questRequirement)
  {
    if (questRequirement!=null)
    {
      String questReqStr=questRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_QUEST_ATTR,XmlWriter.CDATA,questReqStr);
    }
  }

  /**
   * Write a crafting requirement.
   * @param attrs Storage.
   * @param professionRequirement Requirement.
   */
  public static void write(AttributesImpl attrs, ProfessionRequirement professionRequirement)
  {
    if (professionRequirement!=null)
    {
      String professionReqStr=professionRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_PROFESSION_ATTR,XmlWriter.CDATA,professionReqStr);
    }
  }

  /**
   * Write a glory rank requirement.
   * @param attrs Storage.
   * @param gloryRankRequirement Requirement.
   */
  public static void write(AttributesImpl attrs, GloryRankRequirement gloryRankRequirement)
  {
    if (gloryRankRequirement!=null)
    {
      int rank=gloryRankRequirement.getRank();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_GLORY_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
    }
  }

  /**
   * Write an effect requirement.
   * @param attrs Storage.
   * @param effectRequirement Requirement.
   */
  public static void write(AttributesImpl attrs, EffectRequirement effectRequirement)
  {
    if (effectRequirement!=null)
    {
      int effectID=effectRequirement.getEffect().getIdentifier();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_EFFECT_ATTR,XmlWriter.CDATA,String.valueOf(effectID));
    }
  }

  /**
   * Write a trait requirement.
   * @param attrs Storage.
   * @param traitRequirement Requirement.
   */
  public static void write(AttributesImpl attrs, TraitRequirement traitRequirement)
  {
    if (traitRequirement!=null)
    {
      int traitID=traitRequirement.getTrait().getIdentifier();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_TRAIT_ATTR,XmlWriter.CDATA,String.valueOf(traitID));
    }
  }
}
