package delta.games.lotro.character;

import java.util.Set;

import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.WeaponType;
import junit.framework.TestCase;

/**
 * Test character proficiencies.
 * @author DAM
 */
public class CharacterProficienciesTest extends TestCase
{
  /**
   * Test character proficiencies.
   */
  public void testProficiencies()
  {
    for(CharacterClass cClass : CharacterClass.ALL_CLASSES)
    {
      weaponsForClass(cClass);
      armoursForClass(cClass);
    }
  }

  private void weaponsForClass(CharacterClass characterClass)
  {
    for(int level=1;level<=100;level++)
    {
      Set<WeaponType> set1=OldCharacterProficiencies.getWeaponProficiencies(characterClass,level);
      Set<WeaponType> set2=CharacterProficiencies.getWeaponProficiencies(characterClass,level);
      if (!set1.equals(set2))
      {
        System.out.println("Class "+characterClass+": weapon differ at level "+level+": set1="+set1+", set2="+set2);
      }
    }
  }

  private void armoursForClass(CharacterClass characterClass)
  {
    for(int level=1;level<=100;level++)
    {
      Set<ArmourType> set1=OldCharacterProficiencies.getArmourProficiencies(characterClass,level);
      Set<ArmourType> set2=CharacterProficiencies.getArmourProficiencies(characterClass,level);
      if (!set1.equals(set2))
      {
        System.out.println("Class "+characterClass+": armour differ at level "+level+": set1="+set1+", set2="+set2);
      }
    }
  }
}
