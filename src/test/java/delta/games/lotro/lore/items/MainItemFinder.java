package delta.games.lotro.lore.items;

import delta.games.lotro.common.stats.WellKnownStat;

/**
 * A tool to find items.
 * @author DAM
 */
public class MainItemFinder
{
  /**
   * Basic main method for test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    ItemsManager manager=ItemsManager.getInstance();
    for(Item item : manager.getAllItems())
    {
      if (!(item instanceof Armour))
      {
        Number armor=item.getStats().getStat(WellKnownStat.ARMOUR);
        if (armor!=null)
        {
          System.out.println(item+" => "+armor);
        }
      }
    }
  }
}
