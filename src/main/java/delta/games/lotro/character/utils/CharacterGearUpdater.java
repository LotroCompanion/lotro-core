package delta.games.lotro.character.utils;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.gear.CharacterGear;
import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.character.gear.GearSlotContents;
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
    CharacterGear gear=data.getEquipment();
    for(GearSlot slot : GearSlot.getAll())
    {
      GearSlotContents contents=gear.getSlotContents(slot,false);
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
