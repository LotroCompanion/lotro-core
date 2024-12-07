package delta.games.lotro.lore.items.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.SkillEffectsUtils;

/**
 * Utilities to display skill effects.
 * @author DAM
 */
public class SkillEffectsDisplay
{
  /**
   * Get display strings for the effects of a skill.
   * @param skill Skill to use.
   * @param level Level of skill.
   * @return A list of displayable strings.
   */
  public static List<String> showSkill(SkillDescription skill, int level)
  {
    List<String> ret=new ArrayList<String>();
    List<SkillEffectGenerator> effectGenerators=SkillEffectsUtils.getEffects(skill);
    int nbEffects=effectGenerators.size();
    if (nbEffects>0)
    {
      Set<Integer> usedEffectIds=new HashSet<Integer>();
      // Avoid using the same ID multiple times
      for(int i=nbEffects-1;i>=0;i--)
      {
        SkillEffectGenerator effectGenerator=effectGenerators.get(i);
        Integer effectId=Integer.valueOf(effectGenerator.getEffect().getIdentifier());
        if (!usedEffectIds.contains(effectId))
        {
          DisplayEffectsUtils.showEffectGenerator(ret,effectGenerator,false,level);
          usedEffectIds.add(effectId);
        }
      }
    }
    return ret;
  }
}
