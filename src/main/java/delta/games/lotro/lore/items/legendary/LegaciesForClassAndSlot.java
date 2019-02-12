package delta.games.lotro.lore.items.legendary;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.lore.items.EquipmentLocation;

/**
 * Stores the available legacies for a given class and slot.
 * @author DAM
 * @param <T> Legacy type.
 */
public class LegaciesForClassAndSlot<T extends AbstractLegacy>
{
  private CharacterClass _characterClass;
  private EquipmentLocation _slot;
  private List<T> _legacies;

  /**
   * Constructor.
   * @param characterClass Character class.
   * @param slot Slot.
   */
  public LegaciesForClassAndSlot(CharacterClass characterClass, EquipmentLocation slot)
  {
    _characterClass=characterClass;
    _slot=slot;
    _legacies=new ArrayList<T>();
  }

  /**
   * Register a legacy usage.
   * @param legacy Legacy to use.
   */
  public void addLegacyUsage(T legacy)
  {
    if (!_legacies.contains(legacy))
    {
      _legacies.add(legacy);
    }
  }

  /**
   * Get the managed character class.
   * @return the managed character class.
   */
  public CharacterClass getCharacterClass()
  {
    return _characterClass;
  }

  /**
   * Get the managed slot.
   * @return the managed slot.
   */
  public EquipmentLocation getSlot()
  {
    return _slot;
  }

  /**
   * Get all managed legacies.
   * @return A list of managed legacies.
   */
  public List<T> getAll()
  {
    return new ArrayList<T>(_legacies);
  }
}
