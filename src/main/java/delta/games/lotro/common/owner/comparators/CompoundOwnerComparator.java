package delta.games.lotro.common.owner.comparators;

import java.util.Comparator;

import delta.games.lotro.common.owner.CompoundOwner;

/**
 * Comparator for compound owners.
 * @author DAM
 */
public class CompoundOwnerComparator implements Comparator<CompoundOwner>
{
  private OwnerComparator _comparator;

  /**
   * Constructor.
   * @param comparator Comparator to use.
   */
  public CompoundOwnerComparator(OwnerComparator comparator)
  {
    _comparator=comparator;
  }

  @Override
  public int compare(CompoundOwner o1, CompoundOwner o2)
  {
    int size1=o1.getOwnersCount();
    int size2=o2.getOwnersCount();
    int min=Math.min(size1,size2);
    for(int i=0;i<min;i++)
    {
      int ret=_comparator.compare(o1.getOwnerAt(i),o2.getOwnerAt(i));
      if (ret!=0)
      {
        return ret;
      }
    }
    return Integer.compare(size1,size2);
  }
}
