package delta.games.lotro.lore.items.legendary.passives;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Passive.
 * @author DAM
 */
public class Passive implements Identifiable
{
  // Identifier
  private int _id;
  // Stats
  private StatsProvider _statsProvider;

  /**
   * Constructor.
   */
  public Passive()
  {
    // Nothing!
  }

  /**
   * Get the identifier of this passive.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Set the identifier of this passive.
   * @param id the identifier to set.
   */
  public void setId(int id)
  {
    _id=id;
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
   * Get a displayable label for this passive.
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

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Passive: ID=").append(_id);
    sb.append(", stats=").append(_statsProvider);
    return sb.toString();
  }
}
