package delta.games.lotro.lore.pip;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.pip.io.xml.PipXMLParser;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for pips access.
 * @author DAM
 */
public class PipsManager
{
  private static PipsManager _instance=null;

  private HashMap<Integer,PipDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static PipsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new PipsManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the pips database shall be loaded or not.
   */
  private PipsManager(boolean load)
  {
    _cache=new HashMap<Integer,PipDescription>(10);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all pips.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File pipsFile=cfg.getFile(DataFiles.PIPS);
    long now=System.currentTimeMillis();
    List<PipDescription> pips=new PipXMLParser().parseXML(pipsFile);
    for(PipDescription pip : pips)
    {
      _cache.put(Integer.valueOf(pip.getIdentifier()),pip);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"pips",duration);
  }

  /**
   * Get a pip using its type.
   * @param type Type code.
   * @return A pip or <code>null</code> if not found.
   */
  public PipDescription get(int type)
  {
    return _cache.get(Integer.valueOf(type));
  }
}
