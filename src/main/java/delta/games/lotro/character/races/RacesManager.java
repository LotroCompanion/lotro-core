package delta.games.lotro.character.races;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.character.races.io.xml.RaceDescriptionXMLParser;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for access to race descriptions.
 * @author DAM
 */
public class RacesManager
{
  private static final Logger LOGGER=Logger.getLogger(RacesManager.class);

  private static RacesManager _instance=null;

  private HashMap<Integer,RaceDescription> _cacheByID;
  private HashMap<Integer,RaceDescription> _cacheByCode;
  private HashMap<String,RaceDescription> _cacheByKey;
  private HashMap<String,RaceDescription> _cacheByLegacyLabel;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static RacesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new RacesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private RacesManager()
  {
    _cacheByID=new HashMap<Integer,RaceDescription>(10);
    _cacheByCode=new HashMap<Integer,RaceDescription>(10);
    _cacheByKey=new HashMap<String,RaceDescription>(10);
    _cacheByLegacyLabel=new HashMap<String,RaceDescription>(10);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cacheByID.clear();
    _cacheByCode.clear();
    _cacheByKey.clear();
    _cacheByLegacyLabel.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File racesFile=cfg.getFile(DataFiles.RACES);
    long now=System.currentTimeMillis();
    List<RaceDescription> raceDescriptions=RaceDescriptionXMLParser.parseRaceDescriptionsFile(racesFile);
    for(RaceDescription raceDescription : raceDescriptions)
    {
      // ID
      int id=raceDescription.getIdentifier();
      _cacheByID.put(Integer.valueOf(id),raceDescription);
      // Code
      int code=raceDescription.getCode();
      _cacheByCode.put(Integer.valueOf(code),raceDescription);
      // Key
      _cacheByKey.put(raceDescription.getKey(),raceDescription);
      // Legacy label
      String legacyLabel=raceDescription.getLegacyLabel();
      if (legacyLabel!=null)
      {
        _cacheByLegacyLabel.put(raceDescription.getLegacyLabel(),raceDescription);
      }
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    int nbRaces=_cacheByID.size();
    LOGGER.info("Loaded "+nbRaces+" races in "+duration+"ms.");
  }

  /**
   * Get all races.
   * @return A list of races, sorted by identifier.
   */
  public List<RaceDescription> getAll()
  {
    ArrayList<RaceDescription> races=new ArrayList<RaceDescription>();
    races.addAll(_cacheByID.values());
    Collections.sort(races,new IdentifiableComparator<RaceDescription>());
    return races;
  }

  /**
   * Get a race description using its key.
   * @param key Key to use.
   * @return A race description or <code>null</code> if not found.
   */
  public RaceDescription getByKey(String key)
  {
    RaceDescription ret=_cacheByKey.get(key);
    return ret;
  }

  /**
   * Get a race description using its code.
   * @param code Code to use.
   * @return A race description or <code>null</code> if not found.
   */
  public RaceDescription getByCode(int code)
  {
    RaceDescription ret=_cacheByCode.get(Integer.valueOf(code));
    return ret;
  }

  /**
   * Get a race description using a persistence key.
   * @param value Input value (a race key or a race legacy label).
   * @return A race description or <code>null</code> if not found.
   */
  public RaceDescription getByPersistenceKey(String value)
  {
    RaceDescription ret=getByKey(value);
    if (ret==null)
    {
      ret=_cacheByLegacyLabel.get(value);
    }
    return ret;
  }

  /**
   * Get race and gender using the avatar ID.
   * @param avatarID Avatar ID.
   * @return A race and gender, or <code>null</code> if not found.
   */
  public RaceGender findByAvatarID(int avatarID)
  {
    for(RaceDescription race : _cacheByKey.values())
    {
      // Male?
      RaceGender maleGender=race.getMaleGender();
      if ((maleGender!=null) && (maleGender.getAvatarId()==avatarID))
      {
        return maleGender;
      }
      // Female?
      RaceGender femaleGender=race.getFemaleGender();
      if ((femaleGender!=null) && (femaleGender.getAvatarId()==avatarID))
      {
        return femaleGender;
      }
    }
    return null;
  }
}
