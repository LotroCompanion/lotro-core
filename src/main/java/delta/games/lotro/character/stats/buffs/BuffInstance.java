package delta.games.lotro.character.stats.buffs;

/**
 * Instance of a buff.
 * @author DAM
 */
public class BuffInstance
{
  private Buff _buff;
  private int _tier;

  /**
   * Constructor.
   * @param buff Buff.
   */
  public BuffInstance(Buff buff)
  {
    _buff=buff;
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
  public int getTier()
  {
    return _tier;
  }

  /**
   * Set the tier of this buff.
   * @param tier the tier to set.
   */
  public void setTier(int tier)
  {
    _tier=tier;
  }
}
