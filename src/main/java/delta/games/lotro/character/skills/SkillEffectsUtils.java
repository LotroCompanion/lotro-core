package delta.games.lotro.character.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassSkill;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.skills.attack.SkillAttack;
import delta.games.lotro.character.skills.attack.SkillAttacks;
import delta.games.lotro.common.IdentifiableComparator;

/**
 * Utility methods related to skills.
 * @author DAM
 */
public class SkillEffectsUtils
{
  private static SkillEffectType[] TYPES={SkillEffectType.SELF_CRITICAL,SkillEffectType.TOGGLE,SkillEffectType.USER_TOGGLE,SkillEffectType.USER};

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
    ret.addAll(getSelfEffects(skill));
    return ret;
  }

  /**
   * Get all the 'self' effects for a skill.
   * @param skill Skill to use.
   * @return A list of effect generators.
   */
  public static List<SkillEffectGenerator> getSelfEffects(SkillDescription skill)
  {
    List<SkillEffectGenerator> ret=new ArrayList<SkillEffectGenerator>();
    for(SkillEffectType type : TYPES)
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

  /**
   * Get all the skills for character classes.
   * @return A list of skills, sorted by identifier.
   */
  public static List<SkillDescription> getSkillsForClasses()
  {
    Set<SkillDescription> set=new HashSet<SkillDescription>();
    ClassesManager mgr=ClassesManager.getInstance();
    for(ClassDescription cd : mgr.getAllCharacterClasses())
    {
      for(ClassSkill classSkill : cd.getSkills())
      {
        set.add(classSkill.getSkill());
      }
    }
    List<SkillDescription> ret=new ArrayList<SkillDescription>(set);
    Collections.sort(ret,new IdentifiableComparator<SkillDescription>());
    return ret;
  }
}
