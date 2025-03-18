package delta.games.lotro.character;

import java.util.HashSet;
import java.util.Set;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.WellKnownCharacterClassKeys;
import delta.games.lotro.character.classes.proficiencies.ClassProficiencies;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.WeaponType;

/**
 * Character proficiencies.
 * <br>
 * This includes:
 * <ul>
 * <li>Weapons,
 * <li>Armour types,
 * <li>Dual wield.
 * </ul>
 * @author DAM
 */
public class CharacterProficiencies
{
  /**
   * Get the set of weapons usable by a character class/level.
   * @param classDescription Class.
   * @param level Level.
   * @return A set of weapons.
   */
  public static Set<WeaponType> getWeaponProficiencies(ClassDescription classDescription, int level)
  {
    Set<WeaponType> ret=new HashSet<WeaponType>();
    if (classDescription!=null)
    {
      ret.addAll(classDescription.getProficiencies().getWeaponProficiencies().getEntries(level));
    }
    return ret;
  }

  /**
   * Indicates if the given character class/level can dual wield or not.
   * @param characterClass Class.
   * @param level Level.
   * @return <code>true</code> if dual wield is allowed, <code>false</code> otherwise.
   */
  public static boolean isDualWielding(ClassDescription characterClass, int level)
  {
    ClassProficiencies proficiencies=characterClass.getProficiencies();
    Integer minLevel=proficiencies.getMinLevelForDualWield();
    if (minLevel==null)
    {
      return false;
    }
    return (minLevel.intValue()<=level);
  }

  /**
   * Get the set of armour types useable by a character class/level.
   * @param classDescription Class.
   * @param level Level.
   * @return A set of armour types.
   */
  public static Set<ArmourType> getArmourProficiencies(ClassDescription classDescription, int level)
  {
    Set<ArmourType> ret=new HashSet<ArmourType>();
    if (classDescription!=null)
    {
      ret.addAll(classDescription.getProficiencies().getArmourProficiencies().getEntries(level));
    }
    return ret;
  }
}
