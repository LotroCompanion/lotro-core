package delta.games.lotro.crafting;

import java.util.HashMap;

/**
 * Profession.
 * @author DAM
 */
public class Profession
{
  private static HashMap<String,Profession> _instances=new HashMap<String,Profession>();
  private String _key;
  private String _label;

  /**
   * Prospector.
   */
  public static final Profession PROSPECTOR=new Profession("PROSPECTOR","Prospector");
  /**
   * Forester.
   */
  public static final Profession FORESTER=new Profession("FORESTER","Forester");
  /**
   * Farmer.
   */
  public static final Profession FARMER=new Profession("FARMER","Farmer");
  /**
   * Metalsmith.
   */
  public static final Profession METALSMITH=new Profession("METALSMITH","Metalsmith");
  /**
   * Tailor.
   */
  public static final Profession TAILOR=new Profession("TAILOR","Tailor");
  /**
   * Weaponsmith.
   */
  public static final Profession WEAPONSMITH=new Profession("WEAPONSMITH","Weaponsmith");
  /**
   * Woodworker.
   */
  public static final Profession WOODWORKER=new Profession("WOODWORKER","Woodworker");
  /**
   * Scholar.
   */
  public static final Profession SCHOLAR=new Profession("SCHOLAR","Scholar");
  /**
   * Cook.
   */
  public static final Profession COOK=new Profession("COOK","Cook");
  /**
   * Jeweller.
   */
  public static final Profession JEWELLER=new Profession("JEWELLER","Jeweller");

  /**
   * Constructor.
   * @param key Internal key.
   * @param label Displayable label.
   */
  public Profession(String key, String label)
  {
    _key=key;
    _label=label;
    _instances.put(label,this);
    _instances.put(key,this);
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
    Profession ret=_instances.get(key);
    return ret;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
