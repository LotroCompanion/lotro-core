package delta.games.lotro.common.progression;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.utils.maths.Progression;
import delta.games.lotro.utils.maths.io.xml.ProgressionSaxParser;
import delta.games.lotro.utils.maths.io.xml.ProgressionsXMLWriter;

/**
 * Manager for progression curves.
 * @author DAM
 */
public class ProgressionsManager
{
  private static final Logger LOGGER=LoggerFactory.getLogger(ProgressionsManager.class);

  private static ProgressionsManager _instance=null;

  private Map<Integer,Progression> _map;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static ProgressionsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new ProgressionsManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public ProgressionsManager()
  {
    _map=new HashMap<Integer,Progression>();
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _map.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File progressionsFile=cfg.getFile(DataFiles.PROGRESSIONS);
    loadFromFile(progressionsFile);
  }

  /**
   * Load progression from a file.
   * @param from Source file.
   */
  public void loadFromFile(File from)
  {
    if (from.canRead())
    {
      long now=System.currentTimeMillis();
      List<Progression> progressions=ProgressionSaxParser.parseProgressionsFile(from);
      for(Progression progression : progressions)
      {
        _map.put(Integer.valueOf(progression.getIdentifier()),progression);
      }
      long now2=System.currentTimeMillis();
      long duration=now2-now;
      LOGGER.info("Loaded "+_map.size()+" progressions from file "+from+" in "+duration+"ms.");
    }
  }

  /**
   * Get a progression using its identifier.
   * @param id Identifier of the progression to get.
   * @return A progression or <code>null</code> if not found.
   */
  public Progression getProgression(int id)
  {
    return _map.get(Integer.valueOf(id));
  }

  /**
   * Register a new progression.
   * @param id Progression ID.
   * @param progression Progression to register.
   */
  public void registerProgression(int id, Progression progression)
  {
    _map.put(Integer.valueOf(id),progression);
  }

  /**
   * Get a list of all progressions, sorted by identifier.
   * @return A list of progressions.
   */
  public List<Progression> getAll()
  {
    ArrayList<Progression> progressions=new ArrayList<Progression>();
    progressions.addAll(_map.values());
    Collections.sort(progressions,new IdentifiableComparator<Progression>());
    return progressions;
  }

  /**
   * Write the managed progressions to a XML file.
   * @param toFile File to write to.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeToFile(File toFile)
  {
    int nbProgressions=_map.size();
    LOGGER.info("Writing "+nbProgressions+" progressions to: "+toFile);
    List<Progression> progressions=new ArrayList<Progression>(_map.values());
    Collections.sort(progressions,new IdentifiableComparator<Progression>());
    return ProgressionsXMLWriter.write(toFile,progressions);
  }
}
