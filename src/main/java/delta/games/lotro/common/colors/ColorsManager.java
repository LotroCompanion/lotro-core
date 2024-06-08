package delta.games.lotro.common.colors;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.colors.io.xml.ColorXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for access to colors.
 * @author DAM
 */
public class ColorsManager
{
  private static final Logger LOGGER=Logger.getLogger(ColorsManager.class);

  private static ColorsManager _instance=null;

  private List<ColorDescription> _colors;
  private HashMap<Float,ColorDescription> _cache;
  private HashMap<Integer,ColorDescription> _intCodeCache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static ColorsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new ColorsManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public ColorsManager()
  {
    _colors=new ArrayList<ColorDescription>();
    _cache=new HashMap<Float,ColorDescription>(10);
    _intCodeCache=new HashMap<Integer,ColorDescription>(10);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File colorsFile=cfg.getFile(DataFiles.COLORS);
    long now=System.currentTimeMillis();
    List<ColorDescription> colors=new ColorXMLParser().parseXML(colorsFile);
    for(ColorDescription color : colors)
    {
      registerColor(color);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" colors in "+duration+"ms.");
  }

  /**
   * Register a new color.
   * @param color Color to register.
   */
  public void registerColor(ColorDescription color)
  {
    _colors.add(color);
    _cache.put(Float.valueOf(color.getCode()),color);
    _intCodeCache.put(Integer.valueOf(color.getIntCode()),color);
  }

  /**
   * Get a list of all colors.
   * @return A list of colors.
   */
  public List<ColorDescription> getAll()
  {
    ArrayList<ColorDescription> colors=new ArrayList<ColorDescription>(_colors);
    return colors;
  }

  /**
   * Get a color using its code.
   * @param code Color code.
   * @return A color or <code>null</code> if not found.
   */
  public ColorDescription getColor(float code)
  {
    ColorDescription ret=null;
    ret=_cache.get(Float.valueOf(code));
    return ret;
  }

  /**
   * Get a color using its integer code.
   * @param code Color code.
   * @return A color or <code>null</code> if not found.
   */
  public ColorDescription getColor(int code)
  {
    ColorDescription ret=null;
    ret=_intCodeCache.get(Integer.valueOf(code));
    return ret;
  }
}
