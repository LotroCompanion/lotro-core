package delta.games.lotro.lore.xrefs.items;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemQualities;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.legendary.LegendaryWeapon;

/**
 * Test class for the item references builder.
 * @author DAM
 */
public class MainTestItemReferencesBuilder
{
  /**
   * Do it.
   */
  private void doIt()
  {
    ItemReferencesBuilder builder=new ItemReferencesBuilder();
    ItemsManager itemsMgr=ItemsManager.getInstance();
    for(Item item : itemsMgr.getAllItems())
    {
      if (useItem(item))
      {
        builder.inspectItem(item.getIdentifier());
      }
    }
  }

  private boolean useItem(Item item)
  {
    if (item.getEquipmentLocation()==null) return false;
    if (item instanceof LegendaryWeapon)
    {
      if (item.getQuality()==ItemQualities.INCOMPARABLE)
      {
        Integer minLevel=item.getMinLevel();
        if ((minLevel!=null) && (minLevel.intValue()==75))
        {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestItemReferencesBuilder().doIt();
  }
}
