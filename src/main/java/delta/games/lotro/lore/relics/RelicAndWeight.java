package delta.games.lotro.lore.relics;

import delta.games.lotro.lore.items.legendary.relics.Relic;

/**
 * A relic and a weight.
 * <ul>
 * <li>weight,
 * <li>relic.
 * </ul>
 * @author DAM
 */
public class RelicAndWeight
{
  private int _weight;
  private Relic _relic;

  /**
   * Constructor.
   * @param weight Weight.
   * @param relic Referenced relic.
   */
  public RelicAndWeight(int weight, Relic relic)
  {
    _weight=weight;
    _relic=relic;
  }

  /**
   * Get the weight.
   * @return the weight.
   */
  public int getWeight()
  {
    return _weight;
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
    sb.append('(').append(_weight).append(") ");
    sb.append(_relic.getName());
    return sb.toString();
  }
}
