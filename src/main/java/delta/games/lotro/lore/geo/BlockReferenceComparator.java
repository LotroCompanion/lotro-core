package delta.games.lotro.lore.geo;

import java.util.Comparator;

/**
 * Comparator for block references.
 * @author DAM
 */
public class BlockReferenceComparator implements Comparator<BlockReference>
{
  @Override
  public int compare(BlockReference o1, BlockReference o2)
  {
    // Region
    int regionCompare=Integer.compare(o1.getRegion(),o2.getRegion());
    if (regionCompare!=0)
    {
      return regionCompare;
    }
    // X
    int xCompare=Integer.compare(o1.getBlockX(),o2.getBlockX());
    if (xCompare!=0)
    {
      return xCompare;
    }
    // Y
    int yCompare=Integer.compare(o1.getBlockY(),o2.getBlockY());
    return yCompare;
  }
}
