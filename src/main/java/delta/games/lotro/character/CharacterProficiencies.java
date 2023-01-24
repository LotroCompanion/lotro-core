package delta.games.lotro.character;

import java.util.HashSet;
import java.util.Set;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.WellKnownCharacterClassKeys;
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
  public boolean isDualWielding(ClassDescription characterClass, int level)
  {
    // See trait: 1879064092 Dual Wielding
    /*
Mod_Array: 
  #1: Mod_Entry 
    Inventory_AllowSecondaryWeapon: 1
    Mod_DescriptionOverride: 
    Mod_Modified: 268439425 (Inventory_AllowSecondaryWeapon)
    Mod_Op: 5 (Set)
     */
    String classKey=characterClass.getKey();
    if ((WellKnownCharacterClassKeys.CHAMPION.equals(classKey)) ||
        (WellKnownCharacterClassKeys.BEORNING.equals(classKey)) ||
        (WellKnownCharacterClassKeys.BURGLAR.equals(classKey)) ||
        (WellKnownCharacterClassKeys.HUNTER.equals(classKey)))
    {
      return true;
    }
    if ((WellKnownCharacterClassKeys.LORE_MASTER.equals(classKey)) && (level>=40))
    {
      return true;
    }
    return false;
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
