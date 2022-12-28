package delta.games.lotro.config;

import java.io.File;

import delta.common.utils.misc.Preferences;
import delta.common.utils.misc.TypedProperties;

/**
 * Configuration for the data system.
 * @author DAM
 */
public class DataConfiguration
{
  private static final String DATA_CONFIGURATION="DataConfiguration";
  private static final String ROOT_PATH="RootPath";

  private File _rootPath;

  /**
   * Constructor.
   * @param rootPath Root path.
   */
  public DataConfiguration(File rootPath)
  {
    _rootPath=rootPath;
  }

  /**
   * Get the root path for data files.
   * @return a directory path.
   */
  public File getRootPath()
  {
    return _rootPath;
  }

  /**
   * Set the root path for data files.
   * @param rootPath A directory path.
   */
  public void setRootPath(File rootPath)
  {
    _rootPath=rootPath;
  }

  /**
   * Indicates if the given configuration contains the same data as this one.
   * @param cfg Configuration to use.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean same(DataConfiguration cfg)
  {
    return _rootPath.equals(cfg._rootPath);
  }

  @Override
  public String toString()
  {
    return "Root path: "+_rootPath;
  }

  /**
   * Save configuration.
   * @param userCfg User configuration.
   */
  public void save(UserConfig userCfg)
  {
    String userDataPath=getRootPath().getAbsolutePath();
    userCfg.setStringValue(DATA_CONFIGURATION,ROOT_PATH,userDataPath);
  }

  /**
   * Initialize from preferences.
   * @param preferences Preferences to use.
   */
  public void fromPreferences(Preferences preferences)
  {
    TypedProperties props=preferences.getPreferences(DATA_CONFIGURATION);
    String dataPath=props.getStringProperty(ROOT_PATH,_rootPath.getAbsolutePath());
    setRootPath(new File(dataPath));
  }
}
