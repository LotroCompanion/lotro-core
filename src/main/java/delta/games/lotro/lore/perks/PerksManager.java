package delta.games.lotro.lore.perks;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.perks.io.xml.PerkDescriptionXMLParser;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for access to perks.
 * @author DAM
 */
public class PerksManager
{
  private static PerksManager _instance=null;

  private HashMap<Integer,PerkDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static PerksManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new PerksManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private PerksManager()
  {
    _cache=new HashMap<Integer,PerkDescription>(1);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File perksFile=cfg.getFile(DataFiles.PERKS);
    long now=System.currentTimeMillis();
    if ((perksFile.exists()) && (perksFile.canRead()))
    {
      List<PerkDescription> perks=new PerkDescriptionXMLParser().parsePerksFile(perksFile);
      for(PerkDescription perk : perks)
      {
        registerPerk(perk);
      }
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"perks",duration);
  }

  /**
   * Register a new perk.
   * @param perk Perk to register.
   */
  public void registerPerk(PerkDescription perk)
  {
    _cache.put(Integer.valueOf(perk.getIdentifier()),perk);
  }

  /**
   * Get a list of all perks, sorted by identifier.
   * @return A list of perks.
   */
  public List<PerkDescription> getAll()
  {
    ArrayList<PerkDescription> perks=new ArrayList<PerkDescription>();
    perks.addAll(_cache.values());
    Collections.sort(perks,new IdentifiableComparator<PerkDescription>());
    return perks;
  }

  /**
   * Get a perk using its identifier.
   * @param id Perk identifier.
   * @return A perk description or <code>null</code> if not found.
   */
  public PerkDescription getPerk(int id)
  {
    return _cache.get(Integer.valueOf(id));
  }
}
