package delta.games.lotro.account;

/**
 * Account type.
 * @author DAM
 */
public enum AccountType
{
  /**
   * Free to play.
   */
  FREE_TO_PLAY("Free to play"),
  /**
   * Premimum.
   */
  PREMIUM("Premium"),
  /**
   * VIP.
   */
  VIP("VIP"),
  /**
   * Founder.
   */
  FOUNDER("Founder"),
  /**
   * Lifetime.
   */
  LIFETIME("Lifetime");

  private String _name;

  /**
   * Constructor.
   * @param name Display label.
   */
  private AccountType(String name)
  {
    _name=name;
  }

  /**
   * Get the display label for this account type.
   * @return a displayable label.
   */
  public String getName()
  {
    return _name;
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
