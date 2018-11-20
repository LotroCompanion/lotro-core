package delta.games.lotro.character.traits;

import delta.games.lotro.common.stats.StatsProvider;

/**
 * Trait.
 * @author DAM
 */
public class TraitDescription
{
  /**
   * Trait identifier.
   */
  private int _identifier;
  /**
   * Trait name (nevel <code>null</code>).
   */
  private String _name;
  /**
   * Trait description (nevel <code>null</code>).
   */
  private String _description;
  /**
   * Trait icon identifier.
   */
  private int _iconId;
  /**
   * Minimum level.
   */
  private int _minLevel;
  /**
   * Tiers.
   */
  private int _tiers;
  /**
   * Stats.
   */
  private StatsProvider _stats;
  // Category (enum?)
  // Nature (enum?)
  // Priority
  // Tooltip
  // Cost to slot
  // Cosmetic (boolean?)

  /**
   * Constructor.
   */
  public TraitDescription()
  {
    super();
    _identifier=0;
    _name="";
    _description="";
    _iconId=0;
    _minLevel=1;
    _tiers=1;
    _stats=new StatsProvider();
  }

  /**
   * Get the identifier of this trait.
   * @return a trait identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this trait.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the name of this trait.
   * @return a trait name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this trait.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the description of this trait.
   * @return a trait description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this trait.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    _description=description;
  }

  /**
   * Get the icon ID for this trait.
   * @return an icon ID.
   */
  public int getIconId()
  {
    return _iconId;
  }

  /**
   * Set the icon ID for this trait.
   * @param iconId Icon ID to set.
   */
  public void setIcon(int iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the minimum level for this trait.
   * @return a level.
   */
  public int getMinLevel()
  {
    return _minLevel;
  }

  /**
   * Set the minimum level for this trait.
   * @param minLevel the value to set.
   */
  public void setMinLevel(int minLevel)
  {
    _minLevel=minLevel;
  }

  /**
   * Get the number of tiers for this trait.
   * @return a number of tiers.
   */
  public int getTiersCount()
  {
    return _tiers;
  }

  /**
   * Set the number of tiers for this trait.
   * @param tiers the value to set.
   */
  public void setTiersCount(int tiers)
  {
    _tiers=tiers;
  }

  /**
   * Get the stats provider.
   * @return the stats provider.
   */
  public StatsProvider getStatsProvider()
  {
    return _stats;
  }
}
