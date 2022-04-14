package delta.games.lotro.character.storage.cosmetics;

import java.util.List;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.AccountOnServer;
import delta.games.lotro.account.AccountsManager;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.storage.CharacterStorage;
import delta.games.lotro.character.storage.StorageUtils;
import delta.games.lotro.character.storage.StoragesIO;
import delta.games.lotro.character.storage.StoredItem;

/**
 * Test class to show the groups of same cosmetics in a character's storage.
 * @author DAM
 */
public class MainTestFindSameCosmetics
{
  private void doIt()
  {
    CharacterFile toon=CharactersManager.getInstance().getToonById("Landroval","Lorewyne");
    CharacterStorage characterStorage=StoragesIO.loadCharacterStorage(toon);
    List<StoredItem> items=StorageUtils.buildCharacterItems(toon,characterStorage);
    doIt(items);

    Account account=AccountsManager.getInstance().getAccountByName("glorfindel666");
    AccountOnServer accountOnServer=account.getServer("Landroval");
    List<StoredItem> items2=StorageUtils.buildAccountItems(accountOnServer);
    doIt(items2);
  }

  private void doIt(List<StoredItem> items)
  {
    SameCosmeticsFinder finder=new SameCosmeticsFinder();
    List<CosmeticItemsGroup> groups=finder.findGroups(items);
    for(CosmeticItemsGroup group : groups)
    {
      int cosmeticID=group.getCosmeticID();
      List<StoredItem> itemsForGroup=group.getItems();
      System.out.println("ID="+cosmeticID);
      for(StoredItem item : itemsForGroup)
      {
        System.out.println("\t"+item.getItem().getItem()+" - "+item.getLocation());
      }
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestFindSameCosmetics().doIt();
  }
}
