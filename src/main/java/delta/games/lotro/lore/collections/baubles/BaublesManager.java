package delta.games.lotro.lore.collections.baubles;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.collections.baubles.io.xml.BaublesXMLParser;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for baubles access.
 * @author DAM
 */
public class BaublesManager
{
  private static BaublesManager _instance=null;

  private HashMap<Integer,SkillDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static BaublesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new BaublesManager();
    }
    return _instance;
  }

  /**
   * Private constructor.
   */
  private BaublesManager()
  {
    _cache=new HashMap<Integer,SkillDescription>(100);
    loadAll();
  }

  /**
   * Load all baubles.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File baublesFile=cfg.getFile(DataFiles.BAUBLES);
    long now=System.currentTimeMillis();
    List<SkillDescription> baubles=new BaublesXMLParser().parseBaublesFile(baublesFile);
    for(SkillDescription bauble : baubles)
    {
      _cache.put(Integer.valueOf(bauble.getIdentifier()),bauble);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"baubles",duration);
  }

  /**
   * Get a list of all baubles, sorted by identifier.
   * @return A list of baubles.
   */
  public List<SkillDescription> getAll()
  {
    ArrayList<SkillDescription> collections=new ArrayList<SkillDescription>();
    collections.addAll(_cache.values());
    Collections.sort(collections,new IdentifiableComparator<SkillDescription>());
    return collections;
  }
}
