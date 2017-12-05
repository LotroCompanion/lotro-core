package delta.games.lotro.common;

import java.util.HashMap;

/**
 * Character sex.
 * @author DAM
 */
public final class CharacterSex
{
  private static HashMap<String,CharacterSex> _instancesByKey=new HashMap<String,CharacterSex>();
  private String _key;
  private String _label;

  /**
   * Male.
   */
  public static final CharacterSex MALE=new CharacterSex("MALE","Male");
  /**
   * Female.
   */
  public static final CharacterSex FEMALE=new CharacterSex("FEMALE","Female");

  /**
   * An array of all character sex instances.
   */
  public static final CharacterSex[] ALL = {
    MALE, FEMALE
  };

  private CharacterSex(String key, String label)
  {
    _key=key;
    _label=label;
    _instancesByKey.put(key,this);
  }

  /**
   * Get the key for this class.
   * @return An internal key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the displayable name of this class.
   * @return A displayable label.
   */
  public String getLabel()
  {
    return _label;
  }

  /**
   * Get a character sex instance by its key.
   * @param key Key to search.
   * @return A character sex or <code>null</code> if not found.
   */
  public static CharacterSex getByKey(String key)
  {
    CharacterSex ret=_instancesByKey.get(key);
    return ret;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
