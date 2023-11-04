package delta.games.lotro.lore.items.sets;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.effects.Effect2;
import delta.games.lotro.common.effects.EffectDisplay;
import delta.games.lotro.common.effects.EffectGenerator;

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
   * @return the displayable lines.
   */
  public List<String> buildSetEffectsDisplay(ItemsSet set, SetBonus bonus)
  {
    _level=getLevel(set);
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
    Effect2 effect2=generator.getEffect();
    Float spellcraft=generator.getSpellcraft();
    int level=_level;
    if (spellcraft!=null)
    {
      level=spellcraft.intValue();
    }
    showEffect(storage,effect2,level);
  }

  private void showEffect(List<String> storage, Effect2 effect, int level)
  {
    EffectDisplay display=new EffectDisplay(level);
    List<String> childStorage=new ArrayList<String>();
    display.displayEffect(childStorage,effect);
    if (!childStorage.isEmpty())
    {
      storage.addAll(childStorage);
    }
  }

  private int getLevel(ItemsSet set)
  {
    // May be use: set.useAverageItemLevelForSetLevel();
    return set.getSetLevel();
  }
}
