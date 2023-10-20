package delta.games.lotro.lore.items.effects;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.SkillEffectsManager;
import delta.games.lotro.common.effects.Effect2;
import delta.games.lotro.common.effects.EffectDisplay;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemUtils;
import delta.games.lotro.lore.items.details.SkillToExecute;
import delta.games.lotro.lore.items.effects.ItemEffectsManager.Type;

/**
 * Build a displayable version of the effects of an item.
 * @author DAM
 */
public class ItemEffectsDisplay
{
  private int _level;

  /**
   * Build a displayable version of the effects of an item.
   * @param item Item to use.
   * @return the displayable lines.
   */
  public String buildItemEffectsDisplay(Item item)
  {
    _level=getLevel(item);
    StringBuilder sb=new StringBuilder();
    // Show effects
    ItemEffectsManager mgr=item.getEffects();
    if (mgr!=null)
    {
      if (mgr.hasOnEquipEffects())
      {
        EffectGenerator[] onEquip=mgr.getEffects(Type.ON_EQUIP);
        if (onEquip.length>0)
        {
          sb.append("On equip:").append(EndOfLine.NATIVE_EOL);
          for(EffectGenerator effect : onEquip)
          {
            showEffectGenerator(sb,effect);
          }
        }
      }
      if (mgr.hasOnUseEffects())
      {
        EffectGenerator[] onUse=mgr.getEffects(Type.ON_USE);
        if (onUse.length>0)
        {
          sb.append("On use:").append(EndOfLine.NATIVE_EOL);
          for(EffectGenerator effect : onUse)
          {
            showEffectGenerator(sb,effect);
          }
        }
      }
    }
    // Skill
    SkillToExecute skillToExecute=ItemUtils.getDetail(item,SkillToExecute.class);
    if (skillToExecute!=null)
    {
      showSkill(sb,skillToExecute);
    }
    return sb.toString().trim();
  }

  private void showSkill(StringBuilder sb,SkillToExecute skillToExecute)
  {
    SkillDescription skill=skillToExecute.getSkill();
    SkillEffectsManager mgr=skill.getEffects();
    if (mgr==null)
    {
      return;
    }
    int levelBackup=_level;
    Integer skillLevel=skillToExecute.getLevel();
    if (skillLevel!=null)
    {
      _level=skillLevel.intValue();
    }
    SkillEffectGenerator[] effectGenerators=mgr.getEffects();
    for(SkillEffectGenerator effectGenerator : effectGenerators)
    {
      showEffectGenerator(sb,effectGenerator);
    }
    _level=levelBackup;
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
      sb2.append(sb.toString().trim()).append(EndOfLine.NATIVE_EOL);
    }
  }

  private int getLevel(Item item)
  {
    int level=1;
    Integer itemLevel=item.getItemLevel();
    if (itemLevel!=null)
    {
      level=itemLevel.intValue();
    }
    return level;
  }
}
