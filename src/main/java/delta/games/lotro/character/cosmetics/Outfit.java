package delta.games.lotro.character.cosmetics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import delta.games.lotro.character.gear.GearSlot;

/**
 * Outfit.
 * @author DAM
 */
public class Outfit
{
  private Map<GearSlot,OutfitElement> _elements;
  private Set<GearSlot> _visibleSlots;

  /**
   * Constructor.
   */
  public Outfit()
  {
    _elements=new HashMap<GearSlot,OutfitElement>();
    _visibleSlots=new HashSet<GearSlot>();
  }

  /**
   * Get the description of a slot.
   * @param slot Slot to get.
   * @return An outfit element or <code>null</code> if nothing in the slot.
   */
  public OutfitElement getSlot(GearSlot slot)
  {
    return _elements.get(slot);
  }

  /**
   * Set the contents of a slot.
   * @param slot Slot to set.
   * @param element Outfit element to set.
   */
  public void setSlot(GearSlot slot, OutfitElement element)
  {
    _elements.put(slot,element);
  }

  /**
   * Indicates if the given slot is visible or not.
   * @param slot Slot to use.
   * @return <code>true</code> if it is visible, <code>false</code> otherwise.
   */
  public boolean isSlotVisible(GearSlot slot)
  {
    return _visibleSlots.contains(slot);
  }

  /**
   * Set the visibility of a slot.
   * @param slot Slot to use.
   * @param visible Visibility to set.
   */
  public void setSlotVisible(GearSlot slot, boolean visible)
  {
    if (visible)
    {
      _visibleSlots.add(slot);
    }
    else
    {
      _visibleSlots.remove(slot);
    }
  }
}
