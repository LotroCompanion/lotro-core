package delta.games.lotro.common;

import delta.games.lotro.common.stats.StatsProvider;

/**
 * Effect.
 * @author DAM
 */
public class Effect implements Identifiable
{
  // Identifier
  private int _id;
  // Name?
  // Stats
  private StatsProvider _statsProvider;

  /**
   * Constructor.
   */
  public Effect()
  {
    // Nothing
  }

  /**
   * Get the identifier of this effect.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Set the identifier of this effect.
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

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Effect: ID=").append(_id);
    sb.append(", stats=").append(_statsProvider);
    return sb.toString();
  }
}
