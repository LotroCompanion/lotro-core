package delta.games.lotro.character.storage.currencies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Summary of all currencies in a scope (character/server/account).
 * @author DAM
 */
public class CurrenciesSummary
{
  private long _date;
  private Map<String,CurrencyStatus> _status;

  /**
   * Constructor.
   */
  public CurrenciesSummary()
  {
    _date=0;
    _status=new HashMap<String,CurrencyStatus>();
  }

  /**
   * Get the validity date.
   * @return the validity date.
   */
  public long getDate()
  {
    return _date;
  }

  /**
   * Set the validity date.
   * @param date Date to set.
   */
  public void setDate(long date)
  {
    _date=date;
  }

  /**
   * Add a currency status.
   * @param status Currency status to add.
   */
  public void addCurrencyStatus(CurrencyStatus status)
  {
    String key=status.getCurrency().getKey();
    _status.put(key,status);
  }

  /**
   * List of currency statuses, ordered by currency key.
   * @return A possibly empty but not <code>null</code> list of currency statuses.
   */
  public List<CurrencyStatus> getCurrencies()
  {
    List<String> keys=new ArrayList<String>(_status.keySet());
    Collections.sort(keys);
    List<CurrencyStatus> ret=new ArrayList<CurrencyStatus>();
    for(String key : keys)
    {
      ret.add(_status.get(key));
    }
    return ret;
  }

  /**
   * Get a currency status.
   * @param key Key of the currency to get.
   * @param createIfNecessary Create a new status if needed.
   * @return A currency status or <code>null</code> if not found.
   */
  public CurrencyStatus getCurrency(String key, boolean createIfNecessary)
  {
    CurrencyStatus status=_status.get(key);
    if ((createIfNecessary) && (status==null))
    {
      Currency currency=Currencies.get().getByKey(key);
      if (currency!=null)
      {
        status=new CurrencyStatus(currency);
        _status.put(key,status);
      }
    }
    return status;
  }
}
