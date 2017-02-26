package delta.games.lotro.plugins.kikiinventory;

import java.io.File;

import delta.games.lotro.plugins.PluginConstants;

/**
 * @author DAM
 */
public class KikiInterface
{
  private String _account;
  private String _server;

  /**
   * Constructor.
   * @param account Account name.
   * @param server Server name.
   */
  public KikiInterface(String account, String server)
  {
    _account=account;
    _server=server;
  }

  /**
   * Get the path of the 'CharList' file.
   * @return a file.
   */
  public File getWalletFile()
  {
    File rootDir=PluginConstants.getServerAccount(_account,_server);
    File allCharactersDir=new File(rootDir,"AllCharacters");
    File walletFile=new File(allCharactersDir,"KikiInventory_wallet.plugindata");
    return walletFile;
  }
}
