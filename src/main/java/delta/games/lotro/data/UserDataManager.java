package delta.games.lotro.data;

import java.io.File;

import delta.games.lotro.config.DataConfiguration;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade to access to user data storage.
 * @author DAM
 */
public class UserDataManager
{
  private static UserDataManager _instance=new UserDataManager();

  // Root directory for user data
  private File _userDataDir;
  // Account data
  private File _accountsDir;
  // Character data
  private File _toonsDir;
  // Kinship data
  private File _kinshipsDir;
  // Trait tree setups
  private File _traitTreeSetupDir;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static UserDataManager getInstance()
  {
    return _instance;
  }

  /**
   * Constructor.
   */
  private UserDataManager()
  {
    initUserDataDir();
    _toonsDir=new File(_userDataDir,"characters");
    _accountsDir=new File(_userDataDir,"accounts");
    _kinshipsDir=new File(_userDataDir,"kinships");
    _traitTreeSetupDir=new File(_userDataDir,"traitTrees");
  }

  private void initUserDataDir()
  {
    DataConfiguration dataCfg=LotroCoreConfig.getInstance().getDataConfiguration();
    _userDataDir=(dataCfg.getRootPath());
  }

  /**
   * Get the root storage directory for toons.
   * @return a directory.
   */
  public File getToonsDir()
  {
    return _toonsDir;
  }

  /**
   * Get the root storage directory for accounts.
   * @return a directory.
   */
  public File getAccountsDir()
  {
    return _accountsDir;
  }

  /**
   * Get the root storage directory for kinships.
   * @return a directory.
   */
  public File getKinshipsDir()
  {
    return _kinshipsDir;
  }

  /**
   * Get the root storage directory for trait tree setups.
   * @return a directory.
   */
  public File getTraitTreeSetupDir()
  {
    return _traitTreeSetupDir;
  }
}
