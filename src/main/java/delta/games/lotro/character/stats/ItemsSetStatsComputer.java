package delta.games.lotro.character.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.misc.IntegerHolder;
import delta.games.lotro.character.CharacterEquipment;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.CharacterEquipment.SlotContents;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.character.stats.contribs.StatsContributionsManager;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.sets.ItemsSet;
import delta.games.lotro.lore.items.sets.SetBonus;
import delta.games.lotro.lore.items.sets.ItemsSetsManager;

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
    List<Item> items=getItemsFromGear(equipment);
    Map<Integer,IntegerHolder> itemsCountsBySet=getItemsCountsBySet(items);
    BasicStatsSet stats=new BasicStatsSet();
    for(Map.Entry<Integer,IntegerHolder> entry : itemsCountsBySet.entrySet())
    {
      BasicStatsSet statsForSet=new BasicStatsSet();
      int setId=entry.getKey().intValue();
      ItemsSet set=_itemsSetsManager.getSetById(setId);
      int count=entry.getValue().getInt();
      for(SetBonus bonus : set.getBonuses())
      {
        int countForBonus=bonus.getPiecesCount();
        if (count>=countForBonus)
        {
          StatsProvider statsProvider=bonus.getStatsProvider();
          int level=set.getSetLevel();
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

  private Map<Integer,IntegerHolder> getItemsCountsBySet(List<Item> items)
  {
    Map<Integer,IntegerHolder> ret=new HashMap<Integer,IntegerHolder>();
    for(Item item : items)
    {
      int itemId=item.getIdentifier();
      List<ItemsSet> sets=_itemsSetsManager.getSetsForItem(itemId);
      for(ItemsSet set : sets)
      {
        int setId=set.getIdentifier();
        Integer setKey=Integer.valueOf(setId);
        IntegerHolder intHolder=ret.get(setKey);
        if (intHolder==null)
        {
          intHolder=new IntegerHolder();
          ret.put(setKey,intHolder);
        }
        intHolder.increment();
      }
    }
    return ret;
  }

  private List<Item> getItemsFromGear(CharacterEquipment equipment)
  {
    List<Item> items=new ArrayList<Item>();
    for(EQUIMENT_SLOT slot : EQUIMENT_SLOT.values())
    {
      SlotContents slotContents=equipment.getSlotContents(slot,false);
      if (slotContents!=null)
      {
        ItemInstance<?> itemInstance=slotContents.getItem();
        if (itemInstance!=null)
        {
          items.add(itemInstance.getReference());
        }
      }
    }
    return items;
  }
}
