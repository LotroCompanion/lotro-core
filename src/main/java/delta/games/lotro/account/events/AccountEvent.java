package delta.games.lotro.account.events;

import delta.common.utils.misc.TypedProperties;
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
  private TypedProperties _props;

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

  /**
   * Get the properties associated with this event.
   * @return some typed properties.
   */
  public TypedProperties getProperties()
  {
    if (_props==null)
    {
      _props=new TypedProperties();
    }
    return _props;
  }
}
