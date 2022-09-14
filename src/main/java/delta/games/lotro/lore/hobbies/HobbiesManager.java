package delta.games.lotro.lore.hobbies;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.hobbies.io.xml.HobbyDescriptionXMLParser;

/**
 * Facade for access to hobbies.
 * @author DAM
 */
public class HobbiesManager
{
  private static final Logger LOGGER=Logger.getLogger(HobbiesManager.class);

  private static HobbiesManager _instance=null;

  private HashMap<Integer,HobbyDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static HobbiesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new HobbiesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private HobbiesManager()
  {
    _cache=new HashMap<Integer,HobbyDescription>(1);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File hobbiesFile=cfg.getFile(DataFiles.HOBBIES);
    long now=System.currentTimeMillis();
    if ((hobbiesFile.exists()) && (hobbiesFile.canRead()))
    {
      List<HobbyDescription> hobbies=HobbyDescriptionXMLParser.parseHobbiesFile(hobbiesFile);
      for(HobbyDescription hobby : hobbies)
      {
        registerHobby(hobby);
      }
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" hobbies in "+duration+"ms.");
  }

  /**
   * Register a new hobby.
   * @param hobby Hobby to register.
   */
  public void registerHobby(HobbyDescription hobby)
  {
    _cache.put(Integer.valueOf(hobby.getIdentifier()),hobby);
  }

  /**
   * Get a list of all hobbies, sorted by identifier.
   * @return A list of hobbies.
   */
  public List<HobbyDescription> getAll()
  {
    ArrayList<HobbyDescription> hobbies=new ArrayList<HobbyDescription>();
    hobbies.addAll(_cache.values());
    Collections.sort(hobbies,new IdentifiableComparator<HobbyDescription>());
    return hobbies;
  }

  /**
   * Get a hobby using its identifier.
   * @param id Hobby identifier.
   * @return A hobby description or <code>null</code> if not found.
   */
  public HobbyDescription getHobby(int id)
  {
    HobbyDescription ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
