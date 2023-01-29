package delta.games.lotro.account;

/**
 * Storage class for a LOTRO account summary.
 * @author DAM
 */
public class AccountSummary
{
  private AccountReference _id;
  private Long _signupDate;
  private AccountType _accountType;

  /**
   * Constructor.
   */
  public AccountSummary()
  {
    _id=new AccountReference();
    _signupDate=null;
    _accountType=null;
  }

  /**
   * Get the account ID.
   * @return the account ID.
   */
  public AccountReference getAccountID()
  {
    return _id;
  }

  /**
   * Set the account ID.
   * @param id ID to set.
   */
  public void setAccountID(AccountReference id)
  {
    _id=id;
  }

  /**
   * Get a display name for this account.
   * @return A display name.
   */
  public String getDisplayName()
  {
    return _id.getDisplayName();
  }

  /**
   * Get the account name.
   * @return the account name.
   */
  public String getName()
  {
    return _id.getAccountName();
  }

  /**
   * Get the subscription key.
   * @return a key or an empty string.
   */
  public String getSubscriptionKey()
  {
    return _id.getSubscriptionKey();
  }

  /**
   * Get the signup date.
   * @return A timestamp or <code>null</code>.
   */
  public Long getSignupDate()
  {
    return _signupDate;
  }

  /**
   * Set the signup date.
   * @param signupDate Value to set (may be <code>null</code>).
   */
  public void setSignupDate(Long signupDate)
  {
    _signupDate=signupDate;
  }

  /**
   * Get the account type.
   * @return an account type or <code>null</code> if unspecified. 
   */
  public AccountType getAccountType()
  {
    return _accountType;
  }

  /**
   * Set the account type.
   * @param accountType Account type to set (may be <code>null</code>).
   */
  public void setAccountType(AccountType accountType)
  {
    _accountType=accountType;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Account [").append(_id).append("], ");
    sb.append("Signup date [").append(_signupDate).append("], ");
    sb.append("Account type [").append(_accountType).append(']');
    return sb.toString();
  }
}
