package delta.games.lotro.common.owner.comparators;

import java.util.Comparator;

import delta.games.lotro.common.owner.AccountOwner;

/**
 * Comparator for account owners.
 * @author DAM
 */
public class AccountOwnerComparator implements Comparator<AccountOwner>
{
  @Override
  public int compare(AccountOwner o1, AccountOwner o2)
  {
    return o1.getAccountName().compareTo(o2.getAccountName());
  }
}
