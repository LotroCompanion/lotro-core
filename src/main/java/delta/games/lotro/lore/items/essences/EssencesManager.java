package delta.games.lotro.lore.items.essences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.common.enums.SocketType;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Manager for all known essences.
 * @author DAM
 */
public class EssencesManager
{
  private static EssencesManager _instance=null;

  private HashMap<Integer,Essence> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static EssencesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new EssencesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public EssencesManager()
  {
    _cache=new HashMap<Integer,Essence>(100);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    ItemsManager itemsMgr=ItemsManager.getInstance();
    for(Item item : itemsMgr.getAllItems())
    {
      if (item instanceof Essence)
      {
        registerEssence((Essence)item);
      }
    }
  }

  /**
   * Register a new essence.
   * @param essence Essence to register.
   */
  private void registerEssence(Essence essence)
  {
    _cache.put(Integer.valueOf(essence.getIdentifier()),essence);
  }

  /**
   * Get an essence using its identifier.
   * @param id Essence identifier.
   * @return An essence or <code>null</code> if not found.
   */
  public Essence getEssence(int id)
  {
    return _cache.get(Integer.valueOf(id));
  }

  /**
   * Get all essences.
   * @return a list of essences, sorted by ID.
   */
  public List<Essence> getAll()
  {
    List<Essence> ret=new ArrayList<Essence>(_cache.values());
    Collections.sort(ret,new IdentifiableComparator<Essence>());
    return ret;
  }

  /**
   * Get all the essences for a given socket type.
   * @param type Socket type.
   * @return A list of essences.
   */
  public List<Essence> getEssences(SocketType type)
  {
    List<Essence> ret=new ArrayList<Essence>();
    for(Essence essence : _cache.values())
    {
      if (essence.getType()==type)
      {
        ret.add(essence);
      }
    }
    return ret;
  }

  /**
   * Get all essence items for a given type.
   * @param type Socket type.
   * @return A list of items.
   */
  public List<Essence> getAllEssenceItems(SocketType type)
  {
    List<Essence> ret=new ArrayList<Essence>();
    for(Essence essence : getEssences(type))
    {
      ret.add(essence);
    }
    Collections.sort(ret,new NamedComparator());
    return ret;
  }
}
