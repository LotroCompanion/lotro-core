package delta.games.lotro.character.storage.currencies;

import java.util.List;

import delta.games.lotro.account.Account;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.storage.wallet.Wallet;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;

/**
 * Tool methods to update currencies.
 * @author DAM
 */
public class CurrenciesUpdater
{
  /**
   * Update the currencies for an account.
   * @param sharedWallet Wallet to use.
   * @param account Account to use.
   * @param serverName Server name.
   */
  public static void updateCurrenciesForAccount(Wallet sharedWallet, Account account, String serverName)
  {
    CurrenciesManager mgr=new CurrenciesManager(account,serverName);
    updateCurrenciesForWallet(mgr,sharedWallet);
  }

  /**
   * Update the currencies for a character.
   * @param wallet Wallet to use.
   * @param characterFile Character to use.
   */
  public static void updateCurrenciesForCharacter(Wallet wallet, CharacterFile characterFile)
  {
    CurrenciesManager mgr=new CurrenciesManager(characterFile);
    updateCurrenciesForWallet(mgr,wallet);
  }

  private static void updateCurrenciesForWallet(CurrenciesManager mgr, Wallet wallet)
  {
    List<CountedItem<Item>> countedItems=wallet.getAllItemsSortedByID();
    for(CountedItem<Item> countedItem : countedItems)
    {
      int id=countedItem.getId();
      String currencyID=String.valueOf(id);
      int quantity=countedItem.getQuantity();
      boolean useHistory=useHistory(currencyID);
      mgr.updateCurrency(currencyID,quantity,useHistory);
    }
    mgr.save();
  }

  private static boolean useHistory(String currencyID)
  {
    return true;
  }
}
