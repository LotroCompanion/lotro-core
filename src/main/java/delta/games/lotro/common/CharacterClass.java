package delta.games.lotro.common;

import java.util.HashMap;

/**
 * Character class.
 * @author DAM
 */
public class CharacterClass
{
  private static HashMap<String,CharacterClass> _instances=new HashMap<String,CharacterClass>();
  private static HashMap<String,CharacterClass> _instancesByKey=new HashMap<String,CharacterClass>();
  private String _key;
  private String _iconPath;

  /**
   * Beorning.
   */
  public static final CharacterClass BEORNING=new CharacterClass("Beorning","beorning");
  /**
   * Burglar.
   */
  public static final CharacterClass BURGLAR=new CharacterClass("Burglar","burglar");
  /**
   * Captain.
   */
  public static final CharacterClass CAPTAIN=new CharacterClass("Captain","captain");
  /**
   * Champion.
   */
  public static final CharacterClass CHAMPION=new CharacterClass("Champion","champion");
  /**
   * Guardian.
   */
  public static final CharacterClass GUARDIAN=new CharacterClass("Guardian","guardian");
  /**
   * Hunter.
   */
  public static final CharacterClass HUNTER=new CharacterClass("Hunter","hunter");
  /**
   * Lore master.
   */
  public static final CharacterClass LORE_MASTER=new CharacterClass("Lore-master","loremaster",new String[]{"LoreMaster"});
  /**
   * Minstrel.
   */
  public static final CharacterClass MINSTREL=new CharacterClass("Minstrel","minstrel");
  /**
   * Rune-keeper.
   */
  public static final CharacterClass RUNE_KEEPER=new CharacterClass("Rune-keeper","runekeeper",new String[]{"RuneKeeper"});
  /**
   * Warden.
   */
  public static final CharacterClass WARDEN=new CharacterClass("Warden","warden");

  /**
   * An array of all character classes.
   */
  public static final CharacterClass[] ALL_CLASSES = {
    BEORNING, BURGLAR, CAPTAIN, CHAMPION, GUARDIAN, HUNTER, LORE_MASTER, MINSTREL, RUNE_KEEPER, WARDEN
  };

  private CharacterClass(String label, String iconPath)
  {
    this(label,iconPath,null);
  }

  private CharacterClass(String key, String iconPath, String[] aliases)
  {
    _key=key;
    _iconPath=iconPath;
    _instances.put(key,this);
    _instancesByKey.put(key,this);
    if (aliases!=null)
    {
      for(String alias : aliases)
      {
        _instances.put(alias,this);
      }
    }
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
    return _key;
  }

  /**
   * Get the name of the associated icon.
   * @return a icon path.
   */
  public String getIconPath()
  {
    return _iconPath;
  }

  /**
   * Get a character class instance by its key.
   * @param key Key to search.
   * @return A character class or <code>null</code> if not found.
   */
  public static CharacterClass getByKey(String key)
  {
    CharacterClass ret=_instancesByKey.get(key);
    return ret;
  }

  /**
   * Get a character class instance by a name.
   * @param name Label to search.
   * @return A character class or <code>null</code> if not found.
   */
  public static CharacterClass getByName(String name)
  {
    CharacterClass ret=_instances.get(name);
    return ret;
  }

  @Override
  public String toString()
  {
    return _key;
  }
}
