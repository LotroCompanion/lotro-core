package delta.games.lotro.lore.titles;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.titles.io.xml.TitleXMLParser;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Facade for titles access.
 * @author DAM
 */
public class TitlesManager
{
  private static final Logger _logger=LotroLoggers.getLotroLogger();

  private static TitlesManager _instance=null;

  private HashMap<Integer,TitleDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static TitlesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new TitlesManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the titles database shall be loaded or not.
   */
  private TitlesManager(boolean load)
  {
    _cache=new HashMap<Integer,TitleDescription>(1000);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Constructor.
   * @param titles Titles to use.
   */
  public TitlesManager(List<TitleDescription> titles)
  {
    this(false);
    for(TitleDescription title : titles)
    {
      _cache.put(Integer.valueOf(title.getIdentifier()),title);
    }
  }

  /**
   * Load all titles.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File titlesFile=cfg.getFile(DataFiles.TITLES);
    long now=System.currentTimeMillis();
    List<TitleDescription> titles=new TitleXMLParser().parseXML(titlesFile);
    for(TitleDescription title : titles)
    {
      _cache.put(Integer.valueOf(title.getIdentifier()),title);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    _logger.info("Loaded "+_cache.size()+" titles in "+duration+"ms.");
  }

  /**
   * Get a list of all titles, sorted by identifier.
   * @return A list of titles.
   */
  public List<TitleDescription> getAll()
  {
    ArrayList<TitleDescription> titles=new ArrayList<TitleDescription>();
    titles.addAll(_cache.values());
    Collections.sort(titles,new IdentifiableComparator<TitleDescription>());
    return titles;
  }

  /**
   * Get a title using its identifier.
   * @param id Title identifier.
   * @return A title description or <code>null</code> if not found.
   */
  public TitleDescription getTitle(int id)
  {
    TitleDescription ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }

  /**
   * Get all title categories.
   * @return a sorted list of title categories.
   */
  public List<String> getCategories()
  {
    Set<String> categories=new HashSet<String>();
    for(TitleDescription title : _cache.values())
    {
      categories.add(title.getCategory());
    }
    List<String> ret=new ArrayList<String>(categories);
    Collections.sort(ret);
    return ret;
  }
}
