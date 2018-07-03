package delta.games.lotro.character.currency;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Scope;

/**
 * Access to all currencies.
 * @author DAM
 */
public class Currencies
{
  private static List<Currency> _currencies;

  /**
   * Get a list of all known currencies.
   * @return a list of currencies.
   */
  public static List<Currency> getCurrencies()
  {
    if (_currencies==null)
    {
      init();
    }
    return _currencies;
  }

  private static void init()
  {
    _currencies=new ArrayList<Currency>();
    // Gold
    Currency gold=new Currency("gold","Gold",Scope.CHARACTER,"World");
    _currencies.add(gold);
    // Marks
    Currency marks=new Currency("marks","Marks",Scope.SERVER,"World");
    _currencies.add(marks);
    // Medallions
    Currency medallions=new Currency("medallions","Medallions",Scope.SERVER,"World");
    _currencies.add(medallions);
  }
}
