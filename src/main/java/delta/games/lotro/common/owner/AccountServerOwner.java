package delta.games.lotro.common.owner;

import java.util.Objects;

/**
 * Owner of type 'account and server'.
 * @author DAM
 */
public class AccountServerOwner extends Owner
{
  private AccountOwner _account;
  private String _serverName;

  /**
   * Constructor.
   * @param account Parent account.
   * @param serverName Server name.
   */
  public AccountServerOwner(AccountOwner account, String serverName)
  {
    _account=account;
    _serverName=serverName;
  }

  /**
   * Get the parent owner.
   * @return the parent owner.
   */
  public AccountOwner getAccount()
  {
    return _account;
  }

  /**
   * Get the account name.
   * @return the account name.
   */
  public String getAccountName()
  {
    return _account.getAccountName();
  }

  /**
   * Get the server name.
   * @return the server name.
   */
  public String getServerName()
  {
    return _serverName;
  }

  @Override
  public String getLabel()
  {
    return getAccountName()+"/"+_serverName;
  }

  @Override
  public boolean equals(Object object)
  {
    if (this == object)
    {
      return true;
    }
    if (!(object instanceof AccountServerOwner))
    {
      return false;
    }
    AccountServerOwner other=(AccountServerOwner)object;
    if (!_serverName.equals(other._serverName))
    {
      return false;
    }
    return Objects.equals(_account,other._account);
  }
}
