package delta.games.lotro.common.constraints;

import java.util.Objects;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.lore.items.EquipmentLocation;

/**
 * Class and slot specification.
 * @author DAM
 */
public class ClassAndSlot
{
  private ClassDescription _characterClass;
  private EquipmentLocation _slot;

  /**
   * Constructor.
   * @param characterClass Character class.
   * @param slot Slot.
   */
  public ClassAndSlot(ClassDescription characterClass, EquipmentLocation slot)
  {
    _characterClass=characterClass;
    _slot=slot;
  }

  /**
   * Get the character class.
   * @return the character class.
   */
  public ClassDescription getCharacterClass()
  {
    return _characterClass;
  }

  /**
   * Get the slot.
   * @return the slot.
   */
  public EquipmentLocation getSlot()
  {
    return _slot;
  }

  @Override
  public boolean equals(Object other)
  {
    if (other instanceof ClassAndSlot)
    {
      ClassAndSlot otherClassAndSlot=(ClassAndSlot)other;
      return (Objects.equals(_characterClass,otherClassAndSlot._characterClass) && Objects.equals(_slot,otherClassAndSlot._slot));
    }
    return false;
  }

  @Override
  public String toString()
  {
    return "Character class: "+_characterClass+", slot: "+_slot;
  }
}
