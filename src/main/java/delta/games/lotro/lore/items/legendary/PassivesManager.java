package delta.games.lotro.lore.items.legendary;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.legendary.passives.Passive;
import delta.games.lotro.lore.items.legendary.passives.PassivesGroup;
import delta.games.lotro.lore.items.legendary.passives.PassivesGroupsManager;
import delta.games.lotro.lore.items.legendary.passives.io.xml.PassivesGroupsXMLParser;
import delta.games.lotro.lore.items.legendary.passives.io.xml.PassivesXMLParser;
import delta.games.lotro.utils.PerfUtils;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Facade for access to passives.
 * @author DAM
 */
public class PassivesManager
{
  private static PassivesManager _instance=null;

  private HashMap<Integer,Passive> _cache;
  private PassivesGroupsManager _passivesUsage;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static PassivesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new PassivesManager();
      _instance.loadData();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public PassivesManager()
  {
    _cache=new HashMap<Integer,Passive>(100);
    _passivesUsage=new PassivesGroupsManager();
  }

  private void loadData()
  {
    // Passives
    loadPassives();
    // Passives usage
    loadPassivesUsage();
  }

  /**
   * Load passives.
   */
  private void loadPassives()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File legaciesFile=cfg.getFile(DataFiles.PASSIVES);
    long now=System.currentTimeMillis();
    SingleLocaleLabelsManager labelsMgr=I18nFacade.getLabelsMgr("passives");
    List<Passive> passives=PassivesXMLParser.parsePassivesFile(legaciesFile,labelsMgr);
    for(Passive passive : passives)
    {
      registerPassive(passive);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"passives",duration);
  }

  /**
   * Load passives usage.
   */
  private void loadPassivesUsage()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File passivesUsageFile=cfg.getFile(DataFiles.PASSIVES_USAGE);
    long now=System.currentTimeMillis();
    List<PassivesGroup> groups=PassivesGroupsXMLParser.parsePassivesUsageFile(passivesUsageFile);
    for(PassivesGroup group : groups)
    {
      _passivesUsage.addPassivesGroup(group);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(groups.size(),"passives groups",duration);
  }

  /**
   * Register a new passive.
   * @param passive Passive to register.
   */
  public void registerPassive(Passive passive)
  {
    _cache.put(Integer.valueOf(passive.getIdentifier()),passive);
  }

  /**
   * Get a list of all passives, sorted by identifier.
   * @return A list of passives.
   */
  public List<Passive> getAll()
  {
    ArrayList<Passive> passives=new ArrayList<Passive>();
    passives.addAll(_cache.values());
    Collections.sort(passives,new IdentifiableComparator<Passive>());
    return passives;
  }

  /**
   * Get a passive using its identifier.
   * @param id Passive identifier.
   * @return A passive or <code>null</code> if not found.
   */
  public Passive getPassive(int id)
  {
    Passive ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }

  /**
   * Get all possible passives for an item.
   * @param itemId Item identifier.
   * @return A list of passives, sorted by their identifier.
   */
  public List<Passive> getPassivesForItem(int itemId)
  {
    List<Passive> passives=new ArrayList<Passive>();
    List<Integer> passiveIds=new ArrayList<Integer>(_passivesUsage.getPassiveIdsForItem(itemId));
    Collections.sort(passiveIds);
    for(Integer passiveId : passiveIds)
    {
      Passive passive=getPassive(passiveId.intValue());
      if (passive!=null)
      {
        passives.add(passive);
      }
    }
    return passives;
  }
}
