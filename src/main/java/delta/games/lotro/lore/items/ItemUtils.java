package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.enums.Genus;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryNameComparator;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.details.ItemDetail;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.items.details.WeaponSlayerInfo;
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

  /**
   * Get a list of genuses used in weapon slayer infos.
   * @return A list of genuses.
   */
  public static List<Genus> getAvailableSlayerGenus()
  {
    Set<Genus> genus=new HashSet<Genus>();
    for(Item item : ItemsManager.getInstance().getAllItems())
    {
      ItemDetailsManager detailsMgr=item.getDetails();
      if (detailsMgr==null)
      {
        continue;
      }
      List<WeaponSlayerInfo> infos=detailsMgr.getItemDetails(WeaponSlayerInfo.class);
      if (infos.isEmpty())
      {
        continue;
      }
      for(WeaponSlayerInfo info : infos)
      {
        genus.addAll(info.getGenus());
      }
    }
    List<Genus> ret=new ArrayList<Genus>(genus);
    Collections.sort(ret,new LotroEnumEntryNameComparator<Genus>());
    return ret;
  }

  /**
   * Get the sole detail of the given class for the given item.
   * @param <T> Type of detail to get.
   * @param item Item to use.
   * @param detailClass Class of detail to get.
   * @return A detail or <code>null</code> if none.
   */
  public static <T extends ItemDetail> T getDetail(Item item, Class<T> detailClass)
  {
    ItemDetailsManager detailsMgr=item.getDetails();
    if (detailsMgr==null)
    {
      return null;
    }
    List<T> details=detailsMgr.getItemDetails(detailClass);
    if (details.isEmpty())
    {
      return null;
    }
    return details.get(0);
  }
}
