package delta.games.lotro.character.storage.currencies;

import java.util.Comparator;

/**
 * Comparator for currencies, using their name.
 * @author DAM
 */
public class CurrencyNameComparator implements Comparator<Currency>
{
  @Override
  public int compare(Currency c1, Currency c2)
  {
    return c1.getName().compareTo(c2.getName());
  }
}
