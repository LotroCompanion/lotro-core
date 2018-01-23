package delta.games.lotro.common;

import delta.common.utils.text.EndOfLine;

/**
 * Virtue points reward.
 * @author DAM
 */
public class Virtue
{
  private VirtueId _identifier;
  private int _count;

  /**
   * Constructor.
   * @param id Virtue identifier.
   * @param count Virtue points count.
   */
  public Virtue(VirtueId id, int count)
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
