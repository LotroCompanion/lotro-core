package delta.games.lotro.character.storage.currencies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.Scope;

/**
 * Access to all currencies.
 * @author DAM
 */
public class Currencies
{
  private static Currencies _instance=new Currencies();
  private HashMap<String,Currency> _currenciesMap;
  private List<Currency> _currencies;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static Currencies get()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private Currencies()
  {
    _currencies=new ArrayList<Currency>();
    _currenciesMap=new HashMap<String,Currency>();
    init();
  }

  private void init()
  {
    // Gold
    Currency gold=new Currency(CurrencyKeys.GOLD,"Gold",Scope.CHARACTER,"World");
    registerCurrency(gold);
    // Marks
    Currency marks=new Currency("marks","Marks",Scope.SERVER,"World");
    registerCurrency(marks);
    // Medallions
    Currency medallions=new Currency("medallions","Medallions",Scope.SERVER,"World");
    registerCurrency(medallions);
    // Destiny points
    Currency destinyPoints=new Currency("destinyPoints","Destiny Points",Scope.SERVER,"World");
    registerCurrency(destinyPoints);
    // LOTRO points
    Currency lotroPoints=new Currency("lotroPoints","Destiny Points",Scope.ACCOUNT,"World");
    registerCurrency(lotroPoints);
  }

  private void registerCurrency(Currency currency)
  {
    String key=currency.getKey();
    _currenciesMap.put(key,currency);
    _currencies.add(currency);
  }

  /**
   * Get a list of all known currencies.
   * @return a list of currencies.
   */
  public List<Currency> getCurrencies()
  {
    List<Currency> ret=new ArrayList<Currency>(_currencies);
    Collections.sort(ret,new CurrencyKeyComparator());
    return _currencies;
  }

  /**
   * Get a currency using its identifying key.
   * @param key Key of the currency to get.
   * @return A currency or <code>null</code> if not found.
   */
  public Currency getByKey(String key)
  {
    return _currenciesMap.get(key);
  }
}
