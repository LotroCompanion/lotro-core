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
  private String _userData;

  /**
   * Constructor.
   */
  public AccountSummary()
  {
    _id=new AccountReference();
    _signupDate=null;
    _accountType=null;
    _userData="";
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

  /**
   * Get the user data.
   * @return the user data.
   */
  public String getUserData()
  {
    return _userData;
  }

  /**
   * Set the user data.
   * @param userData User data to set.
   */
  public void setUserData(String userData)
  {
    if (userData==null)
    {
      userData="";
    }
    _userData=userData;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Account [").append(_id).append("], ");
    sb.append("Signup date [").append(_signupDate).append("], ");
    sb.append("Account type [").append(_accountType).append(']');
    sb.append("User data [").append(_userData).append(']');
    return sb.toString();
  }
}
