package delta.games.lotro.character.comparators;

import java.util.Comparator;

import delta.games.lotro.account.AccountReference;
import delta.games.lotro.character.BaseCharacterSummary;

/**
 * Comparator for character summaries using the character account.
 * @author DAM
 * @param <T> Actual type of compared objects.
 */
public class CharacterAccountComparator<T extends BaseCharacterSummary> implements Comparator<T>
{
  @Override
  public int compare(T o1, T o2)
  {
    AccountReference id1=o1.getAccountID();
    AccountReference id2=o2.getAccountID();
    if (id1==null)
    {
      return (id2==null)?0:-1;
    }
    if (id2==null)
    {
      return 1;
    }
    String name1=id1.getAccountName();
    String name2=id2.getAccountName();
    int ret=name1.compareTo(name2);
    if (ret==0)
    {
      String subscription1=id1.getSubscriptionKey();
      String subscription2=id2.getSubscriptionKey();
      return subscription1.compareTo(subscription2);
    }
    return ret;
  }
}
