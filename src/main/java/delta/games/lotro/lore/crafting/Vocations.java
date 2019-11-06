package delta.games.lotro.lore.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.IdentifiableComparator;

/**
 * Vocations registry.
 * @author DAM
 */
public final class Vocations
{
  private HashMap<String,Vocation> _vocationsByName;
  private HashMap<String,Vocation> _vocationsByKey;
  private HashMap<Integer,Vocation> _vocationsById;

  /**
   * Constructor.
   */
  public Vocations()
  {
    _vocationsByName=new HashMap<String,Vocation>();
    _vocationsByKey=new HashMap<String,Vocation>();
    _vocationsById=new HashMap<Integer,Vocation>();
  }

  /**
   * Register a vocation.
   * @param vocation Vocation to add.
   */
  public void addVocation(Vocation vocation)
  {
    _vocationsById.put(Integer.valueOf(vocation.getIdentifier()),vocation);
    _vocationsByKey.put(vocation.getKey(),vocation);
    _vocationsByName.put(vocation.getName(),vocation);
  }

  /**
   * Get a list of all vocations.
   * @return a list of all vocations.
   */
  public List<Vocation> getAll()
  {
    List<Vocation> ret=new ArrayList<Vocation>();
    ret.addAll(_vocationsById.values());
    Collections.sort(ret,new IdentifiableComparator<Vocation>());
    return ret;
  }

  /**
   * Get a vocation by its identifier. 
   * @param identifier Identifier of the vocation to get.
   * @return A vocation or <code>null</code> if not found.
   */
  public Vocation getVocationById(int identifier)
  {
    return _vocationsById.get(Integer.valueOf(identifier));
  }

  /**
   * Get a vocation by its identifying key. 
   * @param key Identifying key of the vocation to get.
   * @return A vocation or <code>null</code> if not found.
   */
  public Vocation getVocationByKey(String key)
  {
    return _vocationsByKey.get(key);
  }

  /**
   * Get a vocation by its name. 
   * @param name Name of vocation to get.
   * @return A vocation or <code>null</code> if not found.
   */
  public Vocation getVocationByName(String name)
  {
    return _vocationsByName.get(name);
  }
}
