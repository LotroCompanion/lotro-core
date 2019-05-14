package delta.games.lotro.common;

import java.util.Comparator;

/**
 * Comparator for repeatabilites.
 * @author DAM
 */
public class RepeatabilityComparator implements Comparator<Repeatability>
{
  @Override
  public int compare(Repeatability o1, Repeatability o2)
  {
    byte id1=o1.getCode();
    byte id2=o2.getCode();
    if (id1==id2) return 0;
    // Order:
    // 0 (not repeatable)
    // n (>0) times repeatable
    // -1 (infinitely repeatable)
    if (id1==0) return -1;
    if (id1==-1) return 1;
    if (id2==0) return 1;
    if (id2==-1) return -1;
    return id1-id2;
  }
}
