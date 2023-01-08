package delta.games.lotro.character.storage.currencies;

import java.util.Date;

/**
 * Status of a single currency.
 * @author DAM
 */
public class CurrencyStatus
{
  private Currency _currency;
  private long _date;
  private int _value;

  /**
   * Constructor.
   * @param currency Associated currency.
   */
  public CurrencyStatus(Currency currency)
  {
    _currency=currency;
  }

  /**
   * Get the associated currency.
   * @return a currency.
   */
  public Currency getCurrency()
  {
    return _currency;
  }

  /**
   * Get the validity date of this status.
   * @return A date.
   */
  public long getDate()
  {
    return _date;
  }

  /**
   * Set the validity date of this status.
   * @param date Validity date to set.
   */
  public void setDate(long date)
  {
    _date=date;
  }

  /**
   * Get the value for this currency.
   * @return a value/count.
   */
  public int getValue()
  {
    return _value;
  }

  /**
   * Set the value for this currency.
   * @param value the value to set.
   */
  public void setValue(int value)
  {
    _value=value;
  }

  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Currency: ").append(_currency.getName());
    sb.append(", value=").append(_value);
    sb.append(", date=").append(_date);
    sb.append(" (").append(new Date(_date)).append(')');
    return sb.toString();
  }
}
