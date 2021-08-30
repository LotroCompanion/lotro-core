package delta.games.lotro.lore.relics;

import delta.games.lotro.lore.items.legendary.relics.Relic;

/**
 * A relic and a count.
 * @author DAM
 */
public class CountedRelic
{
  private Relic _relic;
  private int _count;

  /**
   * Constructor.
   * @param relic Referenced relic.
   * @param count Count.
   */
  public CountedRelic(Relic relic, int count)
  {
    _relic=relic;
    _count=count;
  }

  /**
   * Get the count.
   * @return the count.
   */
  public int getCount()
  {
    return _count;
  }

  /**
   * Get the referenced relic.
   * @return a relic.
   */
  public Relic getRelic()
  {
    return _relic;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_count).append(' ');
    sb.append(_relic.getName());
    return sb.toString();
  }
}
