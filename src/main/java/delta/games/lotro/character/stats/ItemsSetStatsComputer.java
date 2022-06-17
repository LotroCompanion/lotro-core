package delta.games.lotro.character.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.gear.CharacterGear;
import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.character.gear.GearSlotContents;
import delta.games.lotro.character.stats.contribs.StatsContribution;
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
   * @param characterLevel Character level.
   * @param equipment Equipment to use.
   * @return some stats.
   */
  public List<StatsContribution> getStats(int characterLevel, CharacterGear equipment)
  {
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    List<ItemInstance<? extends Item>> itemInstances=getItemInstancesFromGear(equipment);
    int nbInstances=itemInstances.size();
    if (nbInstances==0)
    {
      return ret;
    }
    Map<Integer,List<ItemInstance<? extends Item>>> itemInstancesBySet=getItemInstancesBySet(itemInstances);
    for(Map.Entry<Integer,List<ItemInstance<? extends Item>>> entry : itemInstancesBySet.entrySet())
    {
      int setId=entry.getKey().intValue();
      ItemsSet set=_itemsSetsManager.getSetById(setId);
      boolean isApplicable=StatsComputationUtils.setIsApplicable(set,characterLevel);
      if (!isApplicable)
      {
        continue;
      }
      BasicStatsSet statsForSet=new BasicStatsSet();
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
        StatsContribution contrib=StatsContribution.getItemsSetContrib(set,count,statsForSet);
        ret.add(contrib);
      }
    }
    return ret;
  }

  private int getSetLevel(ItemsSet set, List<ItemInstance<? extends Item>> itemInstances)
  {
    if (set.useAverageItemLevelForSetLevel())
    {
      int totalItemLevels=0;
      for(ItemInstance<? extends Item> itemInstance : itemInstances)
      {
        int itemLevel=itemInstance.getApplicableItemLevel();
        totalItemLevels+=itemLevel;
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

  private List<ItemInstance<? extends Item>> getItemInstancesFromGear(CharacterGear equipment)
  {
    List<ItemInstance<? extends Item>> itemInstances=new ArrayList<ItemInstance<? extends Item>>();
    for(GearSlot slot : GearSlot.values())
    {
      GearSlotContents slotContents=equipment.getSlotContents(slot,false);
      if (slotContents==null)
      {
        continue;
      }
      ItemInstance<?> itemInstance=slotContents.getItem();
      if (itemInstance==null)
      {
        continue;
      }
      boolean isApplicable=StatsComputationUtils.itemIsApplicable(itemInstance);
      if (isApplicable)
      {
        itemInstances.add(itemInstance);
      }
    }
    return itemInstances;
  }
}
