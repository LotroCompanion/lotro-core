package delta.games.lotro.lore.items;

import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.effects.GenericItemEffects;
import delta.games.lotro.lore.items.effects.GenericItemEffectsManager;

/**
 * Utility methods related to items.
 * @author DAM
 */
public class ItemUtils
{
  /**
   * Add generic effects to the given item.
   * @param item Item to enhance.
   */
  public static void injectGenericEffects(Item item)
  {
    GenericItemEffectsManager mgr=GenericItemEffectsManager.getInstance();
    GenericItemEffects effects=mgr.getEffects(item.getEquipmentCategory());
    if (effects==null)
    {
      return;
    }
    StatsProvider statsProvider=effects.getStatsProvider();
    int nbStats=statsProvider.getNumberOfStatProviders();
    if (nbStats>=0)
    {
      StatsProvider oldStatsProvider=item.getStatsProvider();
      if (oldStatsProvider==null)
      {
        item.setStatsProvider(statsProvider);
      }
      else
      {
        for(int i=0;i<nbStats;i++)
        {
          oldStatsProvider.addStatProvider(statsProvider.getStatProvider(i));
        }
      }
    }
  }
}
