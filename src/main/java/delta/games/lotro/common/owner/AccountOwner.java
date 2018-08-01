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
}
