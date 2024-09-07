package delta.games.lotro.common.inductions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for inductions access.
 * @author DAM
 */
public class InductionsManager
{
  private static final Logger LOGGER=LoggerFactory.getLogger(InductionsManager.class);

  private static InductionsManager _instance=null;

  private HashMap<Integer,Induction> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static InductionsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new InductionsManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the action tables database shall be loaded or not.
   */
  private InductionsManager(boolean load)
  {
    _cache=new HashMap<Integer,Induction>(10);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all inductions.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File inductionsFile=cfg.getFile(DataFiles.INDUCTIONS);
    long now=System.currentTimeMillis();
    List<Induction> inductions=new ArrayList<Induction>();
    //List<Induction> inductions=new ActionTablesXMLParser().parseXML(actionTablesFile);
    for(Induction induction : inductions)
    {
      _cache.put(Integer.valueOf(induction.getIdentifier()),induction);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" inductions in "+duration+"ms.");
  }

  /**
   * Get an induction using its identifier.
   * @param id Identifier.
   * @return An induction or <code>null</code> if not found.
   */
  public Induction get(int id)
  {
    return _cache.get(Integer.valueOf(id));
  }
}
