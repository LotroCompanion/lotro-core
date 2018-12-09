package delta.games.lotro.character.utils;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.CharacterEquipment;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.CharacterEquipment.SlotContents;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemFactory;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.essences.EssencesSet;
import delta.games.lotro.lore.items.legendary.Legendary;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;

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
        Item item=contents.getItem();
        Item updatedItem=updateItem(item);
        contents.setItem(updatedItem);
      }
    }
  }

  private Item updateItem(Item item)
  {
    if (item==null)
    {
      return null;
    }
    ItemsManager manager=ItemsManager.getInstance();
    int id=item.getIdentifier();
    Item refItem=manager.getItem(id);
    if (refItem==null)
    {
      return null;
    }
    Item clonedItem=ItemFactory.clone(item);
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
    // Crafting data
    clonedItem.setCrafterName(item.getCrafterName());
    clonedItem.setBirthName(item.getBirthName());
    // Legendary attributes
    boolean isLegendary=(refItem instanceof Legendary);
    boolean oldIsLegendary=(item instanceof Legendary);
    if ((isLegendary) && (oldIsLegendary))
    {
      LegendaryAttrs oldLegAttrs=((Legendary)item).getLegendaryAttrs();
      LegendaryAttrs newLegAttrs=((Legendary)clonedItem).getLegendaryAttrs();
      // Setting
      newLegAttrs.setSetting(getRelic(oldLegAttrs.getSetting()));
      // Gem
      newLegAttrs.setGem(getRelic(oldLegAttrs.getGem()));
      // Rune
      newLegAttrs.setRune(getRelic(oldLegAttrs.getRune()));
      // Crafted
      newLegAttrs.setCraftedRelic(getRelic(oldLegAttrs.getCraftedRelic()));
    }
    return clonedItem;
  }

  private Relic getRelic(Relic ref)
  {
    if (ref==null)
    {
      return null;
    }
    // TODO Use ID
    String refName=ref.getName();
    Relic ret=RelicsManager.getInstance().getByName(refName);
    return ret;
  }
}

