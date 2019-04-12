package delta.games.lotro.common.rewards;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.VirtueId;

/**
 * Virtue points reward.
 * @author DAM
 */
public class VirtueReward
{
  private VirtueId _identifier;
  private int _count;

  /**
   * Constructor.
   * @param id Virtue identifier.
   * @param count Virtue points count.
   */
  public VirtueReward(VirtueId id, int count)
  {
    _identifier=id;
    _count=count;
  }

  /**
   * Get the internal identifier.
   * @return the internal identifier.
   */
  public VirtueId getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the count of virtue points.
   * @return a count.
   */
  public int getCount()
  {
    return _count;
  }

  /**
   * Dump the contents of this virtue as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Virtue: +").append(_count).append(' ').append(_identifier);
    sb.append(EndOfLine.NATIVE_EOL);
    return sb.toString();
  }

  @Override
  public String toString()
  {
    return _identifier.toString()+'('+_count+')';
  }
}
