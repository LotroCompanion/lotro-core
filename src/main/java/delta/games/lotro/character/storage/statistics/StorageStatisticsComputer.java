package delta.games.lotro.character.storage.statistics;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.character.storage.StoredItem;
import delta.games.lotro.character.storage.statistics.reputation.StorageFactionStats;
import delta.games.lotro.character.storage.statistics.reputation.StorageReputationStats;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.statistics.items.ItemsStats;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.DisenchantmentManager;
import delta.games.lotro.lore.items.DisenchantmentResult;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.ItemProvider;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.items.details.ItemReputation;
import delta.games.lotro.lore.items.details.ItemXP;
import delta.games.lotro.lore.items.details.VirtueXP;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.utils.Registry;

/**
 * Computes statistics on stored items.
 * @author DAM
 */
public class StorageStatisticsComputer
{
  private static final Logger LOGGER=LoggerFactory.getLogger(StorageStatisticsComputer.class);

  /**
   * Compute storage statistics.
   * @param items Item to use.
   * @param results Storage for results.
   */
  public void computeStatistics(List<StoredItem> items, StorageStatistics results)
  {
    // Item XP
    long totalItemXP=computeTotalItemXP(items);
    results.setTotalItemXP(totalItemXP);
    // Virtue XP
    long totalVirtueXP=computeTotalVirtueXP(items);
    results.setTotalVirtueXP(totalVirtueXP);
    // Reputation
    computeReputationStats(items,results.getReputationStats());
    // Disenchantment results
    computeDisenchantmentResults(items,results.getItemStats());
    // Total value
    Money totalValue=computeTotalValue(items);
    results.setTotalValue(totalValue);
  }

  private void computeReputationStats(List<StoredItem> items, StorageReputationStats results)
  {
    results.reset();
    for(StoredItem storedItem : items)
    {
      CountedItem<ItemProvider> counted=storedItem.getItem();
      Item item=counted.getItem();
      ItemDetailsManager details=item.getDetails();
      if (details!=null)
      {
        List<ItemReputation> itemReputations=details.getItemDetails(ItemReputation.class);
        if (!itemReputations.isEmpty())
        {
          ItemReputation itemReputation=itemReputations.get(0);
          int amount=itemReputation.getAmount();
          Faction faction=itemReputation.getFaction();
          int count=counted.getQuantity();
          StorageFactionStats stats=results.get(faction,true);
          stats.addItems(count,amount);
        }
      }
    }
  }

  private void computeDisenchantmentResults(List<StoredItem> items, ItemsStats results)
  {
    Registry<DisenchantmentResult> registry=DisenchantmentManager.getInstance().getDisenchantmentResults();
    results.reset();
    for(StoredItem storedItem : items)
    {
      CountedItem<ItemProvider> counted=storedItem.getItem();
      Item item=counted.getItem();
      int quantity=counted.getQuantity();
      DisenchantmentResult result=registry.getItem(item.getIdentifier());
      if (result!=null)
      {
        CountedItem<Item> resultItems=result.getCountedItem();
        if (resultItems!=null)
        {
          int resultItemId=resultItems.getIdentifier();
          int resultItemQuantity=resultItems.getQuantity();
          results.add(resultItemId,resultItemQuantity*quantity);
        }
      }
    }
  }

  private long computeTotalItemXP(List<StoredItem> items)
  {
    long totalXP=0;
    for(StoredItem storedItem : items)
    {
      CountedItem<ItemProvider> counted=storedItem.getItem();
      Item item=counted.getItem();
      ItemDetailsManager details=item.getDetails();
      if (details!=null)
      {
        List<ItemXP> itemXPs=details.getItemDetails(ItemXP.class);
        if (!itemXPs.isEmpty())
        {
          ItemXP itemXP=itemXPs.get(0);
          int count=counted.getQuantity();
          if (LOGGER.isDebugEnabled())
          {
            LOGGER.debug("Got "+count+" of "+item+" - "+itemXP.getAmount()+" XP");
          }
          totalXP+=(count*itemXP.getAmount());
        }
      }
    }
    return totalXP;
  }

  private long computeTotalVirtueXP(List<StoredItem> items)
  {
    long totalXP=0;
    for(StoredItem storedItem : items)
    {
      CountedItem<ItemProvider> counted=storedItem.getItem();
      Item item=counted.getItem();
      ItemDetailsManager details=item.getDetails();
      if (details!=null)
      {
        List<VirtueXP> virtueXPs=details.getItemDetails(VirtueXP.class);
        if (!virtueXPs.isEmpty())
        {
          VirtueXP virtueXP=virtueXPs.get(0);
          int count=counted.getQuantity();
          totalXP+=(count*virtueXP.getAmount());
        }
      }
    }
    return totalXP;
  }

  private Money computeTotalValue(List<StoredItem> items)
  {
    int totalRawValue=0;
    for(StoredItem storedItem : items)
    {
      CountedItem<ItemProvider> counted=storedItem.getItem();
      int itemRawValue=0;
      ItemProvider itemProvider=counted.getManagedItem();
      if (itemProvider instanceof ItemInstance)
      {
        ItemInstance<?> itemInstance=(ItemInstance<?>)itemProvider;
        Money value=itemInstance.getEffectiveValue();
        if (value!=null)
        {
          itemRawValue=value.getInternalValue();
        }
      }
      else if (itemProvider instanceof Item)
      {
        Item item=(Item)itemProvider;
        Money value=item.getValueAsMoney();
        if (value!=null)
        {
          itemRawValue=value.getInternalValue();
        }
      }
      int quantity=counted.getQuantity();
      totalRawValue+=(itemRawValue)*quantity;
    }
    Money ret=new Money();
    ret.setRawValue(totalRawValue);
    return ret;
  }
}
