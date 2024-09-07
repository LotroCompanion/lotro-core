package delta.games.lotro.character.skills;

import java.util.Set;

import delta.games.lotro.character.skills.attack.SkillAttacks;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.DamageQualifiers;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.inductions.Induction;

/**
 * @author dmorcellet
 */
public class SkillDetails
{
  // Duration, induction, channeling
  private Float _actionDurationContribution;
  private Induction _induction;
  private Float _channelingDuration;
  // Cooldown
  private Float _cooldown;
  // Flags
  private int _flags;
  private SkillGeometry _geometry;
  // Max target
  private Integer _maxTargets; // From Skill_AreaEffectMaxTargets
  // + Mod Array // From Skill_AreaEffectMaxTargets_Mod_Array

  private Integer _toggleHookNumber; // ?? From Skill_Toggle_Hook_Number
  private ResistCategory _resistCategory;
  private Set<Object> _displayTypes; // TODO enum 587203540

  // Effects: see SkillEffectsManager
  // 4 lists: critical, toggle, toggle user, user effect

  private SkillCostData _cost;
  private SkillPipData _pipData;
  private SkillGambitData _gambitData;
  private SkillAttacks _attacks;

  public DamageQualifier getDamagerQualifier()
  {
    return DamageQualifiers.MELEE;
  }

  public Float getActionDurationContribution()
  {
    // From Skill_ActionDurationContribution
    return null;
  }

  public Integer getInductionActionID()
  {
    return null;
  }

}
