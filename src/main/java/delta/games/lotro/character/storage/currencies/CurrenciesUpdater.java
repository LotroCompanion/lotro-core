package delta.games.lotro.character.storage.currencies;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.account.Account;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.storage.wallet.Wallet;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.paper.PaperItem;
import delta.games.lotro.lore.items.paper.PaperItemsManager;

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
    updateCurrenciesForWallet(mgr,sharedWallet,true);
  }

  /**
   * Update the currencies for a character.
   * @param wallet Wallet to use.
   * @param characterFile Character to use.
   */
  public static void updateCurrenciesForCharacter(Wallet wallet, CharacterFile characterFile)
  {
    CurrenciesManager mgr=new CurrenciesManager(characterFile);
    updateCurrenciesForWallet(mgr,wallet,false);
  }

  private static void updateCurrenciesForWallet(CurrenciesManager mgr, Wallet wallet, boolean shared)
  {
    Set<Integer> done=new HashSet<Integer>();
    List<CountedItem<Item>> countedItems=wallet.getAllItemsSortedByID();
    for(CountedItem<Item> countedItem : countedItems)
    {
      int id=countedItem.getId();
      done.add(Integer.valueOf(id));
      String currencyID=String.valueOf(id);
      int quantity=countedItem.getQuantity();
      mgr.updateCurrency(currencyID,quantity,true);
    }
    updateMissingCurrencies(mgr,shared,done);
    mgr.save();
  }

  private static void updateMissingCurrencies(CurrenciesManager mgr, boolean shared, Set<Integer> done)
  {
    PaperItemsManager piMgr=PaperItemsManager.getInstance();
    for(PaperItem paperItem : piMgr.getAll())
    {
      if (shared!=paperItem.isShared())
      {
        continue;
      }
      Integer id=Integer.valueOf(paperItem.getIdentifier());
      if (done.contains(id))
      {
        continue;
      }
      String currencyID=String.valueOf(id);
      CurrenciesSummary summary=mgr.getSummary();
      CurrencyStatus status=summary.getCurrency(currencyID,false);
      if (status!=null)
      {
        mgr.updateCurrency(currencyID,0,true);
      }
    }
  }
}
