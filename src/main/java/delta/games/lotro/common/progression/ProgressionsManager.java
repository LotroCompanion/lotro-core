package delta.games.lotro.common.progression;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.LotroCoreConfig;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.utils.maths.Progression;
import delta.games.lotro.utils.maths.io.xml.ProgressionsXMLParser;

/**
 * Manager for progression curves.
 * @author DAM
 */
public class ProgressionsManager
{
  private static final Logger LOGGER=Logger.getLogger(ProgressionsManager.class);

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
    File loreDir=cfg.getLoreDir();
    File progressionsFile=new File(loreDir,"progressions.xml");
    long now=System.currentTimeMillis();
    List<Progression> progressions=ProgressionsXMLParser.parseProgressions(progressionsFile);
    for(Progression progression : progressions)
    {
      _map.put(Integer.valueOf(progression.getIdentifier()),progression);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_map.size()+" progressions in "+duration+"ms.");
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
}
