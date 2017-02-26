package delta.games.lotro.plugins;

import java.io.File;

import delta.games.lotro.character.storage.AccountServerStorage;
import delta.games.lotro.plugins.altinventory.AltInventoryInterface;
import delta.games.lotro.plugins.altinventory.CharListParser;
import delta.games.lotro.plugins.altinventory.DataParser;
import delta.games.lotro.plugins.kikiinventory.KikiInterface;
import delta.games.lotro.plugins.kikiinventory.WalletParser;

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
    AccountServerStorage storage=new AccountServerStorage(account,server);
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
    storage.dump();
  }
}
