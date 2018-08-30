package delta.games.lotro.common.owner.comparators;

import java.util.Comparator;

import delta.games.lotro.common.owner.AccountOwner;
import delta.games.lotro.common.owner.AccountServerOwner;
import delta.games.lotro.common.owner.CharacterOwner;
import delta.games.lotro.common.owner.CompoundOwner;
import delta.games.lotro.common.owner.Owner;

/**
 * Comparator for all owner types.
 * @author DAM
 */
public class OwnerComparator implements Comparator<Owner>
{
  private CharacterOwnerComparator _characterComparator=new CharacterOwnerComparator();
  private AccountServerOwnerComparator _accountServerComparator=new AccountServerOwnerComparator();
  private AccountOwnerComparator _accountComparator=new AccountOwnerComparator();
  private CompoundOwnerComparator _compoundComparator=new CompoundOwnerComparator(this);

  @Override
  public int compare(Owner o1, Owner o2)
  {
    int index1=getOwnerIndex(o1);
    int index2=getOwnerIndex(o2);
    if (index1!=index2)
    {
      return index1-index2;
    }
    if (index1==1)
    {
      return _accountComparator.compare((AccountOwner)o1,(AccountOwner)o2);
    }
    if (index1==2)
    {
      return _accountServerComparator.compare((AccountServerOwner)o1,(AccountServerOwner)o2);
    }
    if (index1==3)
    {
      return _characterComparator.compare((CharacterOwner)o1,(CharacterOwner)o2);
    }
    if (index1==4)
    {
      return _compoundComparator.compare((CompoundOwner)o1,(CompoundOwner)o2);
    }
    return 0;
  }

  private int getOwnerIndex(Owner owner)
  {
    if (owner instanceof AccountOwner) return 1;
    if (owner instanceof AccountServerOwner) return 2;
    if (owner instanceof CharacterOwner) return 3;
    if (owner instanceof CompoundOwner) return 4;
    return 5;
  }
}
