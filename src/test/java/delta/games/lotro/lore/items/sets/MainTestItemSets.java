package delta.games.lotro.lore.items.sets;

import java.util.List;

import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Simple test class to show the bonuses of item sets.
 * @author DAM
 */
public class MainTestItemSets
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    ItemsSetsManager mgr=ItemsSetsManager.getInstance();
    List<ItemsSet> itemsSets=mgr.getAll();
    for(ItemsSet itemsSet : itemsSets)
    {
      System.out.println("Items set: "+itemsSet.getName());
      int level=itemsSet.getSetLevel();
      for(SetBonus bonusSet : itemsSet.getBonuses())
      {
        int count=bonusSet.getPiecesCount();
        System.out.println("\tCount="+count);
        StatsProvider statsProvider=bonusSet.getStatsProvider();
        List<String> lines=StatUtils.getFullStatsForDisplay(statsProvider,level);
        for(String line : lines)
        {
          System.out.println("\t\t"+line);
        }
      }
    }
  }
}
