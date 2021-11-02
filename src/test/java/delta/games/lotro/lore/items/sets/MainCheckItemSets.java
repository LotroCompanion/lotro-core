package delta.games.lotro.lore.items.sets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.SpecialEffect;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemPropertyNames;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.scaling.Munging;
import delta.games.lotro.lore.items.sets.ItemsSet.SetType;

/**
 * Simple test class to show the bonuses of item sets.
 * @author DAM
 */
public class MainCheckItemSets
{
  private void doIt()
  {
    //checkItemsThatBelongToSeveralSets();
    // => no item belong to more than 1 set
    //checkItemsInSetAreUnique();
    // => lots of non unique set members
  }

  void checkItemsThatBelongToSeveralSets()
  {
    System.out.println("Check for items that belong to several sets:");
    // Gather data
    Map<Integer,List<ItemsSet>> mapByItemId=new HashMap<Integer,List<ItemsSet>>();
    ItemsSetsManager mgr=ItemsSetsManager.getInstance();
    List<ItemsSet> itemsSets=mgr.getAll();
    for(ItemsSet itemsSet : itemsSets)
    {
      // Filter
      /*
      boolean average=itemsSet.useAverageItemLevelForSetLevel();
      if (!average)
      {
        continue;
      }
      */
      SetType type=itemsSet.getSetType();
      if (type==SetType.TRACERIES)
      {
        continue;
      }
      mapItemsForSet(itemsSet,mapByItemId);
    }
    // Check
    for(Map.Entry<Integer,List<ItemsSet>> entry : mapByItemId.entrySet())
    {
      List<ItemsSet> sets=entry.getValue();
      if (sets.size()>1)
      {
        int itemId=entry.getKey().intValue();
        Item item=ItemsManager.getInstance().getItem(itemId);
        System.out.println("Item: "+item+" is found in severals sets:");
        for(ItemsSet set : sets)
        {
          System.out.println("\t"+set.getIdentifier()+" - "+set.getName());
        }
      }
    }
  }

  private void mapItemsForSet(ItemsSet itemsSet, Map<Integer,List<ItemsSet>> mapByItemId)
  {
    List<Item> members=itemsSet.getMembers();
    for(Item member : members)
    {
      Integer key=Integer.valueOf(member.getIdentifier());
      List<ItemsSet> sets=mapByItemId.get(key);
      if (sets==null)
      {
        sets=new ArrayList<ItemsSet>();
        mapByItemId.put(key,sets);
      }
      sets.add(itemsSet);
    }
  }

  void displayItemLevelSet(ItemsSet itemsSet)
  {
    System.out.println("Items set: "+itemsSet.getName());
    int level=itemsSet.getSetLevel();
    System.out.println("\tSet level="+level);
    List<Item> members=itemsSet.getMembers();
    System.out.println("\tMembers:");
    for(Item member : members)
    {
      Munging munging=null;
      String mungingSpec=member.getProperty(ItemPropertyNames.MUNGING);
      if (mungingSpec!=null)
      {
        munging=Munging.fromString(mungingSpec);
      }
      System.out.println("\t\t"+member+" => "+munging);
    }
    for(SetBonus bonusSet : itemsSet.getBonuses())
    {
      int count=bonusSet.getPiecesCount();
      System.out.println("\tCount="+count);
      StatsProvider statsProvider=bonusSet.getStatsProvider();
      BasicStatsSet bonusStats=statsProvider.getStats(1,level);
      String[] lines=StatUtils.getStatsDisplayLines(bonusStats);
      for(String line : lines)
      {
        System.out.println("\t\t"+line);
      }
      for(SpecialEffect effect : statsProvider.getSpecialEffects())
      {
        System.out.println("\t\tEFFECT="+effect.getLabel());
      }
    }
  }

  void checkItemsInSetAreUnique()
  {
    System.out.println("Check unicity of items:");
    // Gather data
    ItemsSetsManager mgr=ItemsSetsManager.getInstance();
    List<ItemsSet> itemsSets=mgr.getAll();
    for(ItemsSet itemsSet : itemsSets)
    {
      // Filter
      /*
      boolean average=itemsSet.useAverageItemLevelForSetLevel();
      if (!average)
      {
        continue;
      }
      */
      SetType type=itemsSet.getSetType();
      if (type==SetType.TRACERIES)
      {
        continue;
      }
      for(Item member : itemsSet.getMembers())
      {
        boolean isMultipleSlot=isMultipleSlot(member.getEquipmentLocation());
        if (isMultipleSlot)
        {
          boolean isUnique=member.isUnique();
          if (!isUnique)
          {
            System.out.println("Set "+itemsSet.getIdentifier()+" - "+itemsSet.getName()+": "+member+" is not unique");
          }
        }
      }
    }
  }

  private boolean isMultipleSlot(EquipmentLocation location)
  {
    return ((location==EquipmentLocation.FINGER)||(location==EquipmentLocation.EAR)||(location==EquipmentLocation.WRIST));
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainCheckItemSets().doIt();
  }
}
