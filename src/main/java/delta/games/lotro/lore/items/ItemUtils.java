package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.text.TextTools;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.enums.Genus;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryNameComparator;
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
      Effect effect=effectGenerator.getEffect();
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
    int nbEntries=statsProvider.getEntriesCount();
    if (nbEntries>=0)
    {
      StatsProvider itemStatsProvider=item.getStatsProvider();
      if (itemStatsProvider==null)
      {
        itemStatsProvider=new StatsProvider();
        item.setStatsProvider(itemStatsProvider);
      }
      for(int i=0;i<nbEntries;i++)
      {
        itemStatsProvider.addEntry(statsProvider.getEntry(i));
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
      if (detailsMgr!=null)
      {
        List<WeaponSlayerInfo> infos=detailsMgr.getItemDetails(WeaponSlayerInfo.class);
        if (!infos.isEmpty())
        {
          for(WeaponSlayerInfo info : infos)
          {
            genus.addAll(info.getGenus());
          }
        }
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
   * @param itemLevel Item level to use (<code>null</code> to use default).
   * @return A list of lines.
   */
  public static List<String> buildLinesToShowItem(Item item, Integer itemLevel)
  {
    List<String> lines=new ArrayList<String>();
    StatsProvider statsProvider=item.getStatsProvider();
    if (statsProvider!=null)
    {
      int level=getLevelForStatsComputations(item,itemLevel);
      lines.addAll(StatUtils.getFullStatsForDisplay(statsProvider,level));
    }
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
   * Get the item level to use for stats computations.
   * @param item Item.
   * @param itemLevelInt Item level (<code>null</code> to use default).
   * @return A level.
   */
  public static int getLevelForStatsComputations(Item item, Integer itemLevelInt)
  {
    if (itemLevelInt==null)
    {
      Integer itemLevel=item.getItemLevelForStats();
      return (itemLevel!=null)?itemLevel.intValue():0;
    }
    Integer offsetInt=item.getItemLevelOffset();
    int offset=(offsetInt!=null)?offsetInt.intValue():0;
    int itemLevel=(itemLevelInt!=null)?itemLevelInt.intValue():0;
    int effectiveItemLevel=itemLevel+offset;
    return effectiveItemLevel;
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
    List<String> lines=StatUtils.getFullStatsForDisplay(statsProvider,level);
    SetEffectsDisplay effectsDisplay=new SetEffectsDisplay();
    List<String> effectsDisplayText=effectsDisplay.buildSetEffectsDisplay(set,bonus,level);
    lines.addAll(effectsDisplayText);
    if (!lines.isEmpty())
    {
      lines=TextTools.handleNewLines(lines);
    }
    return lines;
  }
}
