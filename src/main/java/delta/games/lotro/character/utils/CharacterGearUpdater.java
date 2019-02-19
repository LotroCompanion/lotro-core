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
    /*
    if (item==null)
    {
      return;
    }
    ItemsManager manager=ItemsManager.getInstance();
    int id=item.getIdentifier();
    Item refItem=manager.getItem(id);
    if (refItem==null)
    {
      return;
    }
    item.setReference(refItem);
    // Item level
    Integer itemLevel=item.getItemLevel();
    if (itemLevel!=null)
    {
      StatsProvider provider=refItem.getStatsProvider();
      if (provider!=null)
      {
        BasicStatsSet stats=provider.getStats(1,itemLevel.intValue(),true);
        clonedItem.getStats().setStats(stats);
      }
      clonedItem.setItemLevel(itemLevel);
    }
    // Essences
    EssencesSet essences=item.getEssences();
    if (essences!=null)
    {
      int nbEssences=essences.getSize();
      EssencesSet newEssences=new EssencesSet(nbEssences);
      for(int i=0;i<nbEssences;i++)
      {
        Item newEssence=null;
        Item oldEssence=essences.getEssence(i);
        if (oldEssence!=null)
        {
          Item refEssence=manager.getItem(oldEssence.getIdentifier());
          if (refEssence!=null)
          {
            newEssence=ItemFactory.clone(refEssence);
          }
        }
        newEssences.setEssence(i,newEssence);
      }
      clonedItem.setEssences(newEssences);
    }
    return clonedItem;
    */
  }
}
