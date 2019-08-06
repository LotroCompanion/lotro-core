package delta.games.lotro.lore.items;

import java.util.List;

import delta.games.lotro.lore.items.legendary.Legendary;

/**
 * Simple test class to instanciate/init all legendary items.
 * @author DAM
 */
public class MainTestLegendaryItemsInstanciation
{
  private void handleLegendaryItem(Item item)
  {
    System.out.println(item.dump());
    ItemInstance<? extends Item> itemInstance=ItemFactory.buildInstance(item);
    ItemFactory.initInstance(itemInstance);
    System.out.println(itemInstance.dump());
  }

  private void doIt()
  {
    ItemsManager itemsMgr=ItemsManager.getInstance();
    List<Item> items=itemsMgr.getAllItems();
    for(Item item : items)
    {
      if (item instanceof Legendary)
      {
        handleLegendaryItem(item);
      }
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestLegendaryItemsInstanciation().doIt();
  }
}
