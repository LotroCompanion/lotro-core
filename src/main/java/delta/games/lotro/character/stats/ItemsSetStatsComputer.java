package delta.games.lotro.character.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.CharacterEquipment;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.CharacterEquipment.SlotContents;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.character.stats.contribs.StatsContributionsManager;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.sets.ItemsSet;
import delta.games.lotro.lore.items.sets.ItemsSetsManager;
import delta.games.lotro.lore.items.sets.SetBonus;

/**
 * Computes stats contributions from items sets.
 * @author DAM
 */
public class ItemsSetStatsComputer
{
  private ItemsSetsManager _itemsSetsManager;

  /**
   * Constructor;
   */
  public ItemsSetStatsComputer()
  {
    _itemsSetsManager=ItemsSetsManager.getInstance();
  }

  /**
   * Get the stats contribution from items sets.
   * @param equipment Equipment to use.
   * @param contribs Contributions manager (optional).
   * @return some stats.
   */
  public BasicStatsSet getStats(CharacterEquipment equipment, StatsContributionsManager contribs)
  {
    List<ItemInstance<? extends Item>> itemInstances=getItemInstancesFromGear(equipment);
    Map<Integer,List<ItemInstance<? extends Item>>> itemInstancesBySet=getItemInstancesBySet(itemInstances);
    BasicStatsSet stats=new BasicStatsSet();
    for(Map.Entry<Integer,List<ItemInstance<? extends Item>>> entry : itemInstancesBySet.entrySet())
    {
      BasicStatsSet statsForSet=new BasicStatsSet();
      int setId=entry.getKey().intValue();
      ItemsSet set=_itemsSetsManager.getSetById(setId);
      int count=entry.getValue().size();
      int level=getSetLevel(set,entry.getValue());
      for(SetBonus bonus : set.getBonuses())
      {
        int countForBonus=bonus.getPiecesCount();
        if (count>=countForBonus)
        {
          StatsProvider statsProvider=bonus.getStatsProvider();
          BasicStatsSet bonusStats=statsProvider.getStats(1,level);
          statsForSet.addStats(bonusStats);
        }
      }
      if (statsForSet.getStatsCount()>0)
      {
        stats.addStats(statsForSet);
        if (contribs!=null)
        {
          StatsContribution contrib=StatsContribution.getItemsSetContrib(set,count,statsForSet);
          contribs.addContrib(contrib);
        }
      }
    }
    return stats;
  }

  private int getSetLevel(ItemsSet set, List<ItemInstance<? extends Item>> itemInstances)
  {
    if (set.useAverageItemLevelForSetLevel())
    {
      int totalItemLevels=0;
      for(ItemInstance<? extends Item> itemInstance : itemInstances)
      {
        Integer itemLevel=itemInstance.getEffectiveItemLevel();
        totalItemLevels+=((itemLevel!=null)?itemLevel.intValue():0);
      }
      int nbElements=itemInstances.size();
      return totalItemLevels/nbElements;
    }
    return set.getSetLevel();
  }

  private Map<Integer,List<ItemInstance<? extends Item>>> getItemInstancesBySet(List<ItemInstance<? extends Item>> itemInstances)
  {
    Map<Integer,List<ItemInstance<? extends Item>>> ret=new HashMap<Integer,List<ItemInstance<? extends Item>>>();
    for(ItemInstance<? extends Item> itemInstance : itemInstances)
    {
      Item item=itemInstance.getItem();
      int itemId=item.getIdentifier();
      List<ItemsSet> sets=_itemsSetsManager.getSetsForItem(itemId);
      for(ItemsSet set : sets)
      {
        int setId=set.getIdentifier();
        Integer setKey=Integer.valueOf(setId);
        List<ItemInstance<? extends Item>> itemsForSet=ret.get(setKey);
        if (itemsForSet==null)
        {
          itemsForSet=new ArrayList<ItemInstance<? extends Item>>();
          ret.put(setKey,itemsForSet);
        }
        itemsForSet.add(itemInstance);
      }
    }
    return ret;
  }

  private List<ItemInstance<? extends Item>> getItemInstancesFromGear(CharacterEquipment equipment)
  {
    List<ItemInstance<? extends Item>> itemInstances=new ArrayList<ItemInstance<? extends Item>>();
    for(EQUIMENT_SLOT slot : EQUIMENT_SLOT.values())
    {
      SlotContents slotContents=equipment.getSlotContents(slot,false);
      if (slotContents!=null)
      {
        ItemInstance<?> itemInstance=slotContents.getItem();
        if (itemInstance!=null)
        {
          itemInstances.add(itemInstance);
        }
      }
    }
    return itemInstances;
  }
}
