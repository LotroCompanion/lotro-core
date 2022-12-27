package delta.games.lotro.common.stats;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.StatsSetElement;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Test class to display item stats for those items who have low stat values (<1).
 * @author DAM
 */
public class MainTestStatUtilsWithLowValues
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    for(Item item : ItemsManager.getInstance().getAllItems())
    {
      BasicStatsSet stats=item.getStats();
      boolean doIt=false;
      for(StatsSetElement element : stats.getStatElements())
      {
        float value=element.getFloatValue();
        if (Math.abs(value)<1)
        {
          doIt=true;
        }
      }
      if (doIt)
      {
        System.out.println("Item: "+item);
        System.out.println("\t"+stats);
        System.out.println(StatUtils.getStatsDisplayLinesAsList(stats));
      }
    }
  }
}
