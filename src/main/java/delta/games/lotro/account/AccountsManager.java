package delta.games.lotro.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import delta.games.lotro.account.events.AccountEvent;
import delta.games.lotro.account.events.AccountEventType;
import delta.games.lotro.common.binding.BindingsManager;
import delta.games.lotro.utils.events.EventsManager;

/**
 * Manages all known accounts.
 * @author DAM
 */
public final class AccountsManager
{
  private static AccountsManager _instance=new AccountsManager();

  private AccountsStorageManager _storage;
  private List<Account> _accounts;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static AccountsManager getInstance()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private AccountsManager()
  {
    _storage=new AccountsStorageManager();
    _accounts=new ArrayList<Account>();
    List<Account> accounts=_storage.getAllAccounts();
    for(Account account : accounts)
    {
      _accounts.add(account);
      BindingsManager.getInstance().addAccount(account);
    }
  }

  /**
   * Get a list of all managed accounts, sorted by name.
   * @return a list of accounts.
   */
  public List<Account> getAllAccounts()
  {
    List<Account> accounts=new ArrayList<Account>(_accounts);
    return accounts;
  }

  /**
   * Get an account using external identifiers.
   * @param accountName Account name.
   * @param subscriptionKey Subscription key.
   * @return An account or <code>null</code> if not found.
   */
  public Account getAccount(String accountName, String subscriptionKey)
  {
    for(Account account : _accounts)
    {
      String currentAccountName=account.getAccountName();
      if (Objects.equals(currentAccountName,accountName))
      {
        String currentSubscriptionKey=account.getSubscriptionKey();
        if (Objects.equals(currentSubscriptionKey,subscriptionKey))
        {
          return account;
        }
      }
    }
    return null;
  }

  /**
   * Get an account using its ID.
   * @param id External ID to search.
   * @return An account or <code>null</code> if not found.
   */
  public Account getAccountByID(AccountReference id)
  {
    if (id==null)
    {
      return null;
    }
    Account ret=null;
    String subscriptionKey=id.getSubscriptionKey();
    if (subscriptionKey.length()>0)
    {
      ret=getAccountBySubscriptionKey(subscriptionKey);
    }
    if (ret!=null)
    {
      return ret;
    }
    String accountName=id.getAccountName();
    if (accountName.length()>0)
    {
      ret=getAccountByAccountName(accountName);
    }
    return ret;
  }

  /**
   * Get an account by subscription key.
   * @param subscriptionKey Subscription key.
   * @return An account or <code>null</code> if not found.
   */
  public Account getAccountBySubscriptionKey(String subscriptionKey)
  {
    for(Account account : _accounts)
    {
      String currentSubscriptionKey=account.getSubscriptionKey();
      if (Objects.equals(currentSubscriptionKey,subscriptionKey))
      {
        return account;
      }
    }
    return null;
  }

  /**
   * Get an account by name.
   * @param accountName Account name.
   * @return An account or <code>null</code> if not found.
   */
  public Account getAccountByAccountName(String accountName)
  {
    for(Account account : _accounts)
    {
      String currentAccountName=account.getAccountName();
      if (Objects.equals(currentAccountName,accountName))
      {
        return account;
      }
    }
    return null;
  }

  /**
   * Add a new account.
   * @param accountID Account ID.
   * @return An account or <code>null</code> if an error occurs.
   */
  public Account addAccount(AccountReference accountID)
  {
    Account account=_storage.newAccount(accountID);
    if (account!=null)
    {
      _accounts.add(account);
      BindingsManager.getInstance().addAccount(account);
      // Broadcast account creation event...
      AccountEvent event=new AccountEvent(AccountEventType.ACCOUNT_ADDED,account);
      EventsManager.invokeEvent(event);
    }
    return account;
  }

  /**
   * Remove an account.
   * @param account Targeted account.
   * @return <code>true</code> if successful, <code>false</code> otherwise.
   */
  public boolean removeAccount(Account account)
  {
    boolean ret=_accounts.remove(account);
    if (ret)
    {
      _storage.removeAccount(account);
      // Broadcast account deletion event...
      AccountEvent event=new AccountEvent(AccountEventType.ACCOUNT_REMOVED,account);
      EventsManager.invokeEvent(event);
    }
    return ret;
  }
}
