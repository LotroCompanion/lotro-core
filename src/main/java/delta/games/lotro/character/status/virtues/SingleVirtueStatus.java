package delta.games.lotro.character.status.virtues;

import delta.games.lotro.character.virtues.VirtueDescription;

/**
 * Status for a single virtue.
 * @author DAM
 */
public class SingleVirtueStatus
{
  private VirtueDescription _virtue;
  private int _xp;
  private int _tier;

  /**
   * Constructor.
   * @param virtue Associated virtue.
   */
  public SingleVirtueStatus(VirtueDescription virtue)
  {
    _virtue=virtue;
  }

  /**
   * Get the associated virtue.
   * @return a virtue.
   */
  public VirtueDescription getVirtue()
  {
    return _virtue;
  }

  /**
   * Get the acquired XP for this virtue.
   * @return an XP amount.
   */
  public int getXp()
  {
    return _xp;
  }

  /**
   * Set the acquired XP amount for this virtue.
   * @param xp XP amount to set.
   */
  public void setXp(int xp)
  {
    _xp=xp;
  }

  /**
   * Get the current tier for this virtue.
   * @return a virtue tier.
   */
  public int getTier()
  {
    return _tier;
  }

  /**
   * Set the current tier for this virtue.
   * @param tier Tier to set.
   */
  public void setTier(int tier)
  {
    _tier=tier;
  }
}
