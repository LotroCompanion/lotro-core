package delta.games.lotro.lore.items.sets;

import delta.common.utils.text.EndOfLine;
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
  public String buildSetEffectsDisplay(ItemsSet set, SetBonus bonus)
  {
    _level=getLevel(set);
    StringBuilder sb=new StringBuilder();
    ItemSetEffectsManager mgr=bonus.getEffects();
    if (mgr!=null)
    {
      EffectGenerator[] effects=mgr.getEffects();
      if (effects.length>0)
      {
        for(EffectGenerator effect : effects)
        {
          showEffectGenerator(sb,effect);
        }
      }
    }
    return sb.toString().trim();
  }

  private void showEffectGenerator(StringBuilder sb, EffectGenerator generator)
  {
    Effect2 effect2=generator.getEffect();
    Float spellcraft=generator.getSpellcraft();
    int level=_level;
    if (spellcraft!=null)
    {
      level=spellcraft.intValue();
    }
    showEffect(sb,effect2,level);
  }

  private void showEffect(StringBuilder sb, Effect2 effect, int level)
  {
    EffectDisplay display=new EffectDisplay(level);
    StringBuilder sb2=new StringBuilder();
    display.displayEffect(sb2,effect);
    if (sb2.length()>0)
    {
      sb.append(sb2.toString().trim()).append(EndOfLine.NATIVE_EOL);
    }
  }

  private int getLevel(ItemsSet set)
  {
    // May be use: set.useAverageItemLevelForSetLevel();
    return set.getSetLevel();
  }
}
