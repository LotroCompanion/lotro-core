package delta.games.lotro.lore.items.legendary.non_imbued;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.lore.items.EquipmentLocation;

/**
 * Stores the available legacies for a given class and slot.
 * @author DAM
 */
public class LegaciesAvailableForClassAndSlot
{
  private CharacterClass _characterClass;
  private EquipmentLocation _slot;
  private List<NonImbuedLegacy> _legacies;

  /**
   * Constructor.
   * @param characterClass Character class.
   * @param slot Slot.
   */
  public LegaciesAvailableForClassAndSlot(CharacterClass characterClass, EquipmentLocation slot)
  {
    _characterClass=characterClass;
    _slot=slot;
    _legacies=new ArrayList<NonImbuedLegacy>();
  }

  /**
   * Register a legacy usage.
   * @param legacy Legacy to use.
   */
  public void addLegacyUsage(NonImbuedLegacy legacy)
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
  public List<NonImbuedLegacy> getAll()
  {
    return new ArrayList<NonImbuedLegacy>(_legacies);
  }
}
