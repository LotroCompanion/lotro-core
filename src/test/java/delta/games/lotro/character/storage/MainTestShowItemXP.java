package delta.games.lotro.character.storage;

import java.util.List;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemProvider;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.items.details.ItemXP;

/**
 * Simple tool class to assess how much Item XP a character has.
 * @author DAM
 */
public class MainTestShowItemXP
{
  /**
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    CharacterFile toon=CharactersManager.getInstance().getToonById("Landroval","Kargarth");
    CharacterStorage characterStorage=StoragesIO.loadCharacterStorage(toon);
    List<StoredItem> items=StorageUtils.buildCharacterItems(toon,characterStorage);
    long totalXP=0;
    for(StoredItem storedItem : items)
    {
      CountedItem<ItemProvider> counted=storedItem.getItem();
      Item item=counted.getItem();
      ItemDetailsManager details=item.getDetails();
      if (details!=null)
      {
        List<ItemXP> itemXPs=details.getItemDetails(ItemXP.class);
        if (itemXPs.size()>0)
        {
          ItemXP itemXP=itemXPs.get(0);
          int count=counted.getQuantity();
          System.out.println("Got "+count+" of "+item+" - "+itemXP.getAmount()+" XP");
          totalXP+=(count*itemXP.getAmount());
        }
      }
    }
    System.out.println("Got: "+totalXP);
  }
}
