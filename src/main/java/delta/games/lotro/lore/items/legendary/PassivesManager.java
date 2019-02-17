package delta.games.lotro.lore.items.legendary;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.Effect;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.effects.io.xml.EffectXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for access to passives.
 * @author DAM
 */
public class PassivesManager
{
  private static final Logger LOGGER=Logger.getLogger(PassivesManager.class);

  private static PassivesManager _instance=null;

  private HashMap<Integer,Effect> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static PassivesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new PassivesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public PassivesManager()
  {
    _cache=new HashMap<Integer,Effect>(100);
  }

  /**
   * Load all.
   */
  private void loadAll()
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
    LOGGER.info("Loaded "+_cache.size()+" effects in "+duration+"ms.");
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
}
