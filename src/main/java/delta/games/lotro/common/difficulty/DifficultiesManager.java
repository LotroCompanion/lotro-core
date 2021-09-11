package delta.games.lotro.common.difficulty;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.difficulty.io.xml.DifficultyXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for access to difficulties.
 * @author DAM
 */
public class DifficultiesManager
{
  private static final Logger LOGGER=Logger.getLogger(DifficultiesManager.class);

  private static DifficultiesManager _instance=null;

  private List<Difficulty> _difficulties;
  private HashMap<Integer,Difficulty> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static DifficultiesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new DifficultiesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public DifficultiesManager()
  {
    _difficulties=new ArrayList<Difficulty>();
    _cache=new HashMap<Integer,Difficulty>(10);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File difficultiesFile=cfg.getFile(DataFiles.DIFFICULTIES);
    long now=System.currentTimeMillis();
    List<Difficulty> difficulties=DifficultyXMLParser.parseXML(difficultiesFile);
    for(Difficulty difficulty : difficulties)
    {
      registerDifficulty(difficulty);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" difficulties in "+duration+"ms.");
  }

  /**
   * Register a new difficulty.
   * @param difficulty Difficulty to register.
   */
  public void registerDifficulty(Difficulty difficulty)
  {
    _difficulties.add(difficulty);
    _cache.put(Integer.valueOf(difficulty.getCode()),difficulty);
  }

  /**
   * Get a list of all difficulties.
   * @return A list of difficulties.
   */
  public List<Difficulty> getAll()
  {
    ArrayList<Difficulty> difficulties=new ArrayList<Difficulty>(_difficulties);
    return difficulties;
  }

  /**
   * Get a difficulty using its code.
   * @param code Difficulty code.
   * @return A difficulty or <code>null</code> if not found.
   */
  public Difficulty getDifficulty(int code)
  {
    Difficulty ret=null;
    ret=_cache.get(Integer.valueOf(code));
    return ret;
  }
}
