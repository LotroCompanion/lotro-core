package delta.games.lotro.lore.items.legendary.relics;

import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * Relic description.
 * @author DAM
 */
public class Relic
{
  private String _name;
  private RelicType _type;
  // TODO add category: tier 1..10, lvl55, mounted...
  private Integer _requiredLevel;
  private BasicStatsSet _stats;

  /**
   * Constructor.
   * @param name Relic name.
   * @param type Type (setting/rune/gem/crafted).
   * @param requiredLevel Required character level.
   */
  public Relic(String name, RelicType type, Integer requiredLevel)
  {
    _name=name;
    _type=type;
    _requiredLevel=requiredLevel;
    _stats=new BasicStatsSet();
  }

  /**
   * Get the relic name.
   * @return the relic name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the relic type.
   * @return the relic type.
   */
  public RelicType getType()
  {
    return _type;
  }

  /**
   * Get the required item level for this relic.
   * @return an item level.
   */
  public Integer getRequiredLevel()
  {
    return _requiredLevel;
  }

  /**
   * Get the stats of this relic.
   * @return a set of stats.
   */
  public BasicStatsSet getStats()
  {
    return _stats;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_name);
    sb.append(" (").append(_type).append(") ");
    if (_requiredLevel!=null)
    {
      sb.append("(min ").append(_requiredLevel).append(") ");
    }
    sb.append(_stats);
    return sb.toString();
  }
}
