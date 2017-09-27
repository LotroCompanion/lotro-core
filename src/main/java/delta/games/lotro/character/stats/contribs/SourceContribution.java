package delta.games.lotro.character.stats.contribs;

import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Contribution of a source to a stat.
 * @author DAM
 */
public class SourceContribution
{
  private String _source;
  private FixedDecimalsInteger _value;

  /**
   * Constructor.
   * @param source Source.
   * @param value Stat contribution.
   */
  public SourceContribution(String source, FixedDecimalsInteger value)
  {
    _source=source;
    _value=value;
  }

  /**
   * Get the source.
   * @return the source.
   */
  public String getSource()
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
