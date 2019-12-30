package delta.games.lotro.common.money.comparator;

import java.util.Comparator;

import delta.games.lotro.common.money.Money;

/**
 * Comparator for money objects.
 * @author DAM
 */
public class MoneyComparator implements Comparator<Money>
{
  @Override
  public int compare(Money o1, Money o2)
  {
    return Integer.compare(o1.getInternalValue(),o2.getInternalValue());
  }
}
