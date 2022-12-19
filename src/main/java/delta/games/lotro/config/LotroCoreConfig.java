package delta.games.lotro.config;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;

import delta.common.utils.io.StreamTools;
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
  // Parameters
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
    String path=_locations.getStringProperty("root",null);
    _rootDir=new File(path);

    // Parameters
    File parametersFile=getFile(DataFiles.PARAMETERS);
    _parameters=new TypedProperties();
    if (parametersFile.canRead())
    {
      _parameters.loadFromFile(parametersFile);
    }

    // User data
    File userHomeDir=new File(System.getProperty("user.home"));
    File userApplicationDir=new File(userHomeDir,".lotrocompanion");
    _userDataDir=new File(userApplicationDir,"data");
    _toonsDir=new File(_userDataDir,"characters");
    _accountsDir=new File(_userDataDir,"accounts");
    _kinshipsDir=new File(_userDataDir,"kinships");
    _traitTreeSetupDir=new File(_userDataDir,"traitTrees");
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
