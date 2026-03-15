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
import delta.games.lotro.common.effects.EffectsManager;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.ImplementUsageTypes;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.values.StructValue;

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

  /**
   * Decode an effect generator from a generic struct value.
   * @param structValue Input struct.
   * @return A effect generator or <code>null</code> if no such generator found.
   */
  public static SkillEffectGenerator decodeEffect(StructValue structValue)
  {
    Integer skillEffectID=(Integer)structValue.getValue("Skill_Effect");
    if ((skillEffectID==null) || (skillEffectID.intValue()==0))
    {
      return null;
    }
    Effect effect=EffectsManager.getInstance().getEffectById(skillEffectID.intValue());
    if (effect==null)
    {
      return null;
    }
    // Spellcraft
    Float spellcraft=null;
    SkillEffectGenerator generator=new SkillEffectGenerator(effect,spellcraft,null);
    // Implement usage
    Integer implementUsageCode=(Integer)structValue.getValue("Skill_EffectImplementUsage");
    if ((implementUsageCode!=null) && (implementUsageCode.intValue()>0))
    {
      ImplementUsageType implementUsage=ImplementUsageTypes.getByCode(implementUsageCode.intValue());
      generator.setImplementUsage(implementUsage);
    }
    return generator;
  }
}
