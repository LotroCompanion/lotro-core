package delta.games.lotro.plugins;

import delta.games.lotro.character.storage.AccountServerStorage;

/**
 * Test to load storage data from AltInventory/Kiki.
 * @author DAM
 */
public class MainTestLoadStorageFromPluginsData
{
  /**
   * Main method for this test.
   * @param args Not used.
   * @throws Exception If a problem occurs.
   */
  public static void main(String[] args) throws Exception
  {
    String account="glorfindel666";
    String server="Landroval";
    StorageLoader loader=new StorageLoader();
    AccountServerStorage storage=loader.loadStorage(account,server);
    if (storage!=null)
    {
      storage.dump();
    }
  }
}
