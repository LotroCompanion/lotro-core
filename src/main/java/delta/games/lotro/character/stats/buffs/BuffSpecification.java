package delta.games.lotro.character.stats.buffs;

/**
 * Buff specification.
 * @author DAM
 */
public class BuffSpecification
{
  private String _buffId;
  private Integer _tier;

  /**
   * Constructor.
   * @param buffId Buff identifier.
   * @param tier Tier (optional).
   */
  public BuffSpecification(String buffId, Integer tier)
  {
    _buffId=buffId;
    _tier=tier;
  }

  /**
   * Get the buff identifier.
   * @return the buff identifier.
   */
  public String getBuffId()
  {
    return _buffId;
  }

  /**
   * Get the tier of this buff.
   * @return a buff tier.
   */
  public Integer getTier()
  {
    return _tier;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_buffId);
    if (_tier!=null)
    {
      sb.append(" (tier ").append(_tier).append(')');
    }
    return sb.toString();
  }
}
