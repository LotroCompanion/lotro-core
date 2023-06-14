package delta.games.lotro.common.effects;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Effect.
 * @author DAM
 */
public class Effect implements Identifiable,Named
{
  // Identifier
  private int _id;
  // Name
  private String _name;
  // Duration
  private Float _duration;
  // Icon
  private Integer _iconId;
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
   * Get the effect name.
   * @return a name or <code>null</code>.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the effect name.
   * @param name Name to set (may be <code>null</code>).
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the effect duration.
   * @return a duration (seconds) or <code>null</code>.
   */
  public Float getDuration()
  {
    return _duration;
  }

  /**
   * Set the effect duration.
   * @param duration Duration to set (seconds) (may be <code>null</code>).
   */
  public void setDuration(Float duration)
  {
    _duration=duration;
  }

  /**
   * Get the icon ID.
   * @return an icon ID or <code>null</code> if none.
   */
  public Integer getIconId()
  {
    return _iconId;
  }

  /**
   * Set the icon ID.
   * @param iconId Icon ID to set.
   */
  public void setIconId(Integer iconId)
  {
    _iconId=iconId;
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
    if (_name!=null)
    {
      sb.append(", (name=").append(_name).append(')');
    }
    if (_iconId!=null)
    {
      sb.append(", (icon=").append(_iconId).append(')');
    }
    if (_duration!=null)
    {
      sb.append(", (duration=").append(_duration).append("s)");
    }
    sb.append(", stats=").append(_statsProvider);
    return sb.toString();
  }
}
