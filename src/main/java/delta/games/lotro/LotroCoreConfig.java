package delta.games.lotro;

import java.io.File;

import delta.games.lotro.utils.TypedProperties;

/**
 * Configuration.
 * @author DAM
 */
public class LotroCoreConfig
{
  private static LotroCoreConfig _instance=new LotroCoreConfig();
  
  private File _rootDataDir;
  private File _configDir;
  private File _toonsDir;
  private File _loreDir;
  private File _indexesDir;
  private File _questsDir;
  private File _deedsDir;
  private File _iconsDir;
  private File _itemsDir;
  private File _recipesDir;
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
    _rootDataDir=new File("data");
    _configDir=new File(_rootDataDir,"config");
    _toonsDir=new File(_rootDataDir,"characters");
    _loreDir=new File(_rootDataDir,"lore");
    _indexesDir=new File(_loreDir,"indexes");
    File parametersFiles=new File(_configDir,"params.txt");
    _parameters=new TypedProperties();
    _parameters.loadFromFile(parametersFiles);
    _questsDir=new File(_rootDataDir,"quests");
    _deedsDir=new File(_rootDataDir,"deeds");
    _iconsDir=new File(_rootDataDir,"icons");
    _itemsDir=new File(_rootDataDir,"items");
    _recipesDir=new File(_rootDataDir,"recipes");
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
   * Get the root directory for lore data storage.
   * @return a directory.
   */
  public File getLoreDir()
  {
    return _loreDir;
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
   * Get the root directory for icons data storage.
   * @return a directory.
   */
  public File getIconsDir()
  {
    return _iconsDir;
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
   * Get the root directory for items data storage.
   * @return a directory.
   */
  public File getItemsDir()
  {
    return _itemsDir;
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
}
