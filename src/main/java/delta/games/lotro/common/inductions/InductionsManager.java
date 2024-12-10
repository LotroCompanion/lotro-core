package delta.games.lotro.common.inductions;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.inductions.io.xml.InductionXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for inductions access.
 * @author DAM
 */
public class InductionsManager
{
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
   * @param load Indicates if the inductions database shall be loaded or not.
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
    List<Induction> inductions=new InductionXMLParser().parseXML(inductionsFile);
    for(Induction induction : inductions)
    {
      _cache.put(Integer.valueOf(induction.getIdentifier()),induction);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"inductions",duration);
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
