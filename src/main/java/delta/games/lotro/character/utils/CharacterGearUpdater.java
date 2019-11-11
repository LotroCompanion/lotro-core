package delta.games.lotro.character.utils;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.CharacterEquipment;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.CharacterEquipment.SlotContents;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Character gear updater.
 * Updates the gear of a character using the current items database.
 * @author DAM
 */
public class CharacterGearUpdater
{
  /**
   * Update the given character data.
   * @param data Data to update.
   */
  public void updateGear(CharacterData data)
  {
    CharacterEquipment gear=data.getEquipment();
    for(EQUIMENT_SLOT slot : EQUIMENT_SLOT.values())
    {
      SlotContents contents=gear.getSlotContents(slot,false);
      if (contents!=null)
      {
        ItemInstance<? extends Item> item=contents.getItem();
        updateItem(item);
      }
    }
  }

  private void updateItem(ItemInstance<? extends Item> item)
  {
    if (item==null)
    {
      return;
    }
    item.updateAutoStats();
  }
}
