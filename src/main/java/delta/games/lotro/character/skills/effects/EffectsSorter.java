package delta.games.lotro.character.skills.effects;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.InstantFellowshipEffect;

/**
 * Utility methods to sort effects.
 * @author DAM
 */
public class EffectsSorter
{
  /**
   * Sort some generators.
   * @param effects Effects to sort.
   */
  public static void sortGenerators(List<SkillEffectGenerator> effects)
  {
    if (effects.size()>1)
    {
      Collections.sort(effects,buildComparator());
    }
  }

  private static Comparator<SkillEffectGenerator> buildComparator()
  {
    Comparator<SkillEffectGenerator> c=new Comparator<SkillEffectGenerator>()
    {
      @Override
      public int compare(SkillEffectGenerator o1, SkillEffectGenerator o2)
      {
        return compareEffects(o1.getEffect(),o2.getEffect());
      }
    };
    return c;
  }

  private static int compareEffects(Effect e1, Effect e2)
  {
    Class<? extends Effect> class1=e1.getClass();
    boolean isFellowship1=(class1==InstantFellowshipEffect.class);
    Class<? extends Effect> class2=e2.getClass();
    boolean isFellowship2=(class2==InstantFellowshipEffect.class);
    if (isFellowship1)
    {
      if (isFellowship2)
      {
        return 0;
      }
      return 1;
    }
    if (isFellowship2)
    {
      return -1;
    }
    return 0;
  }
}
