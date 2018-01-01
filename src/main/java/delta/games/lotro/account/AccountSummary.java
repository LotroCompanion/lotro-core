package delta.games.lotro.account;

/**
 * Storage class for a LOTRO account summary.
 * @author DAM
 */
public class AccountSummary
{
  private String _accountName;
  // Creation date
  // Associated toons

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

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Name [").append(_accountName).append("], ");
    return sb.toString();
  }
}
