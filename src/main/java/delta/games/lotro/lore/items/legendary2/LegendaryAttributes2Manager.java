package delta.games.lotro.lore.items.legendary2;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.legendary2.io.xml.LegendaryAttrs2XMLParser;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for access to legendary attributes (reloaded).
 * @author DAM
 */
public class LegendaryAttributes2Manager
{
  private static LegendaryAttributes2Manager _instance=null;

  private HashMap<Integer,LegendaryAttrs2> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static LegendaryAttributes2Manager getInstance()
  {
    if (_instance==null)
    {
      _instance=new LegendaryAttributes2Manager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public LegendaryAttributes2Manager()
  {
    _cache=new HashMap<Integer,LegendaryAttrs2>(100);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File inputFile=cfg.getFile(DataFiles.LEGENDARY_ATTRS);
    long now=System.currentTimeMillis();
    List<LegendaryAttrs2> legendaryAttrs=LegendaryAttrs2XMLParser.parseLegendaryAttributesFile(inputFile);
    for(LegendaryAttrs2 legendaryAttr : legendaryAttrs)
    {
      register(legendaryAttr);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"legendary attributes",duration);
  }

  /**
   * Register a new legendary attributes.
   * @param legendaryAttributes Attributes to register.
   */
  public void register(LegendaryAttrs2 legendaryAttributes)
  {
    _cache.put(Integer.valueOf(legendaryAttributes.getIdentifier()),legendaryAttributes);
  }

  /**
   * Get a legendary attributes using its identifier.
   * @param id Item identifier.
   * @return A legendary attributes or <code>null</code> if not found.
   */
  public LegendaryAttrs2 getLegendaryAttributes(int id)
  {
    LegendaryAttrs2 ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
