package delta.games.lotro.character.skills;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.skills.attack.SkillAttack;
import delta.games.lotro.character.skills.attack.SkillAttacks;

/**
 * Utility methods related to skills.
 * @author DAM
 */
public class SkillEffectsUtils
{
  /**
   * Get the effects for a skill.
   * @param skill Skill to use.
   * @return A list of effect generators.
   */
  public static List<SkillEffectGenerator> getEffects(SkillDescription skill)
  {
    List<SkillEffectGenerator> ret=new ArrayList<SkillEffectGenerator>();
    ret.addAll(getAttackEffects(skill,SkillEffectType.ATTACK));
    SkillEffectType[] types={SkillEffectType.TOGGLE,SkillEffectType.USER_TOGGLE,SkillEffectType.USER};
    for(SkillEffectType type : types)
    {
      ret.addAll(getEffects(skill,type));
    }
    return ret;
  }

  /**
   * Get all the effects for a skill.
   * @param skill Skill to use.
   * @return A list of effect generators.
   */
  public static List<SkillEffectGenerator> getAllEffects(SkillDescription skill)
  {
    List<SkillEffectGenerator> ret=new ArrayList<SkillEffectGenerator>();
    ret.addAll(getAllAttackEffects(skill));
    SkillEffectType[] types={SkillEffectType.SELF_CRITICAL,SkillEffectType.TOGGLE,SkillEffectType.USER_TOGGLE,SkillEffectType.USER};
    for(SkillEffectType type : types)
    {
      ret.addAll(getEffects(skill,type));
    }
    return ret;
  }

  /**
   * Get all the attack effects for a skill.
   * @param skill Skill to use.
   * @return A list of effect generators.
   */
  public static List<SkillEffectGenerator> getAllAttackEffects(SkillDescription skill)
  {
    List<SkillEffectGenerator> ret=new ArrayList<SkillEffectGenerator>();
    SkillDetails details=skill.getDetails();
    SkillAttacks attacks=details.getAttacks();
    if (attacks!=null)
    {
      for(SkillAttack attack : attacks.getAttacks())
      {
        SkillEffectsManager effectsMgr=attack.getEffects();
        if (effectsMgr!=null)
        {
          for(SingleTypeSkillEffectsManager mgr : effectsMgr.getAll())
          {
            ret.addAll(mgr.getEffects());
          }
        }
      }
    }
    return ret;
  }

  /**
   * Get the attack effects for a skill and a given type.
   * @param skill Skill to use.
   * @param type Effect type.
   * @return A list of effect generators.
   */
  public static List<SkillEffectGenerator> getAttackEffects(SkillDescription skill, SkillEffectType type)
  {
    List<SkillEffectGenerator> ret=new ArrayList<SkillEffectGenerator>();
    SkillDetails details=skill.getDetails();
    SkillAttacks attacks=details.getAttacks();
    if (attacks!=null)
    {
      for(SkillAttack attack : attacks.getAttacks())
      {
        SkillEffectsManager attackEffects=attack.getEffects();
        if (attackEffects!=null)
        {
          SingleTypeSkillEffectsManager attackEffectsMgr=attackEffects.getEffects(type);
          if (attackEffectsMgr!=null)
          {
            ret.addAll(attackEffectsMgr.getEffects());
          }
        }
      }
    }
    return ret;
  }

  /**
   * Get the skill effects for a skill and a given type.
   * @param skill Skill to use.
   * @param type Effect type.
   * @return A list of effect generators.
   */
  public static List<SkillEffectGenerator> getEffects(SkillDescription skill, SkillEffectType type)
  {
    SkillDetails details=skill.getDetails();
    SkillEffectsManager allEffectsMgr=details.getEffects();
    List<SkillEffectGenerator> ret=new ArrayList<SkillEffectGenerator>();
    if (allEffectsMgr!=null)
    {
      SingleTypeSkillEffectsManager effectsMgr=allEffectsMgr.getEffects(type);
      if (effectsMgr!=null)
      {
        ret.addAll(effectsMgr.getEffects());
      }
    }
    return ret;
  }
}
