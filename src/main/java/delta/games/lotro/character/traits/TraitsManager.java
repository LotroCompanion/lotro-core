package delta.games.lotro.character.traits;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.character.traits.io.xml.TraitDescriptionXMLParser;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.enums.TraitNature;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for access to traits.
 * @author DAM
 */
public class TraitsManager
{
  private static final Logger LOGGER=Logger.getLogger(TraitsManager.class);

  private static TraitsManager _instance=null;

  private HashMap<Integer,TraitDescription> _cache;
  private HashMap<String,TraitDescription> _mapByKey;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static TraitsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new TraitsManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public TraitsManager()
  {
    _cache=new HashMap<Integer,TraitDescription>(100);
    _mapByKey=new HashMap<String,TraitDescription>();
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File traitsFile=cfg.getFile(DataFiles.TRAITS);
    if (!traitsFile.canRead())
    {
      return;
    }
    long now=System.currentTimeMillis();
    List<TraitDescription> traits=new TraitDescriptionXMLParser().parseTraitsFile(traitsFile);
    for(TraitDescription trait : traits)
    {
      registerTrait(trait);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" traits in "+duration+"ms.");
  }

  /**
   * Register a new trait.
   * @param trait Trait to register.
   */
  public void registerTrait(TraitDescription trait)
  {
    _cache.put(Integer.valueOf(trait.getIdentifier()),trait);
    String key=trait.getKey();
    if (key.length()>0)
    {
      _mapByKey.put(key,trait);
    }
  }

  /**
   * Get a list of all traits, sorted by identifier.
   * @return A list of traits.
   */
  public List<TraitDescription> getAll()
  {
    ArrayList<TraitDescription> traits=new ArrayList<TraitDescription>();
    traits.addAll(_cache.values());
    Collections.sort(traits,new IdentifiableComparator<TraitDescription>());
    return traits;
  }

  /**
   * Get a trait using its identifier.
   * @param id Trait identifier.
   * @return A trait description or <code>null</code> if not found.
   */
  public TraitDescription getTrait(int id)
  {
    TraitDescription ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }

  /**
   * Get the traits for a given nature.
   * @param traitNature Trait nature.
   * @return A list of traits, sorted by ID.
   */
  public List<TraitDescription> getTraitsForNature(TraitNature traitNature)
  {
    List<TraitDescription> ret=new ArrayList<TraitDescription>();
    for(TraitDescription trait : _cache.values())
    {
      if (trait.getNature()==traitNature)
      {
        ret.add(trait);
      }
    }
    Collections.sort(ret,new IdentifiableComparator<TraitDescription>());
    return ret;
  }

  /**
   * Get a trait using its key.
   * @param key Trait key.
   * @return A trait description or <code>null</code> if not found.
   */
  public TraitDescription getTraitByKey(String key)
  {
    return _mapByKey.get(key);
  }
}
