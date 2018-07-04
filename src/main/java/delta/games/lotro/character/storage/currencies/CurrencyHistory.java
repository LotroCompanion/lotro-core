package delta.games.lotro.character.storage.currencies;

/**
 * History for a single currency.
 * @author DAM
 */
public class CurrencyHistory
{
  private Currency _currency;
  private CurrencyStorage _history;

  /**
   * Constructor.
   * @param currency Associated currency.
   */
  public CurrencyHistory(Currency currency)
  {
    _currency=currency;
    _history=new CurrencyStorage();
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
   * Get the storage for the currency history.
   * @return A storage.
   */
  public CurrencyStorage getStorage()
  {
    return _history;
  }
}
