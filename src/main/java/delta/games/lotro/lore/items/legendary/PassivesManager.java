package delta.games.lotro.lore.items.legendary;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.io.xml.EffectXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.legendary.passives.PassivesGroup;
import delta.games.lotro.lore.items.legendary.passives.PassivesGroupsManager;
import delta.games.lotro.lore.items.legendary.passives.io.xml.PassivesGroupsXMLParser;

/**
 * Facade for access to passives.
 * @author DAM
 */
public class PassivesManager
{
  private static final Logger LOGGER=Logger.getLogger(PassivesManager.class);

  private static PassivesManager _instance=null;

  private HashMap<Integer,Effect> _cache;
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
    _cache=new HashMap<Integer,Effect>(100);
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
    List<Effect> effects=EffectXMLParser.parseEffectsFile(legaciesFile);
    for(Effect effect : effects)
    {
      registerEffect(effect);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" passives in "+duration+"ms.");
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
    LOGGER.info("Loaded passives usage in "+duration+"ms.");
  }

  /**
   * Register a new effect.
   * @param effect Effect to register.
   */
  public void registerEffect(Effect effect)
  {
    _cache.put(Integer.valueOf(effect.getIdentifier()),effect);
  }

  /**
   * Get a list of all effects, sorted by identifier.
   * @return A list of effects.
   */
  public List<Effect> getAll()
  {
    ArrayList<Effect> effects=new ArrayList<Effect>();
    effects.addAll(_cache.values());
    Collections.sort(effects,new IdentifiableComparator<Effect>());
    return effects;
  }

  /**
   * Get an effect using its identifier.
   * @param id Effect identifier.
   * @return An effect or <code>null</code> if not found.
   */
  public Effect getEffect(int id)
  {
    Effect ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }

  /**
   * Get all possible passives for an item.
   * @param itemId Item identifier.
   * @return A list of passives, sorted by their identifier.
   */
  public List<Effect> getPassivesForItem(int itemId)
  {
    List<Effect> passives=new ArrayList<Effect>();
    List<Integer> passiveIds=new ArrayList<Integer>(_passivesUsage.getPassiveIdsForItem(itemId));
    Collections.sort(passiveIds);
    for(Integer passiveId : passiveIds)
    {
      Effect passive=getEffect(passiveId.intValue());
      if (passive!=null)
      {
        passives.add(passive);
      }
    }
    return passives;
  }
}
