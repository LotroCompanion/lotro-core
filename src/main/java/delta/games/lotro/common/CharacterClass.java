package delta.games.lotro.common;

import java.util.HashMap;

/**
 * Character class.
 * @author DAM
 */
public final class CharacterClass
{
  private static HashMap<String,CharacterClass> _instancesByKey=new HashMap<String,CharacterClass>();
  private String _key;
  private String _iconPath;

  /**
   * Beorning.
   */
  public static final CharacterClass BEORNING=new CharacterClass("Beorning","beorning");
  /**
   * Brawler.
   */
  public static final CharacterClass BRAWLER=new CharacterClass("Brawler","brawler");
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
  public static final CharacterClass LORE_MASTER=new CharacterClass("Lore-master","loremaster");
  /**
   * Minstrel.
   */
  public static final CharacterClass MINSTREL=new CharacterClass("Minstrel","minstrel");
  /**
   * Rune-keeper.
   */
  public static final CharacterClass RUNE_KEEPER=new CharacterClass("Rune-keeper","runekeeper");
  /**
   * Warden.
   */
  public static final CharacterClass WARDEN=new CharacterClass("Warden","warden");

  /**
   * An array of all character classes.
   */
  public static final CharacterClass[] ALL_CLASSES =
  {
    BEORNING, BRAWLER, BURGLAR, CAPTAIN, CHAMPION, GUARDIAN, HUNTER, LORE_MASTER, MINSTREL, RUNE_KEEPER, WARDEN
  };

  /**
   * Reaver.
   */
  public static final CharacterClass REAVER=new CharacterClass("Reaver","reaver");
  /**
   * Defiler.
   */
  public static final CharacterClass DEFILER=new CharacterClass("Defiler","defiler");
  /**
   * Weaver.
   */
  public static final CharacterClass WEAVER=new CharacterClass("Weaver","weaver");
  /**
   * Blackarrow.
   */
  public static final CharacterClass BLACKARROW=new CharacterClass("Blackarrow","blackarrow");
  /**
   * Warleader.
   */
  public static final CharacterClass WARLEADER=new CharacterClass("Warleader","warleader");
  /**
   * Stalker.
   */
  public static final CharacterClass STALKER=new CharacterClass("Stalker","stalker");

  /**
   * An array of all monster classes.
   */
  public static final CharacterClass[] ALL_MONSTER_CLASSES =
  {
    REAVER, DEFILER, WEAVER, BLACKARROW, WARLEADER, STALKER
  };

  private CharacterClass(String key, String iconPath)
  {
    _key=key;
    _iconPath=iconPath;
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

  @Override
  public String toString()
  {
    return _key;
  }
}
