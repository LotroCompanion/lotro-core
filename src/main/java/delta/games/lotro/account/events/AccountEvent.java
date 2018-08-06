package delta.games.lotro.account.events;

import delta.games.lotro.account.Account;
import delta.games.lotro.utils.events.Event;

/**
 * Data for a account event.
 * @author DAM
 */
public class AccountEvent extends Event
{
  private AccountEventType _type;
  private Account _account;

  /**
   * Constructor.
   * @param type Event type.
   * @param account Targeted account.
   */
  public AccountEvent(AccountEventType type, Account account)
  {
    _type=type;
    _account=account;
  }

  /**
   * Get the type of this event.
   * @return An account event type.
   */
  public AccountEventType getType()
  {
    return _type;
  }

  /**
   * Get the targeted account.
   * @return the targeted account.
   */
  public Account getAccount()
  {
    return _account;
  }
}
