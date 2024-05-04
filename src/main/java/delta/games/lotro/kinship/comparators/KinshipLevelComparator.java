package delta.games.lotro.kinship.comparators;

import java.util.Comparator;

import delta.games.lotro.kinship.KinshipRank;

/**
 * Comparator for kinship ranks, using their level.
 * @author DAM
 */
public class KinshipLevelComparator implements Comparator<KinshipRank>
{
  public int compare(KinshipRank o1, KinshipRank o2)
  {
    int level1=o1.getLevel();
    int level2=o2.getLevel();
    return level1-level2;
  }
}
