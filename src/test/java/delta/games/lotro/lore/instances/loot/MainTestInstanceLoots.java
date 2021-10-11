package delta.games.lotro.lore.instances.loot;

import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.lore.instances.loot.InstanceLootEntry;
import delta.games.lotro.lore.instances.loot.InstanceLootParameters;
import delta.games.lotro.lore.instances.loot.InstanceLootTablesManager;
import delta.games.lotro.lore.instances.loot.InstanceLoots;
import delta.games.lotro.lore.instances.loot.InstanceLootsTable;

/**
 * Test class for instances loots.
 * @author DAM
 */
public class MainTestInstanceLoots
{
  private void doIt()
  {
    InstanceLootTablesManager mgr=InstanceLootTablesManager.getInstance();
    int tableId=mgr.getTables().get(0).getIdentifier();
    InstanceLootsTable table=mgr.getTableById(tableId);
    int instanceId=table.getInstanceLoots().get(0).getIdentifier();
    InstanceLoots instanceLoots=table.getInstanceLootsById(instanceId);
    for(InstanceLootEntry entry : instanceLoots.getEntries())
    {
      InstanceLootParameters params=entry.getParameters();
      TrophyList trophyList=entry.getTrophyList();
      System.out.println(params);
      System.out.println(trophyList);
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestInstanceLoots().doIt();
  }
}
