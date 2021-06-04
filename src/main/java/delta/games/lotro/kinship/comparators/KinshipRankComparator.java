package delta.games.lotro.kinship.comparators;

import java.util.Comparator;

import delta.games.lotro.kinship.KinshipRank;

/**
 * Comparator for kinship ranks, using their code.
 * @author DAM
 */
public class KinshipRankComparator implements Comparator<KinshipRank>
{
  public int compare(KinshipRank o1, KinshipRank o2)
  {
    int id1=o1.getCode();
    int id2=o2.getCode();
    return id1-id2;
  }
}
