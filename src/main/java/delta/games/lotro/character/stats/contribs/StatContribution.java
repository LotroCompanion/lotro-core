package delta.games.lotro.character.stats.contribs;

import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Contribution of a source to a stat.
 * @author DAM
 */
public class StatContribution
{
  private StatContributionSource _source;
  private FixedDecimalsInteger _value;

  /**
   * Constructor.
   * @param source Source.
   * @param value Stat contribution.
   */
  public StatContribution(StatContributionSource source, FixedDecimalsInteger value)
  {
    _source=source;
    _value=value;
  }

  /**
   * Get the source.
   * @return the source.
   */
  public StatContributionSource getSource()
  {
    return _source;
  }

  /**
   * Get the stat contribution.
   * @return the stat contribution.
   */
  public FixedDecimalsInteger getValue()
  {
    return _value;
  }

  @Override
  public String toString()
  {
    return _source+"->"+_value;
  }
}
