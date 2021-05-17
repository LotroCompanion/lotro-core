package delta.games.lotro.account;

import delta.games.lotro.common.id.InternalGameId;

/**
 * Storage class for a LOTRO account summary.
 * @author DAM
 */
public class AccountSummary
{
  private InternalGameId _accountID;
  private String _accountName;
  private Long _signupDate;
  private AccountType _accountType;

  /**
   * Constructor.
   */
  public AccountSummary()
  {
    _accountID=null;
    _accountName="";
  }

  /**
   * Get the account identifier.
   * @return an account identifier.
   */
  public InternalGameId getAccountID()
  {
    return _accountID;
  }

  /**
   * Set the account identifier.
   * @param accountID Account identifier to set.
   */
  public void setAccountID(InternalGameId accountID)
  {
    _accountID=accountID;
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
    sb.append("ID [").append(_accountID).append("], ");
    sb.append("Name [").append(_accountName).append("], ");
    sb.append("Signup date [").append(_signupDate).append("], ");
    sb.append("Account type [").append(_accountType).append(']');
    return sb.toString();
  }
}
