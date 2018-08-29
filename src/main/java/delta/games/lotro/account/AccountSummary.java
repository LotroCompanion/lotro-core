package delta.games.lotro.account;

/**
 * Storage class for a LOTRO account summary.
 * @author DAM
 */
public class AccountSummary
{
  private String _accountName;
  private Long _signupDate;
  private AccountType _accountType;

  /**
   * Constructor.
   */
  public AccountSummary()
  {
    _accountName="";
  }

  /**
   * Copy constructor.
   * @param source Source character.
   */
  public AccountSummary(AccountSummary source)
  {
    _accountName=source._accountName;
  }

  /**
   * Get the account's name.
   * @return the account's name.
   */
  public String getName()
  {
    return _accountName;
  }

  /**
   * Set the account's name.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    if (name==null)
    {
      name="";
    }
    _accountName=name;
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
    sb.append("Name [").append(_accountName).append("], ");
    sb.append("Signup date [").append(_signupDate).append("], ");
    sb.append("Account type [").append(_accountType).append(']');
    return sb.toString();
  }
}
