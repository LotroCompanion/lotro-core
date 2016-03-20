package delta.games.lotro.character.legendary.relics;

import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * Relic description.
 * @author DAM
 */
public class Relic
{
  private String _name;
  private RelicType _type;
  private int _itemLevel;
  private int _requiredLevel;
  private BasicStatsSet _stats;

  /**
   * Constructor.
   * @param name Relic name.
   * @param type Type (setting/rune/gem/crafted).
   * @param level Item level.
   * @param requiredLevel Required character level.
   */
  public Relic(String name, RelicType type, int level, int requiredLevel)
  {
    _name=name;
    _type=type;
    _itemLevel=level;
    _requiredLevel=requiredLevel;
    _stats=new BasicStatsSet();
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return _name;
  }

  /**
   * @return the type
   */
  public RelicType getType()
  {
    return _type;
  }

  /**
   * @return the itemLevel
   */
  public int getItemLevel()
  {
    return _itemLevel;
  }

  /**
   * @return the requiredLevel
   */
  public int getRequiredLevel()
  {
    return _requiredLevel;
  }

  /**
   * @return the stats
   */
  public BasicStatsSet getStats()
  {
    return _stats;
  }
}
