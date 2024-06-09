package delta.games.lotro.lore.items.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.SkillEffectsManager;

/**
 * @author dm
 */
public class SkillEffectsDisplay
{
  public static List<String> showSkill(SkillDescription skill, int level)
  {
    List<String> ret=new ArrayList<String>();
    SkillEffectsManager mgr=skill.getEffects();
    if (mgr==null)
    {
      return ret;
    }
    SkillEffectGenerator[] effectGenerators=mgr.getEffects();
    int nbEffects=effectGenerators.length;
    if (nbEffects>0)
    {
      Set<Integer> usedEffectIds=new HashSet<Integer>();
      // Avoid using the same ID multiple times
      for(int i=nbEffects-1;i>=0;i--)
      {
        SkillEffectGenerator effectGenerator=effectGenerators[i];
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
