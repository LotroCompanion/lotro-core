package delta.games.lotro.common;

/**
 * Scope.
 * @author DAM
 */
public class Scope
{
  /**
   * Character.
   */
  public static final Scope CHARACTER=new Scope("character","Character");
  /**
   * Server/account.
   */
  public static final Scope SERVER=new Scope("server","Server/Account");
  /**
   * Account.
   */
  public static final Scope ACCOUNT=new Scope("account","Account");

  private String _key;
  private String _label;

  private Scope(String key, String label)
  {
    _key=key;
    _label=label;
  }

  public String getKey()
  {
    return _key;
  }

  public String getLabel()
  {
    return _label;
  }

  @Override
  public String toString()
  {
    return _key;
  }
}
