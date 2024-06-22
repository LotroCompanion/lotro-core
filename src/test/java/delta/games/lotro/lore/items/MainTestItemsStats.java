package delta.games.lotro.lore.items;

import java.util.List;

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
      List<String> lines=ItemUtils.buildLinesToShowItem(item,null);
      for(String line : lines)
      {
        System.out.println("\t"+line);
      }
    }
  }
}
