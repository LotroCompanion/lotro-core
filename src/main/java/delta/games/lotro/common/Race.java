package delta.games.lotro.common;

import java.util.HashMap;

/**
 * Character race.
 * @author DAM
 */
public class Race
{
  private static HashMap<String,Race> _instances=new HashMap<String,Race>();
  private String _label;
  private String _key;

  /**
   * Beorning.
   */
  public static final Race BEORNING=new Race("Beorning","beorning");
  /**
   * Dwarf.
   */
  public static final Race DWARF=new Race("Dwarf","dwarf");
  /**
   * Elf.
   */
  public static final Race ELF=new Race("Elf","elf");
  /**
   * Hobbit.
   */
  public static final Race HOBBIT=new Race("Hobbit","hobbit");
  /**
   * High Elf.
   */
  public static final Race HIGH_ELF=new Race("High Elf","highelf");
  /**
   * Man.
   */
  public static final Race MAN=new Race("Race of Man","man");

  /**
   * An array of all races.
   */
  public static final Race[] ALL_RACES = {
    BEORNING, DWARF, ELF, HIGH_ELF, HOBBIT, MAN
  };

  private Race(String label, String key)
  {
    _label=label;
    _key=key;
    _instances.put(label,this);
    _instances.put(key,this);
  }

  /**
   * Get a identifying key for this race.
   * @return A key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the displayable name of this race.
   * @return A displayable label.
   */
  public String getLabel()
  {
    return _label;
  }

  /**
   * Get the name of the associated icon.
   * @return a icon path.
   */
  public String getIconPath()
  {
    return _key;
  }

  /**
   * Get a character race instance by its label.
   * @param label Label to search.
   * @return A character race or <code>null</code> if not found.
   */
  public static Race getByLabel(String label)
  {
    Race ret=_instances.get(label);
    return ret;
  }

  /**
   * Get a character race instance by its key.
   * @param key Key to search.
   * @return A character race or <code>null</code> if not found.
   */
  public static Race getByKey(String key)
  {
    Race ret=_instances.get(key);
    return ret;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
