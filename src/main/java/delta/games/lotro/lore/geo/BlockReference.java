package delta.games.lotro.lore.geo;

/**
 * Storage for a block/cell reference.
 * @author DAM
 */
public class BlockReference
{
  private int _region;
  private int _blockX;
  private int _blockY;

  /**
   * Constructor.
   * @param region Region.
   * @param blockX Block X.
   * @param blockY Block Y.
   */
  public BlockReference(int region, int blockX, int blockY)
  {
    _region=region;
    _blockX=blockX;
    _blockY=blockY;
  }

  /**
   * Get the region code.
   * @return the region code.
   */
  public int getRegion()
  {
    return _region;
  }

  /**
   * Get the block X.
   * @return the block X.
   */
  public int getBlockX()
  {
    return _blockX;
  }

  /**
   * Get the block Y.
   * @return the block Y.
   */
  public int getBlockY()
  {
    return _blockY;
  }

  @Override
  public String toString()
  {
    return "R="+_region+",bx="+_blockX+",by="+_blockY;
  }

  @Override
  public int hashCode()
  {
    return _region+_blockX+_blockY;
  }

  @Override
  public boolean equals(Object other)
  {
    if (other instanceof BlockReference)
    {
      BlockReference otherBlock=(BlockReference)other;
      return (_region==otherBlock._region) && (_blockX==otherBlock._blockX) && (_blockY==otherBlock._blockY);
    }
    return false;
  }
}
