package delta.games.lotro.common.effects;

import delta.games.lotro.common.Interactable;
import delta.games.lotro.utils.Proxy;

/**
 * Genesis effect.
 * @author DAM
 */
public class GenesisEffect extends Effect2
{
  // Effect_Genesis_ConstantSummonDuration: 20.0
  // Effect_Genesis_PermanentSummonDuration: 0
  // Effect_Genesis_SummonedObject: 1879163733

  // Duration (<0 => Permanent)
  private float _summonDuration;
  // What:
  // - Hotspot
  private Hotspot _hotspot;
  // - NPC, Mob or Item
  private Proxy<Interactable> _interactable;

  /**
   * Constructor.
   */
  public GenesisEffect()
  {
    super();
    _hotspot=null;
    _interactable=null;
  }

  /**
   * Indicates if this effect is permanent or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isPermanent()
  {
    return (_summonDuration<0);
  }

  /**
   * Set the permanent flag.
   */
  public void setPermanent()
  {
    _summonDuration=-1;
  }

  /**
   * Get the summon duration.
   * @return A duration in seconds.
   */
  public float getSummonDuration()
  {
    return (_summonDuration>0)?_summonDuration:0.0f;
  }

  /**
   * Set the summon duration.
   * @param summonDuration Duration to set (seconds).
   */
  public void setSummonDuration(float summonDuration)
  {
    _summonDuration=summonDuration;
  }

  /**
   * Get the managed hotspot, if any.
   * @return A hotspot or <code>null</code>.
   */
  public Hotspot getHotspot()
  {
    return _hotspot;
  }

  /**
   * Set the hotspot.
   * @param hotspot Hotspot to set.
   */
  public void setHotspot(Hotspot hotspot)
  {
    _hotspot=hotspot;
  }

  /**
   * Get the interactable, if any.
   * @return An interactable or <code>null</code>.
   */
  public Proxy<Interactable> getInteractable()
  {
    return _interactable;
  }

  /**
   * Set the interactable.
   * @param interactable Interactable to set.
   */
  public void setInteractable(Proxy<Interactable> interactable)
  {
    _interactable=interactable;
  }
}
