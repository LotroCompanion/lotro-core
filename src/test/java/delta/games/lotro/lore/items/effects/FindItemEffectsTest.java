package delta.games.lotro.lore.items.effects;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import delta.common.utils.statistics.ValueSetStatistics;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.SkillEffectsUtils;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.ReactiveVitalEffect;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.items.details.SkillToExecute;
import delta.games.lotro.lore.items.effects.ItemEffectsManager.Type;

/**
 * Test/tool to find item effects and have some statistics about them.
 * @author DAM
 */
class FindItemEffectsTest
{
  private ValueSetStatistics _stats;

  @Test
  void inspectItemEffects()
  {
    _stats=new ValueSetStatistics();
    for(Item item : ItemsManager.getInstance().getAllItems())
    {
      inspectItem(item);
    }
    System.out.println(_stats.dumpByOccurrence());
  }

  private void inspectItem(Item item)
  {
    List<Effect> effects=getItemEffects(item);
    _stats.addValue(effects.size());
    if (effects.size()>0)
    {
      System.out.println("Item: "+item);
      for(Effect effect : effects)
      {
        System.out.println("\t"+effect);
      }
    }
  }

  private List<Effect> getItemEffects(Item item)
  {
    List<Effect> ret=new ArrayList<Effect>();
    ItemEffectsManager itemEffectsMgr=item.getEffects();
    if ((itemEffectsMgr!=null) && (itemEffectsMgr.hasEffects()))
    {
      for(Type type : ItemEffectsManager.Type.values())
      {
        EffectGenerator[] generators=itemEffectsMgr.getEffects(type);
        for(EffectGenerator generator : generators)
        {
          handleEffect(generator.getEffect(),ret);
        }
      }
    }

    ItemDetailsManager detailsMgr=item.getDetails();
    if (detailsMgr!=null)
    {
      List<SkillToExecute> skills=item.getDetails().getItemDetails(SkillToExecute.class);
      if (!skills.isEmpty())
      {
        for(SkillToExecute skillToExecute : skills)
        {
          List<SkillEffectGenerator> generators=SkillEffectsUtils.getAllEffects(skillToExecute.getSkill());
          if (!generators.isEmpty())
          {
            for(SkillEffectGenerator generator : generators)
            {
              handleEffect(generator.getEffect(),ret);
            }
          }
        }
      }
    }
    return ret;
  }

  private void handleEffect(Effect e, List<Effect> list)
  {
    if (e instanceof ReactiveVitalEffect)
    {
      list.add(e);
    }
  }
}
