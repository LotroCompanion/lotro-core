package delta.games.lotro.common.owner;

/**
 * Owner of type 'account'.
 * @author DAM
 */
public class AccountOwner extends Owner
{
  private String _accountName;

  /**
   * Constructor.
   * @param accountName Account name.
   */
  public AccountOwner(String accountName)
  {
    _accountName=accountName;
  }

  /**
   * Get the name of the account.
   * @return an account name.
   */
  public String getAccountName()
  {
    return _accountName;
  }

  @Override
  public String getLabel()
  {
    return getAccountName();
  }

  @Override
  public boolean equals(Object object)
  {
    if (this == object)
    {
      return true;
    }
    if (!(object instanceof AccountOwner))
    {
      return false;
    }
    AccountOwner other=(AccountOwner)object;
    return _accountName.equals(other._accountName);
  }

  @Override
  public int hashCode()
  {
    return _accountName.hashCode();
  }
}
