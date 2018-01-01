package delta.games.lotro.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    _accounts.addAll(_storage.getAllAccounts());
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
   * Get an account by name.
   * @param accountName Account name.
   * @return An account or <code>null</code> if not found.
   */
  public Account getAccountByName(String accountName)
  {
    for(Account account : _accounts)
    {
      String name=account.getName();
      if (Objects.equals(name,accountName))
      {
        return account;
      }
    }
    return null;
  }

  /**
   * Add a new account.
   * @param accountName Account name.
   * @return An account or <code>null</code> if an error occurs.
   */
  public Account addAccount(String accountName)
  {
    Account file=_storage.newAccount(accountName);
    if (file!=null)
    {
      _accounts.add(file);
      // Broadcast account creation event...
      // TODO
    }
    return file;
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
      // TODO
    }
    return ret;
  }
}
