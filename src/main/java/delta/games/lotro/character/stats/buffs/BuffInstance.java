package delta.games.lotro.character.stats.buffs;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * Instance of a buff.
 * @author DAM
 */
public class BuffInstance
{
  private Buff _buff;
  private Integer _tier;

  /**
   * Constructor.
   * @param buff Buff.
   */
  public BuffInstance(Buff buff)
  {
    _buff=buff;
  }

  /**
   * Copy constructor.
   * @param source Source data.
   */
  public BuffInstance(BuffInstance source)
  {
    _buff=source._buff;
    _tier=source._tier;
  }

  /**
   * Get the associated buff.
   * @return the associated buff.
   */
  public Buff getBuff()
  {
    return _buff;
  }

  /**
   * Get the tier of this buff.
   * @return a buff tier.
   */
  public Integer getTier()
  {
    return _tier;
  }

  /**
   * Set the tier of this buff.
   * @param tier the tier to set.
   */
  public void setTier(Integer tier)
  {
    _tier=tier;
  }

  /**
   * Get the stats contributed by this buff on the given character.
   * @param c Targeted character.
   * @return Some stats.
   */
  public BasicStatsSet getStats(CharacterData c)
  {
    AbstractBuffImpl impl=_buff.getImpl();
    BasicStatsSet buffContrib=impl.getStats(c,this);
    return buffContrib;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_buff.getId());
    if (_tier!=null)
    {
      sb.append(" (tier ").append(_tier).append(')');
    }
    return sb.toString();
  }
}
