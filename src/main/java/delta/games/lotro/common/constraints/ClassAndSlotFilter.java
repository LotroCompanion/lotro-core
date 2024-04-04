package delta.games.lotro.common.constraints;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.classes.ClassDescription;
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
   * @param spec Spec.
   */
  public ClassAndSlotFilter(ClassAndSlot spec)
  {
    _spec=spec;
  }

  /**
   * Get the required class.
   * @return A character class or <code>null</code>.
   */
  public ClassDescription getCharacterClass()
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
    ClassDescription characterClass=classAndSlot.getCharacterClass();
    if (characterClass!=null)
    {
      ClassDescription thisClass=_spec.getCharacterClass();
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
}
