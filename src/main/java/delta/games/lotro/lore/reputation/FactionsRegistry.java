package delta.games.lotro.lore.reputation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Factions registry.
 * @author DAM
 */
public class FactionsRegistry
{
  private static FactionsRegistry _instance=new FactionsRegistry();

  private HashMap<String,Faction> _registryByKey;
  private HashMap<String,Faction> _registryByName;
  private List<String> _categories;
  private HashMap<String,List<Faction>> _factionsByCategory;
  private List<String> _factionDeeds;
  private HashMap<String,List<Faction>> _factionsByDeed;
  private List<Faction> _factions;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static FactionsRegistry getInstance()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private FactionsRegistry()
  {
    _registryByKey=new HashMap<String,Faction>();
    _registryByName=new HashMap<String,Faction>();
    _factionsByCategory=new HashMap<String,List<Faction>>();
    _factionsByDeed=new HashMap<String,List<Faction>>();
    _factions=new ArrayList<Faction>();
    initFactions();
  }

  /**
   * Initialize and register all the factions.
   */
  private void initFactions()
  {
    FactionsFactory factory=new FactionsFactory();
    // Categories
    _categories=factory.getCategories();
    for(String category : _categories)
    {
      List<Faction> factions=factory.getByCategory(category);
      _factionsByCategory.put(category,factions);
      for(Faction faction : factions)
      {
        registerFaction(faction);
      }
    }
    // Deeds
    _factionDeeds=factory.getDeeds();
    for(String deed : _factionDeeds)
    {
      List<Faction> factions=factory.getByDeed(deed);
      _factionsByDeed.put(deed,factions);
    }
  }

  /**
   * Register a new faction.
   * @param faction Faction to register.
   */
  private void registerFaction(Faction faction)
  {
    String key=faction.getKey();
    _registryByKey.put(key,faction);
    String name=faction.getName();
    String[] aliases=faction.getAliases();
    _registryByName.put(name,faction);
    for(String alias : aliases)
    {
      _registryByName.put(alias,faction);
    }
    _factions.add(faction);
  }

  /**
   * Get all known factions.
   * @return A list of all factions.
   */
  public List<Faction> getAll()
  {
    return _factions;
  }

  /**
   * Get the faction deeds.
   * @return A list of faction deed keys.
   */
  public List<String> getFactionDeeds()
  {
    return _factionDeeds;
  }

  /**
   * Get the factions involved in a faction deed.
   * @param factionDeedKey Key of the targeted faction deed.
   * @return A list of factions.
   */
  public List<Faction> getFactionsForDeed(String factionDeedKey)
  {
    return _factionsByDeed.get(factionDeedKey);
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
   * Get a faction instance by key.
   * @param key Key of the faction to get.
   * @return A faction instance or <code>null</code> if not found.
   */
  public Faction getByKey(String key)
  {
    return _registryByKey.get(key);
  }

  /**
   * Get a faction instance by name.
   * @param name Name of the faction to get.
   * @return A faction instance or <code>null</code> if <code>name</code> is <code>null</code> or empty.
   */
  public Faction getByName(String name)
  {
    Faction f=null;
    if ((name!=null) && (name.length()>0))
    {
      f=_registryByName.get(name);
    }
    return f;
  }
}
