package delta.games.lotro.common.constraints;

import java.util.Objects;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.lore.items.EquipmentLocation;

/**
 * Filter for class and slot specification.
 * @author DAM
 */
public class ClassAndSlotFilter implements Filter<ClassAndSlot>
{
  private ClassAndSlot _spec;

  /**
   * Constructor.
   * @param characterClass Required class, may be <code>null</code>.
   * @param slot Required slot, may be <code>null</code>.
   */
  public ClassAndSlotFilter(CharacterClass characterClass, EquipmentLocation slot)
  {
    _spec=new ClassAndSlot(characterClass,slot);
  }

  /**
   * Get the required class.
   * @return A character class or <code>null</code>.
   */
  public CharacterClass getCharacterClass()
  {
    return _spec.getCharacterClass();
  }

  /**
   * Get the required slot.
   * @return A slot or <code>null</code>.
   */
  public EquipmentLocation getSlot()
  {
    return _spec.getSlot();
  }

  @Override
  public boolean accept(ClassAndSlot classAndSlot)
  {
    CharacterClass characterClass=classAndSlot.getCharacterClass();
    if (characterClass!=null)
    {
      CharacterClass thisClass=_spec.getCharacterClass();
      if ((thisClass!=null) && (thisClass!=characterClass))
      {
        return false;
      }
    }
    EquipmentLocation slot=classAndSlot.getSlot();
    if (slot!=null)
    {
      EquipmentLocation thisSlot=_spec.getSlot();
      if ((thisSlot!=null) && (thisSlot!=slot))
      {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean equals(Object other)
  {
    if (other instanceof ClassAndSlotFilter)
    {
      ClassAndSlotFilter otherFilter=(ClassAndSlotFilter)other;
      return Objects.equals(_spec,otherFilter._spec);
    }
    return false;
  }
}
