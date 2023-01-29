package delta.games.lotro.account;

import java.util.Comparator;

/**
 * Comparator for accounts, using their game, then their game account name.
 * @author DAM
 */
public class AccountComparator implements Comparator<Account>
{
  @Override
  public int compare(Account o1, Account o2)
  {
    String name1=o1.getAccountName();
    String name2=o2.getAccountName();
    int ret=name1.compareTo(name2);
    if (ret!=0)
    {
      return ret;
    }
    String gameAccountName1=o1.getSubscriptionKey();
    String gameAccountName2=o2.getSubscriptionKey();
    return gameAccountName1.compareTo(gameAccountName2);
  }
}
