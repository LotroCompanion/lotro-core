package delta.games.lotro.lore.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import delta.common.utils.statistics.ValueSetStatistics;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectsManager;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.lore.agents.mobs.MobsManager;

/**
 * Test class for effect utilities.
 * @author DAM
 */
class EffectUtilsTest
{
  @Test
  void test()
  {
    ValueSetStatistics stats=new ValueSetStatistics();
    MobsManager mgr=MobsManager.getInstance();
    for(MobDescription mob : mgr.getMobs())
    {
      Set<Integer> effectIds=EffectUtils.getEffectsFromMob(mob);
      assertNotNull(effectIds);
      if (!effectIds.isEmpty())
      {
        List<Integer> ids=new ArrayList<Integer>(effectIds);
        if (ids.size()>45)
        {
          Collections.sort(ids);
          System.out.println("Mob: "+mob);
          for(Integer id : ids)
          {
            Effect effect=EffectsManager.getInstance().getEffectById(id.intValue());
            System.out.println("\t"+effect);
          }
        }
        stats.addValue(ids.size());
      }
    }
    System.out.println(stats.dumpByValue());
  }
}
