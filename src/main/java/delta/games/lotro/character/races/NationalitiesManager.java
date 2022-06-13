package delta.games.lotro.character.races;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.character.races.io.xml.NationalityDescriptionXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for access to nationalities descriptions.
 * @author DAM
 */
public class NationalitiesManager
{
  private static final Logger LOGGER=Logger.getLogger(NationalitiesManager.class);

  private static NationalitiesManager _instance=null;

  private HashMap<Integer,NationalityDescription> _cache;
  private HashMap<String,NationalityDescription> _mapByName;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static NationalitiesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new NationalitiesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private NationalitiesManager()
  {
    _cache=new HashMap<Integer,NationalityDescription>(10);
    _mapByName=new HashMap<String,NationalityDescription>(10);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File nationalitiesFile=cfg.getFile(DataFiles.NATIONALITIES);
    long now=System.currentTimeMillis();
    List<NationalityDescription> nationalityDescriptions=NationalityDescriptionXMLParser.parseNationalitiesFile(nationalitiesFile);
    for(NationalityDescription nationalityDescription : nationalityDescriptions)
    {
      register(nationalityDescription);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" nationalities in "+duration+"ms.");
  }

  private void register(NationalityDescription nationalityDescription)
  {
    // ID
    _cache.put(Integer.valueOf(nationalityDescription.getIdentifier()),nationalityDescription);
    // Name
    _mapByName.put(nationalityDescription.getName(),nationalityDescription);
    // Alias
    for(String alias : nationalityDescription.getAliases())
    {
      _mapByName.put(alias,nationalityDescription);
    }
  }

  /**
   * Get a nationality description using its code.
   * @param code Code of the nationality to get.
   * @return A nationality description or <code>null</code> if not found.
   */
  public NationalityDescription getNationalityDescription(int code)
  {
    return _cache.get(Integer.valueOf(code));
  }

  /**
   * Get a nationality description using its name/alias.
   * @param name Name/alias of the nationality to get.
   * @return A nationality description or <code>null</code> if not found.
   */
  public NationalityDescription getNationalityDescriptionByName(String name)
  {
    return _mapByName.get(name);
  }
}
