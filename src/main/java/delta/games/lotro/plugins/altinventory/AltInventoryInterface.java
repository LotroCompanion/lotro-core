package delta.games.lotro.plugins.altinventory;

import java.io.File;

import delta.games.lotro.plugins.PluginConstants;

/**
 * Interface with the data of the 'Alt Inventory' plugin.
 * @author DAM
 */
public class AltInventoryInterface
{
  private String _account;
  private String _server;

  /**
   * Constructor.
   * @param account Account name.
   * @param server Server name.
   */
  public AltInventoryInterface(String account, String server)
  {
    _account=account;
    _server=server;
  }

  /**
   * Get the path of the 'CharList' file.
   * @return a file.
   */
  public File getCharListFile()
  {
    File rootDir=PluginConstants.getServerAccount(_account,_server);
    File allCharactersDir=new File(rootDir,"AllCharacters");
    File charListFile=new File(allCharactersDir,"AltInventoryCharList.plugindata");
    return charListFile;
  }

  /**
   * Get the path of the 'Data' file.
   * @return a file.
   */
  public File getDataFile()
  {
    File rootDir=PluginConstants.getServerAccount(_account,_server);
    File allCharactersDir=new File(rootDir,"AllCharacters");
    File charListFile=new File(allCharactersDir,"AltInventoryData.plugindata");
    return charListFile;
  }
}
