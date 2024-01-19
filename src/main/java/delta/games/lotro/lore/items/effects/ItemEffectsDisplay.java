package delta.games.lotro.lore.items.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.SkillEffectType;
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
  public List<String> buildItemEffectsDisplay(Item item)
  {
    _level=getLevel(item);
    List<String> ret=new ArrayList<String>();
    // Show effects
    ItemEffectsManager mgr=item.getEffects();
    if (mgr!=null)
    {
      if (mgr.hasOnEquipEffects())
      {
        EffectGenerator[] onEquip=mgr.getEffects(Type.ON_EQUIP);
        int nbOnEquip=onEquip.length;
        if (nbOnEquip>0)
        {
          List<String> childStorage=new ArrayList<String>();
          for(int i=nbOnEquip-1;i>=0;i--)
          {
            EffectGenerator effect=onEquip[i];
            showEffectGenerator(childStorage,effect,true);
          }
          if (!childStorage.isEmpty())
          {
            ret.addAll(childStorage);
          }
        }
      }
      if (mgr.hasOnUseEffects())
      {
        EffectGenerator[] onUse=mgr.getEffects(Type.ON_USE);
        int nbOnUse=onUse.length;
        if (nbOnUse>0)
        {
          List<String> childStorage=new ArrayList<String>();
          for(int i=nbOnUse-1;i>=0;i--)
          {
            EffectGenerator effect=onUse[i];
            showEffectGenerator(childStorage,effect,false);
          }
          if (!childStorage.isEmpty())
          {
            ret.add("On use:");
            ret.addAll(childStorage);
          }
        }
      }
    }
    // Skill
    SkillToExecute skillToExecute=ItemUtils.getDetail(item,SkillToExecute.class);
    if (skillToExecute!=null)
    {
      showSkill(ret,skillToExecute);
    }
    return ret;
  }

  private void showSkill(List<String> storage, SkillToExecute skillToExecute)
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
    List<String> childStorage=new ArrayList<String>();
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
          showEffectGenerator(childStorage,effectGenerator,false);
          usedEffectIds.add(effectId);
        }
      }
    }
    if (!childStorage.isEmpty())
    {
      storage.add("On Use:");
      storage.addAll(childStorage);
    }
    _level=levelBackup;
  }

  private void showEffectGenerator(List<String> storage, EffectGenerator generator, boolean skipRawStats)
  {
    Effect2 effect=generator.getEffect();
    Float spellcraft=generator.getSpellcraft();
    int level=_level;
    if (spellcraft!=null)
    {
      level=spellcraft.intValue();
    }
    showEffect(storage,effect,level,skipRawStats);
  }

  private void showEffect(List<String> storage, Effect2 effect, int level, boolean skipRawStats)
  {
    EffectDisplay display=new EffectDisplay(level);
    display.skipRawStats(skipRawStats);
    List<String> childStorage=new ArrayList<String>();
    display.displayEffect(childStorage,effect);
    if (!childStorage.isEmpty())
    {
      storage.addAll(childStorage);
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
