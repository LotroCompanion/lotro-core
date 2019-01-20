package delta.games.lotro.lore.items.legendary;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Legacy description.
 * @author DAM
 */
public class Legacy implements Identifiable
{
  private int _id;
  private int _maxInitialLevel;
  private int _maxTier;
  private LegacyType _type;
  //private CharacterClass _requiredClass;
  //private boolean _imbued;
  private StatsProvider _statsProvider;

  /**
   * Constructor.
   */
  public Legacy()
  {
    // Nothing!
  }

  /**
   * Get the identifier of this legacy.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Set the identifier of this legacy.
   * @param id the identifier to set.
   */
  public void setId(int id)
  {
    _id=id;
  }

  /**
   * Get the maximum initial level.
   * @return the maximum initial level.
   */
  public int getMaxInitialLevel()
  {
    return _maxInitialLevel;
  }

  /**
   * Set the maximum initial level.
   * @param maxInitialLevel the maximum initial level to set.
   */
  public void setMaxInitialLevel(int maxInitialLevel)
  {
    _maxInitialLevel=maxInitialLevel;
  }

  /**
   * Get the maximum tier.
   * @return the maximum tier.
   */
  public int getMaxTier()
  {
    return _maxTier;
  }

  /**
   * Set the maximum tier.
   * @param maxTier the maximum tier to set.
   */
  public void setMaxTier(int maxTier)
  {
    _maxTier=maxTier;
  }

  /**
   * Get the legacy type.
   * @return a legacy type.
   */
  public LegacyType getType()
  {
    return _type;
  }

  /**
   * Set the legacy type.
   * @param type the type to set.
   */
  public void setType(LegacyType type)
  {
    _type=type;
  }

  /**
   * Get the stats provider.
   * @return the stats provider.
   */
  public StatsProvider getStatsProvider()
  {
    return _statsProvider;
  }

  /**
   * Set the stats provider.
   * @param statsProvider Stats provider.
   */
  public void setStatsProvider(StatsProvider statsProvider)
  {
    _statsProvider=statsProvider;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Legacy: ID=").append(_id);
    sb.append(", type=").append(_type);
    sb.append(", max tier=").append(_maxTier);
    sb.append(", stats=").append(_statsProvider);
    return sb.toString();
  }
}
