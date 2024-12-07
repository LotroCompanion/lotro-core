package delta.games.lotro.lore.items.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.skills.SkillDescription;
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
            EffectGenerator effectGenerator=onEquip[i];
            DisplayEffectsUtils.showEffectGenerator(childStorage,effectGenerator,true,_level);
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
            DisplayEffectsUtils.showEffectGenerator(childStorage,effect,false,_level);
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
    int level=_level;
    Integer skillLevel=skillToExecute.getLevel();
    if (skillLevel!=null)
    {
      level=skillLevel.intValue();
    }
    List<String> childStorage=SkillEffectsDisplay.showSkill(skill,level);
    if (!childStorage.isEmpty())
    {
      storage.add("On Use:");
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
