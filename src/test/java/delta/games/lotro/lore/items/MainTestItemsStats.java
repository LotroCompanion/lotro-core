package delta.games.lotro.lore.items;

import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatUtils;

/**
 * Simple test class to show the stats of items.
 * @author DAM
 */
public class MainTestItemsStats
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    ItemsManager mgr=ItemsManager.getInstance();
    List<Item> items=mgr.getAllItems();
    for(Item item : items)
    {
      System.out.println("Item: "+item);
      BasicStatsSet stats=item.getStats();
      String[] lines=StatUtils.getStatsDisplayLines(stats);
      for(String line : lines)
      {
        System.out.println("\t"+line);
      }
    }
  }
}
