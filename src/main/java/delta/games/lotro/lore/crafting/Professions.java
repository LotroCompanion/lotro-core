package delta.games.lotro.lore.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.IdentifiableComparator;

/**
 * Professions registry.
 * @author DAM
 */
public final class Professions
{
  private HashMap<String,Profession> _professionsByName;
  private HashMap<String,Profession> _professionsByKey;
  private HashMap<Integer,Profession> _professionsById;

  /**
   * Constructor.
   */
  public Professions()
  {
    _professionsByName=new HashMap<String,Profession>();
    _professionsByKey=new HashMap<String,Profession>();
    _professionsById=new HashMap<Integer,Profession>();
  }

  /**
   * Register a profession.
   * @param profession Profession to add.
   */
  public void addProfession(Profession profession)
  {
    _professionsById.put(Integer.valueOf(profession.getIdentifier()),profession);
    _professionsByKey.put(profession.getKey(),profession);
    _professionsByName.put(profession.getName(),profession);
  }

  /**
   * Get a list of all professions.
   * @return a list of all professions.
   */
  public List<Profession> getAll()
  {
    List<Profession> ret=new ArrayList<Profession>();
    ret.addAll(_professionsById.values());
    Collections.sort(ret,new IdentifiableComparator<Profession>());
    return ret;
  }

  /**
   * Get a profession by its identifier. 
   * @param identifier Identifier of the profession to get.
   * @return A profession or <code>null</code> if not found.
   */
  public Profession getProfessionById(int identifier)
  {
    return _professionsById.get(Integer.valueOf(identifier));
  }

  /**
   * Get a profession by its identifying key. 
   * @param key Identifying key of the profession to get.
   * @return A profession or <code>null</code> if not found.
   */
  public Profession getProfessionByKey(String key)
  {
    return _professionsByKey.get(key);
  }

  /**
   * Get a profession by its name. 
   * @param name Name of the profession to get.
   * @return A profession or <code>null</code> if not found.
   */
  public Profession getProfessionByName(String name)
  {
    return _professionsByName.get(name);
  }
}
