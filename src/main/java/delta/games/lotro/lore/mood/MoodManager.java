package delta.games.lotro.lore.mood;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.mood.io.xml.MoodXMLParser;

/**
 * Mood manager.
 * @author DAM
 */
public class MoodManager
{
  private static final Logger LOGGER=LoggerFactory.getLogger(MoodManager.class);

  private static MoodManager _instance=null;

  private HashMap<Integer,MoodEntry> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static MoodManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new MoodManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the titles database shall be loaded or not.
   */
  private MoodManager(boolean load)
  {
    _cache=new HashMap<Integer,MoodEntry>(10);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load data.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File titlesFile=cfg.getFile(DataFiles.MOOD);
    long now=System.currentTimeMillis();
    List<MoodEntry> moods=new MoodXMLParser().parseXML(titlesFile);
    for(MoodEntry mood : moods)
    {
      _cache.put(Integer.valueOf(mood.getLevel()),mood);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded mood data in "+duration+"ms.");
  }

  /**
   * Get the morale modifier for the given level.
   * @param level Level to use (positive for Hope, negative for Dread).
   * @return A morale modifier.
   */
  public float getMoraleModifier(int level)
  {
    MoodEntry entry=_cache.get(Integer.valueOf(level));
    if (entry!=null)
    {
      return entry.getMoraleModifier();
    }
    return 0.0f;
  }
}
