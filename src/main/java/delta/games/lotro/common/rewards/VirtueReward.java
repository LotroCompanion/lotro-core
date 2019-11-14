package delta.games.lotro.common.rewards;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.virtues.VirtueDescription;

/**
 * Virtue points reward.
 * @author DAM
 */
public class VirtueReward extends RewardElement
{
  private VirtueDescription _virtue;
  private int _count;

  /**
   * Constructor.
   * @param virtue Virtue.
   * @param count Virtue points count.
   */
  public VirtueReward(VirtueDescription virtue, int count)
  {
    _virtue=virtue;
    _count=count;
  }

  /**
   * Get the internal identifier.
   * @return the internal identifier.
   */
  public VirtueDescription getVirtue()
  {
    return _virtue;
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
    sb.append("Virtue: +").append(_count).append(' ').append(_virtue.getName());
    sb.append(EndOfLine.NATIVE_EOL);
    return sb.toString();
  }

  @Override
  public String toString()
  {
    return _virtue.getPersistenceKey()+'('+_count+')';
  }
}
