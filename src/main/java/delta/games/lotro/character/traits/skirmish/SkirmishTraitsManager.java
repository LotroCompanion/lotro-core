package delta.games.lotro.character.traits.skirmish;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.skirmish.io.xml.SkirmishTraitsXMLParser;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.enums.TraitNature;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Skirmish traits manager.
 * @author DAM
 */
public class SkirmishTraitsManager
{
  private static final Logger LOGGER=Logger.getLogger(SkirmishTraitsManager.class);

  private static SkirmishTraitsManager _instance=null;

  private HashMap<Integer,TraitDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static SkirmishTraitsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new SkirmishTraitsManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public SkirmishTraitsManager()
  {
    _cache=new HashMap<Integer,TraitDescription>(100);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File skirmishTraitsFile=cfg.getFile(DataFiles.SKIRMISH_TRAITS);
    if (!skirmishTraitsFile.canRead())
    {
      return;
    }
    long now=System.currentTimeMillis();
    List<TraitDescription> traits=SkirmishTraitsXMLParser.parseTraitsFile(skirmishTraitsFile);
    for(TraitDescription trait : traits)
    {
      _cache.put(Integer.valueOf(trait.getIdentifier()),trait);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" skirmish traits in "+duration+"ms.");
  }

  /**
   * Get a list of all skirmish traits, sorted by identifier.
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
   * Get a list of all skirmish traits of a given nature, sorted by identifier.
   * @param nature Nature to use.
   * @return A list of traits.
   */
  public List<TraitDescription> getAll(TraitNature nature)
  {
    ArrayList<TraitDescription> traits=new ArrayList<TraitDescription>();
    for(TraitDescription trait : _cache.values())
    {
      if (trait.getNature()==nature)
      {
        traits.add(trait);
      }
    }
    Collections.sort(traits,new IdentifiableComparator<TraitDescription>());
    return traits;
  }

  /**
   * Get the managed trait natures.
   * @return A list of trait natures.
   */
  public List<TraitNature> getNatures()
  {
    Set<TraitNature> natures=new HashSet<TraitNature>();
    for(TraitDescription trait : _cache.values())
    {
      TraitNature nature=trait.getNature();
      natures.add(nature);
    }
    List<TraitNature> ret=new ArrayList<TraitNature>(natures);
    Collections.sort(ret,new LotroEnumEntryCodeComparator<TraitNature>());
    return ret;
  }
}
