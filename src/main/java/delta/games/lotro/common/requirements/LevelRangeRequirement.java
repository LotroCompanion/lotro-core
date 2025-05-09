package delta.games.lotro.common.requirements;

/**
 * Requirement on a level range.
 * @author DAM
 */
public class LevelRangeRequirement implements Requirement
{
  // Minimum level (may be null)
  private Integer _minLevel;
  // Maximum level (may be null)
  private Integer _maxLevel;

  /**
   * Constructor.
   * @param minLevel Minimum level (may be <code>null</code>).
   * @param maxLevel Maximum level (may be <code>null</code>).
   */
  public LevelRangeRequirement(Integer minLevel, Integer maxLevel)
  {
    _minLevel=minLevel;
    _maxLevel=maxLevel;
  }

  /**
   * Get the minimum level requirement.
   * @return A level or <code>null</code> if no restriction.
   */
  public Integer getMinLevel()
  {
    return _minLevel;
  }

  /**
   * Get the maximum level requirement.
   * @return A level or <code>null</code> if no restriction.
   */
  public Integer getMaxLevel()
  {
    return _maxLevel;
  }

  /**
   * Indicates if this requirement is empty or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEmpty()
  {
    return ((_minLevel==null) && (_maxLevel==null));
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_minLevel!=null)
    {
      sb.append("Min level=").append(_minLevel);
    }
    if (_maxLevel!=null)
    {
      sb.append(" Max level=").append(_maxLevel);
    }
    return sb.toString().trim();
  }
}
