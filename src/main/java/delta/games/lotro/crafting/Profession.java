package delta.games.lotro.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Profession.
 * @author DAM
 */
public class Profession
{
  private static HashMap<String,Profession> _instances=new HashMap<String,Profession>();
  private static HashMap<String,Profession> _instancesByKey=new HashMap<String,Profession>();
  private String _key;
  private String _label;
  private boolean _hasGuild;

  /**
   * Prospector.
   */
  public static final Profession PROSPECTOR=new Profession("PROSPECTOR","Prospector",false);
  /**
   * Forester.
   */
  public static final Profession FORESTER=new Profession("FORESTER","Forester",false);
  /**
   * Farmer.
   */
  public static final Profession FARMER=new Profession("FARMER","Farmer",false);
  /**
   * Metalsmith.
   */
  public static final Profession METALSMITH=new Profession("METALSMITH","Metalsmith",true);
  /**
   * Tailor.
   */
  public static final Profession TAILOR=new Profession("TAILOR","Tailor",true);
  /**
   * Weaponsmith.
   */
  public static final Profession WEAPONSMITH=new Profession("WEAPONSMITH","Weaponsmith",true);
  /**
   * Woodworker.
   */
  public static final Profession WOODWORKER=new Profession("WOODWORKER","Woodworker",true);
  /**
   * Scholar.
   */
  public static final Profession SCHOLAR=new Profession("SCHOLAR","Scholar",true);
  /**
   * Cook.
   */
  public static final Profession COOK=new Profession("COOK","Cook",true);
  /**
   * Jeweller.
   */
  public static final Profession JEWELLER=new Profession("JEWELLER","Jeweller",true);

  /**
   * Constructor.
   * @param key Internal key.
   * @param label Displayable label.
   * @param hasGuild Indicates if this profession has a guild or not.
   */
  public Profession(String key, String label, boolean hasGuild)
  {
    _key=key;
    _label=label;
    _hasGuild=hasGuild;
    _instances.put(label,this);
    _instances.put(key,this);
    _instancesByKey.put(key,this);
  }

  /**
   * Get a identifying key for this profession.
   * @return A key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the displayable name of this profession.
   * @return A displayable label.
   */
  public String getLabel()
  {
    return _label;
  }

  /**
   * Indicates if this profession has a guild or not.
   * @return <code>true</code> if it has a guild, <code>false</code> otherwise.
   */
  public boolean hasGuild()
  {
    return _hasGuild;
  }

  /**
   * Get a profession instance by its label.
   * @param label Label to search.
   * @return A profession or <code>null</code> if not found.
   */
  public static Profession getByLabel(String label)
  {
    Profession ret=_instances.get(label);
    return ret;
  }

  /**
   * Get a profession instance by its key.
   * @param key Key to search.
   * @return A profession or <code>null</code> if not found.
   */
  public static Profession getByKey(String key)
  {
    Profession ret=_instancesByKey.get(key);
    return ret;
  }

  /**
   * Get all the professions.
   * @return A sorted list of professions.
   */
  public static List<Profession> getAll()
  {
    List<Profession> ret=new ArrayList<Profession>();
    ret.addAll(_instancesByKey.values());
    Collections.sort(ret,new ProfessionComparator());
    return ret;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
