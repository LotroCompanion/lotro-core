package delta.games.lotro.lore.trade.barter;

import java.util.List;

/**
 * Simple test class for the barterers manager.
 * @author DAM
 */
public class MainTestBarterersManager
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    BarterersManager mgr=BarterersManager.getInstance();
    List<BarterNpc> barterers=mgr.getAll();
    for(BarterNpc barterer : barterers)
    {
      System.out.println(barterer.dump());
    }
  }
}
