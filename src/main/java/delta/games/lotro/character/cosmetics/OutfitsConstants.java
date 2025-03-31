package delta.games.lotro.character.cosmetics;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.character.gear.GearSlots;

/**
 * Constants related to outfits.
 * @author DAM
 */
public class OutfitsConstants
{
  /**
   * Get the slots used for outfits.
   * @return a list of slots.
   */
  public static List<GearSlot> getAll()
  {
    List<GearSlot> ret=new ArrayList<GearSlot>();
    ret.add(GearSlots.HEAD);
    ret.add(GearSlots.SHOULDER);
    ret.add(GearSlots.BREAST);
    ret.add(GearSlots.BACK);
    ret.add(GearSlots.HANDS);
    ret.add(GearSlots.LEGS);
    ret.add(GearSlots.FEET);
    ret.add(GearSlots.MAIN_MELEE);
    ret.add(GearSlots.OTHER_MELEE);
    ret.add(GearSlots.RANGED);
    ret.add(GearSlots.CLASS_ITEM);
    ret.add(GearSlots.MAIN_HAND_AURA);
    ret.add(GearSlots.OFF_HAND_AURA);
    ret.add(GearSlots.RANGED_AURA);
    return ret;
  }
}
