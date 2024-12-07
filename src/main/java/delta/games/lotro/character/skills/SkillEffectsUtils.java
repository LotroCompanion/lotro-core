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
    SkillDetails details=skill.getDetails();
    SkillAttacks attacks=details.getAttacks();
    if (attacks!=null)
    {
      for(SkillAttack attack : attacks.getAttacks())
      {
        SkillEffectsManager attackEffects=attack.getEffects();
        if (attackEffects!=null)
        {
          SingleTypeSkillEffectsManager attackEffectsMgr=attackEffects.getEffects(SkillEffectType.ATTACK);
          if (attackEffectsMgr!=null)
          {
            ret.addAll(attackEffectsMgr.getEffects());
          }
        }
      }
    }
    SkillEffectType[] types={SkillEffectType.TOGGLE,SkillEffectType.USER_TOGGLE,SkillEffectType.USER};
    SkillEffectsManager allEffectsMgr=details.getEffects();
    if (allEffectsMgr!=null)
    {
      for(SkillEffectType type : types)
      {
        SingleTypeSkillEffectsManager effectsMgr=allEffectsMgr.getEffects(type);
        if (effectsMgr!=null)
        {
          ret.addAll(effectsMgr.getEffects());
        }
      }
    }
    return ret;
  }
}
