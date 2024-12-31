package delta.games.lotro.lore.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.SkillEffectsUtils;
import delta.games.lotro.common.action.ActionEntry;
import delta.games.lotro.common.action.ActionTable;
import delta.games.lotro.common.action.ActionTableEntry;
import delta.games.lotro.common.action.ActionTables;
import delta.games.lotro.common.action.ActionTablesEntry;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.lore.agents.mobs.MobDescription;

/**
 * Utility methods about effects.
 * @author DAM
 */
public class EffectUtils
{
  /**
   * Get the effects for a skill.
   * @param skill Skill to use.
   * @return A set of effect identifiers.
   */
  public static Set<Integer> getEffectsFromSkill(SkillDescription skill)
  {
    return buildFromGenerators(SkillEffectsUtils.getAllEffects(skill));
  }

  /**
   * Get the effects for a skill.
   * @param skill Skill to use.
   * @return A set of effect identifiers.
   */
  public static Set<Integer> getSelfEffectsFromSkill(SkillDescription skill)
  {
    return buildFromGenerators(SkillEffectsUtils.getSelfEffects(skill));
  }

  /**
   * Get the attack effects for a skill.
   * @param skill Skill to use.
   * @return A set of effect identifiers.
   */
  public static Set<Integer> getAttackEffectsFromSkill(SkillDescription skill)
  {
    return buildFromGenerators(SkillEffectsUtils.getAllAttackEffects(skill));
  }

  private static Set<Integer> buildFromGenerators(List<SkillEffectGenerator> generators)
  {
    Set<Integer> ret=new HashSet<Integer>();
    for(SkillEffectGenerator generator : generators)
    {
      Effect effect=generator.getEffect();
      Integer key=Integer.valueOf(effect.getIdentifier());
      ret.add(key);
    }
    return ret;
  }

  /**
   * Get the effects for a skill.
   * @param mob Mob to use.
   * @return A set of effect identifiers.
   */
  public static Set<Integer> getEffectsFromMob(MobDescription mob)
  {
    Set<Integer> ret=new HashSet<Integer>();
    ActionTables actionTables=mob.getActionTables();
    if (actionTables!=null)
    {
      for(ActionTablesEntry actionTablesEentry : actionTables.getEntries())
      {
        ActionTable actionTable=actionTablesEentry.getTable();
        for(ActionTableEntry actionTableEntry : actionTable.getEntries())
        {
          for(ActionEntry actionEntry : actionTableEntry.getActionsChain())
          {
            SkillDescription skill=actionEntry.getSkill();
            ret.addAll(getEffectsFromSkill(skill));
          }
        }
      }
    }
    return ret;
  }
}
