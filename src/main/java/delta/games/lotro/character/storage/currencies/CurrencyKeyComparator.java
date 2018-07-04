package delta.games.lotro.character.storage.currencies;

import java.util.Comparator;

/**
 * Comparator for currencies, using their key.
 * @author DAM
 */
public class CurrencyKeyComparator implements Comparator<Currency>
{
  @Override
  public int compare(Currency c1, Currency c2)
  {
    return c1.getKey().compareTo(c2.getKey());
  }
}
