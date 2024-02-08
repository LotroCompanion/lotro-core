package delta.games.lotro.lore.items.legendary.imbued;

import java.util.Set;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.legendary.AbstractLegacy;

/**
 * Imbued legacy description.
 * @author DAM
 */
public class ImbuedLegacy extends AbstractLegacy implements Identifiable 
{
  private int _id;
  private int _maxInitialLevel;
  private int _maxLevel;
  private StatsProvider _statsProvider;
  private int _effectID;
  private Set<WeaponType> _types;

  /**
   * Constructor.
   */
  public ImbuedLegacy()
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
  public void setIdentifier(int id)
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
   * Get the maximum level.
   * @return the maximum level.
   */
  public int getMaxLevel()
  {
    return _maxLevel;
  }

  /**
   * Set the maximum level.
   * @param maxLevel the maximum level to set.
   */
  public void setMaxLevel(int maxLevel)
  {
    _maxLevel=maxLevel;
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

  /**
   * Get a displayable label for this effect.
   * @return a displayable label.
   */
  public String getLabel()
  {
    if (_statsProvider!=null)
    {
      return _statsProvider.getLabel();
    }
    return "?";
  }

  /**
   * Get the effect ID.
   * @return the effect ID.
   */
  public int getEffectID()
  {
    return _effectID;
  }

  /**
   * Set the effect ID.
   * @param effectID Effect ID to set.
   */
  public void setEffectID(int effectID)
  {
    _effectID=effectID;
  }

  /**
   * Get the allowed weapon types for this legacy.
   * @return A set of weapon types or <code>null</code> if not defined.
   */
  public Set<WeaponType> getAllowedWeaponTypes()
  {
    return _types;
  }

  /**
   * Set the allowed weapon types for this legacy.
   * @param types Weapon types to set.
   */
  public void setAllowedWeaponTypes(Set<WeaponType> types)
  {
    _types=types;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Legacy: ID=").append(_id);
    sb.append(", type=").append(getType());
    sb.append(", max tier=").append(_maxLevel);
    sb.append(", stats=").append(_statsProvider);
    if (_types!=null)
    {
      sb.append(", types=").append(_types);
    }
    return sb.toString();
  }
}
