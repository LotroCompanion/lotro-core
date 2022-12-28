package delta.games.lotro.config;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;

import delta.common.utils.environment.EnvironmentResolver;
import delta.common.utils.io.StreamTools;
import delta.common.utils.misc.Preferences;
import delta.common.utils.misc.TypedProperties;
import delta.common.utils.url.URLTools;

/**
 * Configuration.
 * @author DAM
 */
public final class LotroCoreConfig
{
  private static final Logger LOGGER=Logger.getLogger(LotroCoreConfig.class);

  private static LotroCoreConfig _instance=new LotroCoreConfig();

  // Locations
  private File _rootDir;
  private TypedProperties _locations;

  // Parameters
  private TypedProperties _parameters;

  // User data directory
  private File _userDataDir;

  // Preferences
  private Preferences _preferences;

  // Data Configuration
  private DataConfiguration _dataConfiguration;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static LotroCoreConfig getInstance()
  {
    return _instance;
  }

  /**
   * Indicates if we're using the live version.
   * @return <code>true</code> if we do, <code>false</code> otherwise.
   */
  public static boolean isLive()
  {
    String mode=getMode();
    return ((mode.isEmpty()) || ("live".equals(mode)));
  }

  /**
   * Get the current application mode.
   * @return A mode (possibly empty but never <code>null</code>).
   */
  public static String getMode()
  {
    String mode=System.getProperty("lc.mode");
    return (mode!=null)?mode:"";
  }

  private static String getLocationFile()
  {
    String mode=getMode();
    return (mode.isEmpty()?"locations":("locations_"+mode));
  }

  /**
   * Private constructor.
   */
  private LotroCoreConfig()
  {
    String propsPath=getLocationFile()+".properties";
    _locations=getLocations(propsPath);
    String path=_locations.getStringProperty(DataFiles.ROOT,null);
    _rootDir=new File(path);

    // Parameters
    File parametersFile=getFile(DataFiles.PARAMETERS);
    _parameters=new TypedProperties();
    if (parametersFile.canRead())
    {
      _parameters.loadFromFile(parametersFile);
    }

    // User data dir
    initDataDir();
    // Preferences
    File preferencesDir=new File(_userDataDir,"preferences");
    _preferences=new Preferences(preferencesDir);
    // Data Configuration
    _dataConfiguration=initDataConfiguration();
  }

  private TypedProperties getLocations(String propsPath)
  {
    TypedProperties props=null;
    URL url=URLTools.getFromClassPath(propsPath, getClass().getClassLoader());
    if (url==null)
    {
      url=URLTools.getFromClassPath(propsPath,this);
    }
    InputStream is=null;
    try
    {
      is=url.openStream();
      props=new TypedProperties();
      props.loadFromInputStream(is);
    }
    catch(Throwable t)
    {
      LOGGER.error("Could not load locations!",t);
    }
    finally
    {
      StreamTools.close(is);
    }
    return props;
  }

  /**
   * Get a file path.
   * @param id Location identifier.
   * @return An absolute file or <code>null</code>.
   */
  public File getFile(String id)
  {
    String path=_locations.getStringProperty(id,null);
    if (path==null)
    {
      return null;
    }
    File ret=new File(_rootDir,path);
    return ret;
  }

  /**
   * Get the configuration parameters.
   * @return the configuration parameters.
   */
  public TypedProperties getParameters()
  {
    return _parameters;
  }

  private void initDataDir()
  {
    String userDataRootDirStr=_parameters.getStringProperty("user.data.root.dir","${user.home}/.lotrocompanion/data");
    userDataRootDirStr=EnvironmentResolver.resolveEnvironment(userDataRootDirStr);
    _userDataDir=new File(userDataRootDirStr);
  }

  private DataConfiguration initDataConfiguration()
  {
    DataConfiguration cfg=new DataConfiguration(_userDataDir);
    cfg.fromPreferences(_preferences);
    return cfg;
  }

  /**
   * Get the preferences manager.
   * @return the preferences manager.
   */
  public Preferences getPreferences()
  {
    return _preferences;
  }

  /**
   * Get the data configuration.
   * @return the data configuration.
   */
  public DataConfiguration getDataConfiguration()
  {
    return _dataConfiguration;
  }

  /**
   * Get the maximum character level.
   * @return the maximum character level.
   */
  public int getMaxCharacterLevel()
  {
    TypedProperties props=getParameters();
    int maxLevel=props.getIntProperty("max.character.level",115);
    return maxLevel;
  }

  /**
   * Get the maximum virtue rank.
   * @return the maximum virtue rank.
   */
  public int getMaxVirtueRank()
  {
    TypedProperties props=getParameters();
    int maxVirtueRank=props.getIntProperty("max.virtue.rank",80);
    return maxVirtueRank;
  }

  /**
   * Get the maximum item level for a legendary item.
   * @return an item level.
   */
  public int getMaxItemLevelForLI()
  {
    TypedProperties props=getParameters();
    int maxLIItemLevel=props.getIntProperty("max.li.itemLevel",470);
    return maxLIItemLevel;
  }
}
