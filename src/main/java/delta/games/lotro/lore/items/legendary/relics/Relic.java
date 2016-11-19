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
  private String _category;
  private String _iconFilename;
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
    _category=null;
    _iconFilename=null;
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

  /**
   * Get the icon filename for this relic.
   * @return a icon filename.
   */
  public String getIconFilename()
  {
    return _iconFilename;
  }

  /**
   * Set icon filename.
   * @param iconFilename Filename to set.
   */
  public void setIconFilename(String iconFilename)
  {
    _iconFilename=iconFilename;
  }

  /**
   * Get the category of this relic.
   * @return a relic category name.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set category.
   * @param category Category to set.
   */
  public void setCategory(String category)
  {
    _category=category;
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
    if (_iconFilename!=null)
    {
      sb.append("(icon=").append(_iconFilename).append(") ");
    }
    sb.append(_stats);
    return sb.toString();
  }
}
