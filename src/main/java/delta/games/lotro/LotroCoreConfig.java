package delta.games.lotro;

import java.io.File;

import delta.common.utils.misc.TypedProperties;

/**
 * Configuration.
 * @author DAM
 */
public final class LotroCoreConfig
{
  private static LotroCoreConfig _instance=new LotroCoreConfig();
  
  // Root directory for constant data
  private File _applicationDataDir;
  // Configuration
  private File _configDir;
  // Lore
  private File _loreDir;
  private File _indexesDir;
  private File _questsDir;
  private File _deedsDir;
  private File _recipesDir;

  // Root directory for use data
  private File _userDataDir;
  // Account data
  private File _accountsDir;
  // Character data
  private File _toonsDir;

  private TypedProperties _parameters;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static LotroCoreConfig getInstance()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private LotroCoreConfig()
  {
    // Application data
    _applicationDataDir=new File("data");

    // Configuration
    _configDir=new File(_applicationDataDir,"config");
    // Parameters
    File parametersFiles=new File(_configDir,"params.txt");
    _parameters=new TypedProperties();
    _parameters.loadFromFile(parametersFiles);

    // Lore
    _loreDir=new File(_applicationDataDir,"lore");
    _indexesDir=new File(_loreDir,"indexes");
    _questsDir=new File(_applicationDataDir,"quests");
    _deedsDir=new File(_applicationDataDir,"deeds");
    _recipesDir=new File(_applicationDataDir,"recipes");

    // User data
    File userHomeDir=new File(System.getProperty("user.home"));
    File userApplicationDir=new File(userHomeDir,".lotrocompanion");
    _userDataDir=new File(userApplicationDir,"data");
    _toonsDir=new File(_userDataDir,"characters");
    _accountsDir=new File(_userDataDir,"accounts");
  }

  /**
   * Get the directory for application data.
   * @return the directory for application data.
   */
  public File getApplicationDataDir()
  {
    return _applicationDataDir;
  }

  /**
   * Get the root storage directory for configuration files.
   * @return a directory.
   */
  public File getConfigDir()
  {
    return _configDir;
  }

  /**
   * Get the configuration parameters.
   * @return the configuration parameters.
   */
  public TypedProperties getParameters()
  {
    return _parameters;
  }

  /**
   * Get the root directory for lore data storage.
   * @return a directory.
   */
  public File getLoreDir()
  {
    return _loreDir;
  }

  /**
   * Get the root directory for indexes storage.
   * @return a directory.
   */
  public File getIndexesDir()
  {
    return _indexesDir;
  }

  /**
   * Get the root directory for quest data storage.
   * @return a directory.
   */
  public File getQuestsDir()
  {
    return _questsDir;
  }

  /**
   * Get the root directory for deed data storage.
   * @return a directory.
   */
  public File getDeedsDir()
  {
    return _deedsDir;
  }

  /**
   * Get the root directory for recipes data storage.
   * @return a directory.
   */
  public File getRecipesDir()
  {
    return _recipesDir;
  }

  /**
   * Get the directory for user data.
   * @return the directory for user data.
   */
  public File getUserDataDir()
  {
    return _userDataDir;
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
}
