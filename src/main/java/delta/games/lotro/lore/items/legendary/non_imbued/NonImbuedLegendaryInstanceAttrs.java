package delta.games.lotro.lore.items.legendary.non_imbued;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.items.legendary.LegendaryConstants;

/**
 * Attributes of a non-imbued legendary item instance.
 * @author DAM
 */
public class NonImbuedLegendaryInstanceAttrs
{
  /**
   * Legendary level (max 60 or 70).
   */
  private int _legendaryItemLevel;
  /**
   * Number of upgrades (max 3).
   */
  private int _nbUpgrades;
  /**
   * Default legacy.
   * - Emblem: Tactical Healing Rating (Combat_TacticalHPS_Modifier)
   * - Belt: Shield Use Rank (Combat_TacticalDPS_Modifier)
   * - Satchel: Tactical Healing Rating (Combat_TacticalHPS_Modifier)
   */
  private DefaultNonImbuedLegacyInstance _defaultLegacy;
  /**
   * Non-imbued legacy instances.
   */
  private List<TieredNonImbuedLegacyInstance> _legacies;
  /**
   * Points spent.
   */
  private int _pointsSpent;
  /**
   * Points left.
   */
  private int _pointsLeft;

  /**
   * Constructor.
   */
  public NonImbuedLegendaryInstanceAttrs()
  {
    _legacies=new ArrayList<TieredNonImbuedLegacyInstance>();
    init();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public NonImbuedLegendaryInstanceAttrs(NonImbuedLegendaryInstanceAttrs source)
  {
    _legendaryItemLevel=source._legendaryItemLevel;
    _nbUpgrades=source._nbUpgrades;
    _defaultLegacy=new DefaultNonImbuedLegacyInstance(source._defaultLegacy);
    _legacies=new ArrayList<TieredNonImbuedLegacyInstance>();
    for(TieredNonImbuedLegacyInstance tieredLegacyInstance : source._legacies)
    {
      TieredNonImbuedLegacyInstance copy=new TieredNonImbuedLegacyInstance(tieredLegacyInstance);
      _legacies.add(copy);
    }
    _pointsSpent=source._pointsSpent;
    _pointsLeft=source._pointsLeft;
  }

  private void init()
  {
    _defaultLegacy=new DefaultNonImbuedLegacyInstance();
    for(int i=0;i<LegendaryConstants.MAX_LEGACIES;i++)
    {
      TieredNonImbuedLegacyInstance legacy=new TieredNonImbuedLegacyInstance();
      _legacies.add(legacy);
    }
  }

  /**
   * Get the legendary item level.
   * @return a legendary item level (max 60 or 70).
   */
  public int getLegendaryItemLevel()
  {
    return _legendaryItemLevel;
  }

  /**
   * Set the legendary item level.
   * @param legendaryItemLevel the value to set.
   */
  public void setLegendaryItemLevel(int legendaryItemLevel)
  {
    _legendaryItemLevel=legendaryItemLevel;
  }

  /**
   * Get the number of upgrades.
   * @return a count of upgrades.
   */
  public int getNbUpgrades()
  {
    return _nbUpgrades;
  }

  /**
   * Set the number of upgrades.
   * @param nbUpgrades the value to set (0-3).
   */
  public void setNbUpgrades(int nbUpgrades)
  {
    _nbUpgrades=nbUpgrades;
  }

  /**
   * Get the default legacy.
   * @return the default legacy.
   */
  public DefaultNonImbuedLegacyInstance getDefaultLegacy()
  {
    return _defaultLegacy;
  }

  /**
   * Get the legacy instance at the given index.
   * @param index Index of legacy, starting at 0.
   * @return A legacy instance.
   */
  public TieredNonImbuedLegacyInstance getLegacy(int index)
  {
    return _legacies.get(index);
  }

  /**
   * Get a list of all legacies.
   * @return a list of all legacies.
   */
  public List<TieredNonImbuedLegacyInstance> getLegacies()
  {
    return new ArrayList<TieredNonImbuedLegacyInstance>(_legacies);
  }

  /**
   * Get the number of points spent on this item.
   * @return a number of points.
   */
  public int getPointsSpent()
  {
    return _pointsSpent;
  }

  /**
   * Set the number of points spent on this item.
   * @param pointsSpent the value to set.
   */
  public void setPointsSpent(int pointsSpent)
  {
    _pointsSpent=pointsSpent;
  }

  /**
   * Get the number of points left on this item.
   * @return a number of points.
   */
  public int getPointsLeft()
  {
    return _pointsLeft;
  }

  /**
   * Set the number of points left on this item.
   * @param pointsLeft the value to set.
   */
  public void setPointsLeft(int pointsLeft)
  {
    _pointsLeft=pointsLeft;
  }

  /**
   * Get the total number of points on this item.
   * @return a number of points.
   */
  public int getTotalPoints()
  {
    return _pointsSpent+_pointsLeft;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Non-imbued: Legendary level=").append(_legendaryItemLevel);
    sb.append(", upgrades=").append(_nbUpgrades);
    sb.append(", points: left=").append(_pointsLeft);
    sb.append(",spent=").append(_pointsSpent);
    sb.append(",total=").append(getTotalPoints());
    sb.append(EndOfLine.NATIVE_EOL);
    if (_defaultLegacy!=null)
    {
      sb.append("\tDefault legacy: ").append(_defaultLegacy).append(EndOfLine.NATIVE_EOL);
    }
    int index=1;
    for(NonImbuedLegacyInstance legacy : _legacies)
    {
      sb.append("\tLegacy #").append(index).append(": ");
      sb.append(legacy).append(EndOfLine.NATIVE_EOL);
      index++;
    }
    return sb.toString().trim();
  }
}
