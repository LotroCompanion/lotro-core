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
  private TypedProperties _locations;

  // Root directory for user data
  private File _userDataDir;
  // Account data
  private File _accountsDir;
  // Character data
  private File _toonsDir;
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
   * Private constructor.
   */
  private LotroCoreConfig()
  {
    _locations=getLocations();

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
    _traitTreeSetupDir=new File(_userDataDir,"traitTrees");
  }

  private TypedProperties getLocations()
  {
    TypedProperties props=null;
    URL url=URLTools.getFromClassPath("locations.properties", getClass().getClassLoader());
    if (url==null)
    {
      url=URLTools.getFromClassPath("locations.properties",this);
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
    return path!=null?new File(path).getAbsoluteFile():null;
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
   * Get the root storage directory for trait tree setups.
   * @return a directory.
   */
  public File getTraitTreeSetupDir()
  {
    return _traitTreeSetupDir;
  }
}
