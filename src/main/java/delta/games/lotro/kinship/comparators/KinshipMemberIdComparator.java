package delta.games.lotro.kinship.comparators;

import java.util.Comparator;

import delta.games.lotro.kinship.KinshipMember;

/**
 * Comparator for kinship members, using their ID.
 * @author DAM
 */
public class KinshipMemberIdComparator implements Comparator<KinshipMember>
{
  public int compare(KinshipMember o1, KinshipMember o2)
  {
    Long id1=o1.getID();
    Long id2=o2.getID();
    return Long.compare(id1.longValue(),id2.longValue());
  }
}
