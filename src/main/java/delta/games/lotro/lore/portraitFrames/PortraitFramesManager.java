package delta.games.lotro.lore.portraitFrames;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.portraitFrames.io.xml.PortraitFramesXMLParser;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for portrait frames access.
 * @author DAM
 */
public class PortraitFramesManager
{
  private static PortraitFramesManager _instance=null;

  private HashMap<Integer,PortraitFrameDescription> _cacheById;
  private HashMap<Integer,PortraitFrameDescription> _cacheByCode;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static PortraitFramesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new PortraitFramesManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the data shall be loaded or not.
   */
  private PortraitFramesManager(boolean load)
  {
    _cacheById=new HashMap<Integer,PortraitFrameDescription>(50);
    _cacheByCode=new HashMap<Integer,PortraitFrameDescription>(50);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all portrait frames.
   */
  private void loadAll()
  {
    _cacheById.clear();
    _cacheByCode.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File portraitFramesFile=cfg.getFile(DataFiles.PORTRAIT_FRAMES);
    long now=System.currentTimeMillis();
    List<PortraitFrameDescription> portraitFrames=new PortraitFramesXMLParser().parseXML(portraitFramesFile);
    for(PortraitFrameDescription portraitFrame : portraitFrames)
    {
      _cacheById.put(Integer.valueOf(portraitFrame.getIdentifier()),portraitFrame);
      _cacheByCode.put(Integer.valueOf(portraitFrame.getCode()),portraitFrame);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cacheById.size(),"portrait frames",duration);
  }

  /**
   * Get a list of all portrait frames groups, sorted by identifier.
   * @return A list of portrait frames.
   */
  public List<PortraitFrameDescription> getAll()
  {
    ArrayList<PortraitFrameDescription> portraitFrames=new ArrayList<PortraitFrameDescription>();
    portraitFrames.addAll(_cacheById.values());
    Collections.sort(portraitFrames,new IdentifiableComparator<PortraitFrameDescription>());
    return portraitFrames;
  }

  /**
   * Get a portrait frame using its identifier.
   * @param id Identifier.
   * @return A portrait frame description or <code>null</code> if not found.
   */
  public PortraitFrameDescription getPortraitFrameById(int id)
  {
    PortraitFrameDescription ret=_cacheById.get(Integer.valueOf(id));
    return ret;
  }

  /**
   * Get a portrait frame using its code.
   * @param code code.
   * @return A portrait frame description or <code>null</code> if not found.
   */
  public PortraitFrameDescription getPortraitFrameByCode(int code)
  {
    PortraitFrameDescription ret=_cacheByCode.get(Integer.valueOf(code));
    return ret;
  }
}
