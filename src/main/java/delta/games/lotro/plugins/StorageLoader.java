package delta.games.lotro.plugins;

import java.io.File;

import delta.games.lotro.character.storage.AccountServerStorage;
import delta.games.lotro.plugins.altinventory.AltInventoryInterface;
import delta.games.lotro.plugins.altinventory.CharListParser;
import delta.games.lotro.plugins.altinventory.DataParser;
import delta.games.lotro.plugins.kikiinventory.KikiInterface;
import delta.games.lotro.plugins.kikiinventory.WalletParser;

/**
 * Loads storage data.
 * @author DAM
 */
public class StorageLoader
{
  /**
   * Load storage data for an account/server couple.
   * @param account Targeted account ID.
   * @param server Server name.
   * @return a storage or <code>null</code>.
   */
  public AccountServerStorage loadStorage(String account, String server)
  {
    AccountServerStorage storage=new AccountServerStorage(account,server);
    try
    {
      // Use AltInventory data
      AltInventoryInterface altInventory=new AltInventoryInterface(account,server);
      File charListFile=altInventory.getCharListFile();
      CharListParser parser=new CharListParser();
      parser.doIt(storage,charListFile);
      DataParser dataParser=new DataParser();
      File dataFile=altInventory.getDataFile();
      dataParser.doIt(storage,dataFile);
      // Use KikiInventory dat
      KikiInterface kikiInventory=new KikiInterface(account,server);
      File walletFile=kikiInventory.getWalletFile();
      WalletParser walletParser=new WalletParser();
      walletParser.doIt(storage,walletFile);
    }
    catch(Exception e)
    {
      storage=null;
    }
    return storage;
  }
}
