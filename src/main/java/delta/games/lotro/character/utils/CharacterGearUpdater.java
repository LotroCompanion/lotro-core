package delta.games.lotro.character.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.gear.CharacterGear;
import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.character.gear.GearSlotContents;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.scaling.ItemSpellcraft;
import delta.games.lotro.lore.items.scaling.SpellcraftResolver;

/**
 * Character gear updater.
 * Updates the gear of a character using the current items database.
 * @author DAM
 */
public class CharacterGearUpdater
{
  private static final Logger LOGGER=LoggerFactory.getLogger(CharacterGearUpdater.class);

  private CharacterData _data;
  private SpellcraftResolver _resolver;

  /**
   * Constructor.
   * @param toon Character.
   * @param data Character config.
   */
  public CharacterGearUpdater(CharacterFile toon, CharacterData data)
  {
    _data=data;
    _resolver=new SpellcraftResolver(toon,data);
  }

  /**
   * Update the given character data.
   */
  public void updateGear()
  {
    CharacterGear gear=_data.getEquipment();
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

  /**
   * Update the given item.
   * @param item Item to update.
   */
  public void updateItem(ItemInstance<? extends Item> item)
  {
    if (item==null)
    {
      return;
    }
    Item reference=item.getReference();
    ItemSpellcraft spellcraft=reference.getSpellcraft();
    if (spellcraft!=null)
    {
      Integer itemLevel=_resolver.getEffectiveSpellcraft(reference);
      if (itemLevel!=null)
      {
        LOGGER.info("Using spellcraft item level: {}",itemLevel);
        item.setItemLevel(itemLevel);
      }
    }
    item.updateAutoStats();
  }
}
