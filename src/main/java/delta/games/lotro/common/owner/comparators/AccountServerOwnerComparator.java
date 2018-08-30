package delta.games.lotro.common.owner.comparators;

import java.util.Comparator;

import delta.games.lotro.common.owner.AccountServerOwner;

/**
 * Comparator for account/server owners.
 * @author DAM
 */
public class AccountServerOwnerComparator implements Comparator<AccountServerOwner>
{
  private AccountOwnerComparator _accountComparator=new AccountOwnerComparator();

  @Override
  public int compare(AccountServerOwner o1, AccountServerOwner o2)
  {
    int ret=_accountComparator.compare(o1.getAccount(),o2.getAccount());
    if (ret==0)
    {
      return o1.getServerName().compareTo(o2.getServerName());
    }
    return ret;
  }
}
