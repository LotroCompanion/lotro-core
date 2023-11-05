package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.text.TextTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.effects.Effect2;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.enums.Genus;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryNameComparator;
import delta.games.lotro.common.stats.SpecialEffect;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.details.ItemDetail;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.items.details.WeaponSlayerInfo;
import delta.games.lotro.lore.items.effects.GenericItemEffects;
import delta.games.lotro.lore.items.effects.GenericItemEffectsManager;
import delta.games.lotro.lore.items.effects.ItemEffectsDisplay;
import delta.games.lotro.lore.items.effects.ItemEffectsManager;
import delta.games.lotro.lore.items.effects.ItemEffectsManager.Type;
import delta.games.lotro.lore.items.sets.ItemsSet;
import delta.games.lotro.lore.items.sets.SetBonus;
import delta.games.lotro.lore.items.sets.SetEffectsDisplay;

/**
 * Utility methods related to items.
 * @author DAM
 */
public class ItemUtils
{
  /**
   * Finalize item stats.
   * @param item Item to enhance.
   */
  public static void finalizeItemStats(Item item)
  {
    injectGenericEffects(item);
    addOnEquipStats(item);
  }

  /**
   * Add generic effects to the given item.
   * @param item Item to enhance.
   */
  private static void injectGenericEffects(Item item)
  {
    GenericItemEffectsManager mgr=GenericItemEffectsManager.getInstance();
    GenericItemEffects effects=mgr.getEffects(item.getEquipmentCategory());
    if (effects==null)
    {
      return;
    }
    StatsProvider statsProvider=effects.getStatsProvider();
    addStatsProvider(item,statsProvider);
  }

  /**
   * Add stats from 'on equip' effects. 
   * @param item Item to enhance.
   */
  private static void addOnEquipStats(Item item)
  {
    ItemEffectsManager effectsMgr=item.getEffects();
    if (effectsMgr==null)
    {
      return;
    }
    EffectGenerator[] effectGenerators=effectsMgr.getEffects(Type.ON_EQUIP);
    int nbOnEquip=effectGenerators.length;
    for(int i=nbOnEquip-1;i>=0;i--)
    {
      EffectGenerator effectGenerator=effectGenerators[i];
      Effect2 effect=effectGenerator.getEffect();
      if (effect instanceof PropertyModificationEffect)
      {
        PropertyModificationEffect propModEffect=(PropertyModificationEffect)effect;
        StatsProvider effectStats=propModEffect.getStatsProvider();
        addStatsProvider(item,effectStats);
      }
    }
  }

  private static void addStatsProvider(Item item, StatsProvider statsProvider)
  {
    if (statsProvider==null)
    {
      return;
    }
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
        for(SpecialEffect specialEffect : statsProvider.getSpecialEffects())
        {
          oldStatsProvider.addSpecialEffect(specialEffect);
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

  /**
   * Build the lines to show an item (stats+special effects+effects).
   * @param item Item to use.
   * @return A list of lines.
   */
  public static List<String> buildLinesToShowItem(Item item)
  {
    StatsProvider statsProvider=item.getStatsProvider();
    BasicStatsSet stats=item.getStats();
    List<String> lines=StatUtils.getFullStatsDisplayAsLines(stats,statsProvider);
    ItemEffectsDisplay effectsDisplay=new ItemEffectsDisplay();
    List<String> effectsDisplayText=effectsDisplay.buildItemEffectsDisplay(item);
    lines.addAll(effectsDisplayText);
    if (!lines.isEmpty())
    {
      lines=TextTools.handleNewLines(lines);
    }
    return lines;
  }

  /**
   * Build the lines to show a set bonus (stats+special effects+effects).
   * @param set Set to use.
   * @param bonus Set bonus.
   * @param level Level to use for computations.
   * @return A list of lines.
   */
  public static List<String> buildLinesToShowItemsSetBonus(ItemsSet set, SetBonus bonus, int level)
  {
    StatsProvider statsProvider=bonus.getStatsProvider();
    BasicStatsSet stats=statsProvider.getStats(1,level);
    List<String> lines=StatUtils.getFullStatsDisplayAsLines(stats,statsProvider);

    SetEffectsDisplay effectsDisplay=new SetEffectsDisplay();
    List<String> effectsDisplayText=effectsDisplay.buildSetEffectsDisplay(set,bonus);
    lines.addAll(effectsDisplayText);
    if (!lines.isEmpty())
    {
      lines=TextTools.handleNewLines(lines);
    }
    return lines;
  }
}
