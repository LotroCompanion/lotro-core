package delta.games.lotro.character.storage.currencies;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.events.AccountEvent;
import delta.games.lotro.account.events.AccountEventProperties;
import delta.games.lotro.account.events.AccountEventType;
import delta.games.lotro.character.storage.Wallet;
import delta.games.lotro.character.storage.io.xml.StorageIO;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.WellKnownItems;
import delta.games.lotro.utils.events.EventsManager;
import delta.games.lotro.utils.events.GenericEventsListener;

/**
 * Listens to storage updates to update currencies.
 * @author DAM
 */
public class CurrenciesUpdater
{
  private GenericEventsListener<AccountEvent> _accountEventsListener;

  /**
   * Constructor.
   */
  public CurrenciesUpdater()
  {
    initAccountsListener();
  }

  private void initAccountsListener()
  {
    _accountEventsListener=new GenericEventsListener<AccountEvent>()
    {
      @Override
      public void eventOccurred(AccountEvent event)
      {
        AccountEventType type=event.getType();
        if (type==AccountEventType.STORAGE_UPDATED)
        {
          String serverName=event.getProperties().getStringProperty(AccountEventProperties.SERVER_NAME,null);
          updateCurrenciesForAccount(event.getAccount(),serverName);
        }
      }
    };
    EventsManager.addListener(AccountEvent.class,_accountEventsListener);
  }

  private void updateCurrenciesForAccount(Account account, String serverName)
  {
    Wallet sharedWallet=StorageIO.loadAccountSharedWallet(account,serverName);
    // Marks
    CountedItem marks=sharedWallet.getById(WellKnownItems.MARK);
    if (marks!=null)
    {
      int quantity=marks.getQuantity();
      CurrenciesFacade.updateAccountServerCurrency(serverName,account.getName(),CurrencyKeys.MARKS,quantity,true);
    }
    // Medallions
    CountedItem medallions=sharedWallet.getById(WellKnownItems.MEDALLION);
    if (medallions!=null)
    {
      int quantity=medallions.getQuantity();
      CurrenciesFacade.updateAccountServerCurrency(serverName,account.getName(),CurrencyKeys.MEDALLIONS,quantity,true);
    }
    // Seals
    CountedItem seals=sharedWallet.getById(WellKnownItems.SEAL);
    if (seals!=null)
    {
      int quantity=seals.getQuantity();
      CurrenciesFacade.updateAccountServerCurrency(serverName,account.getName(),CurrencyKeys.SEALS,quantity,true);
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    EventsManager.removeListener(AccountEvent.class,_accountEventsListener);
  }
}
