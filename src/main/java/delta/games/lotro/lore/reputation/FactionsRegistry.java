package delta.games.lotro.lore.reputation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.reputation.io.xml.FactionsXMLParser;

/**
 * Factions registry.
 * @author DAM
 */
public final class FactionsRegistry
{
  private static FactionsRegistry _instance;

  private HashMap<Integer,Faction> _registryById;
  private HashMap<String,Faction> _registryByKey;
  private List<String> _categories;
  private HashMap<String,List<Faction>> _factionsByCategory;
  private List<ReputationDeed> _factionDeeds;
  private List<Faction> _factions;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static FactionsRegistry getInstance()
  {
    synchronized(FactionsRegistry.class)
    {
      if (_instance==null)
      {
        _instance=loadRegistry();
      }
    }
    return _instance;
  }

  private static FactionsRegistry loadRegistry()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File registryFile=cfg.getFile(DataFiles.FACTIONS);
    FactionsXMLParser parser=new FactionsXMLParser();
    FactionsRegistry registry=parser.parseXML(registryFile);
    if (registry==null)
    {
      registry=new FactionsRegistry();
    }
    return registry;
  }

  /**
   * Constructor.
   */
  public FactionsRegistry()
  {
    _registryById=new HashMap<Integer,Faction>();
    _registryByKey=new HashMap<String,Faction>();
    _categories=new ArrayList<String>();
    _factionsByCategory=new HashMap<String,List<Faction>>();
    _factionDeeds=new ArrayList<ReputationDeed>();
    _factions=new ArrayList<Faction>();
  }

  /**
   * Register a new faction.
   * @param faction Faction to register.
   */
  public void registerFaction(Faction faction)
  {
    // Category
    String category=faction.getCategory();
    if (category.length()>0)
    {
      List<Faction> factionsForCategory=_factionsByCategory.get(category);
      if (factionsForCategory==null)
      {
        _categories.add(category);
        factionsForCategory=new ArrayList<Faction>();
        _factionsByCategory.put(category,factionsForCategory);
      }
      factionsForCategory.add(faction);
    }
    // Map by ID
    int id=faction.getIdentifier();
    if (id!=0)
    {
      _registryById.put(Integer.valueOf(id),faction);
      _registryByKey.put(String.valueOf(id),faction);
    }
    // Map by key
    String factionKey=faction.getLegacyKey();
    if (factionKey!=null)
    {
      _registryByKey.put(factionKey,faction);
    }
    // Register
    _factions.add(faction);
  }

  /**
   * Get all known factions.
   * @return A list of all factions.
   */
  public List<Faction> getAll()
  {
    List<Faction> ret=new ArrayList<Faction>(_factions);
    return ret;
  }

  /**
   * Get the faction deeds.
   * @return A list of faction deed keys.
   */
  public List<ReputationDeed> getReputationDeeds()
  {
    return _factionDeeds;
  }

  /**
   * Register a reputation deed.
   * @param deed Deed to add.
   */
  public void addDeed(ReputationDeed deed)
  {
    _factionDeeds.add(deed);
  }

  /**
   * Get the faction categories.
   * @return A list of faction categories keys.
   */
  public List<String> getFactionCategories()
  {
    return _categories;
  }

  /**
   * Get the factions involved in a category.
   * @param category Key of the targeted category.
   * @return A list of factions.
   */
  public List<Faction> getFactionsForCategory(String category)
  {
    return _factionsByCategory.get(category);
  }

  /**
   * Get a faction instance using its identifier.
   * @param identifier Identifier of the faction to get.
   * @return A faction instance or <code>null</code> if not found.
   */
  public Faction getById(int identifier)
  {
    return _registryById.get(Integer.valueOf(identifier));
  }

  /**
   * Get a faction instance using its legacy key.
   * @param key Key of the faction to get.
   * @return A faction instance or <code>null</code> if not found.
   */
  public Faction getByKey(String key)
  {
    return _registryByKey.get(key);
  }
}
