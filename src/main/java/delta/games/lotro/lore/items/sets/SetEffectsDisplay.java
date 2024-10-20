package delta.games.lotro.lore.items.sets;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.display.EffectDisplay;

/**
 * Build a displayable version of the effects of an items set.
 * @author DAM
 */
public class SetEffectsDisplay
{
  private int _level;

  /**
   * Build a displayable version of the effects of an items set.
   * @param set Set to use.
   * @param bonus Bonus to use.
   * @param level Level to use.
   * @return the displayable lines.
   */
  public List<String> buildSetEffectsDisplay(ItemsSet set, SetBonus bonus, int level)
  {
    _level=level;
    List<String> ret=new ArrayList<String>();
    ItemSetEffectsManager mgr=bonus.getEffects();
    if (mgr!=null)
    {
      EffectGenerator[] effects=mgr.getEffects();
      if (effects.length>0)
      {
        for(EffectGenerator effect : effects)
        {
          showEffectGenerator(ret,effect);
        }
      }
    }
    return ret;
  }

  private void showEffectGenerator(List<String> storage, EffectGenerator generator)
  {
    Effect effect2=generator.getEffect();
    Float spellcraft=generator.getSpellcraft();
    int level=_level;
    if (spellcraft!=null)
    {
      level=spellcraft.intValue();
    }
    showEffect(storage,effect2,level);
  }

  private void showEffect(List<String> storage, Effect effect, int level)
  {
    EffectDisplay display=new EffectDisplay(level);
    List<String> childStorage=new ArrayList<String>();
    display.displayEffect(childStorage,effect);
    if (!childStorage.isEmpty())
    {
      storage.addAll(childStorage);
    }
  }
}
