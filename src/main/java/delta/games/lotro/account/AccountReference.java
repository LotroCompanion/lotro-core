package delta.games.lotro.account;

/**
 * Account reference.
 * @author DAM
 */
public class AccountReference
{
  private String _accountName;
  private String _subscriptionKey;

  /**
   * Constructor.
   */
  public AccountReference()
  {
    _accountName="";
    _subscriptionKey="";
  }

  /**
   * Constructor.
   * @param accountName Account name.
   * @param subscriptionKey Subscription key.
   */
  public AccountReference(String accountName, String subscriptionKey)
  {
    _accountName="";
    if (accountName!=null)
    {
      _accountName=accountName;
    }
    _subscriptionKey="";
    if (subscriptionKey!=null)
    {
      _subscriptionKey=subscriptionKey;
    }
  }

  /**
   * Get the account name.
   * @return the account name.
   */
  public String getAccountName()
  {
    return _accountName;
  }

  /**
   * Get the subscription key.
   * @return a key or an empty string.
   */
  public String getSubscriptionKey()
  {
    return _subscriptionKey;
  }

  /**
   * Get a string identifier for this account ID.
   * @return a string identifier.
   */
  public String getExternalID()
  {
    return getDisplayName();
  }

  /**
   * Get a display name for this account.
   * @return A display name.
   */
  public String getDisplayName()
  {
    String ret=_accountName;
    if (_subscriptionKey.length()>0)
    {
      ret=ret+"/"+_subscriptionKey;
    }
    return ret;
  }

  @Override
  public String toString()
  {
    return getDisplayName();
  }
}
