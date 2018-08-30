package delta.games.lotro.common.owner.comparators;

import java.util.Comparator;

import delta.games.lotro.common.owner.CharacterOwner;

/**
 * Comparator for character owners.
 * @author DAM
 */
public class CharacterOwnerComparator implements Comparator<CharacterOwner>
{
  private AccountServerOwnerComparator _accountServerComparator=new AccountServerOwnerComparator();

  @Override
  public int compare(CharacterOwner o1, CharacterOwner o2)
  {
    int ret=_accountServerComparator.compare(o1.getServer(),o2.getServer());
    if (ret==0)
    {
      return o1.getCharacterName().compareTo(o2.getCharacterName());
    }
    return ret;
  }
}
