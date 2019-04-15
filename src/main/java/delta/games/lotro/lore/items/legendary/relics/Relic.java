package delta.games.lotro.lore.items.legendary.relics;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.Identifiable;

/**
 * Relic description.
 * @author DAM
 */
public class Relic implements Identifiable
{
  private int _identifier;
  private String _name;
  private RelicType _type;
  private boolean _bridleRelic;
  private String _category;
  private String _iconFilename;
  private Integer _requiredLevel;
  private BasicStatsSet _stats;

  /**
   * Constructor.
   * @param identifier Identifier.
   * @param name Relic name.
   * @param type Type (setting/rune/gem/crafted).
   * @param requiredLevel Required character level.
   */
  public Relic(int identifier, String name, RelicType type, Integer requiredLevel)
  {
    _identifier=identifier;
    _name=name;
    _type=type;
    _bridleRelic=false;
    _category=null;
    _iconFilename=null;
    _requiredLevel=requiredLevel;
    _stats=new BasicStatsSet();
  }

  /**
   * Get the identifier of this relic.
   * @return a relic identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
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
   * Indicates if this is a relic for a bridle or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isBridleRelic()
  {
    return _bridleRelic;
  }

  /**
   * Set the 'bridle relic' flag.
   * @param bridleRelic Indicates if it is a relic for a bridle or not.
   */
  public void setBridleRelic(boolean bridleRelic)
  {
    _bridleRelic=bridleRelic;
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
   * Set the required level.
   * @param requiredLevel Level to set or <code>null</code>.
   */
  public void setRequiredLevel(Integer requiredLevel)
  {
    _requiredLevel=requiredLevel;
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
    if (_identifier!=0)
    {
      sb.append("(ID=").append(_identifier).append(") ");
    }
    if (_requiredLevel!=null)
    {
      sb.append("(min ").append(_requiredLevel).append(") ");
    }
    if (_bridleRelic)
    {
      sb.append("(bridle) ");
    }
    if (_iconFilename!=null)
    {
      sb.append("(icon=").append(_iconFilename).append(") ");
    }
    sb.append(_stats);
    return sb.toString();
  }
}
