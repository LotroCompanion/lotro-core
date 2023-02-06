package delta.games.lotro.lore.items.legendary.titles;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.legendary.titles.io.xml.LegendaryTitleXMLParser;

/**
 * Facade for access to legendary titles.
 * @author DAM
 */
public class LegendaryTitlesManager
{
  private static final Logger LOGGER=Logger.getLogger(LegendaryTitlesManager.class);

  private static LegendaryTitlesManager _instance=null;

  private HashMap<Integer,LegendaryTitle> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static LegendaryTitlesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new LegendaryTitlesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public LegendaryTitlesManager()
  {
    _cache=new HashMap<Integer,LegendaryTitle>(100);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File legendaryTitlesFile=cfg.getFile(DataFiles.LEGENDARY_TITLES);
    long now=System.currentTimeMillis();
    List<LegendaryTitle> legendaryTitles=new LegendaryTitleXMLParser().parseXML(legendaryTitlesFile);
    for(LegendaryTitle legendaryTitle : legendaryTitles)
    {
      registerLegendaryTitle(legendaryTitle);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" legendary titles in "+duration+"ms.");
  }

  /**
   * Register a new legendary title.
   * @param legendaryTitle legendary title to register.
   */
  public void registerLegendaryTitle(LegendaryTitle legendaryTitle)
  {
    _cache.put(Integer.valueOf(legendaryTitle.getIdentifier()),legendaryTitle);
  }

  /**
   * Get a list of all legendary titles, sorted by identifier.
   * @return A list of legendary titles.
   */
  public List<LegendaryTitle> getAll()
  {
    ArrayList<LegendaryTitle> legendaryTitles=new ArrayList<LegendaryTitle>();
    legendaryTitles.addAll(_cache.values());
    Collections.sort(legendaryTitles,new IdentifiableComparator<LegendaryTitle>());
    return legendaryTitles;
  }

  /**
   * Get a legendary title using its identifier.
   * @param id Legendary title identifier.
   * @return A legendary title or <code>null</code> if not found.
   */
  public LegendaryTitle getLegendaryTitle(int id)
  {
    LegendaryTitle ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
