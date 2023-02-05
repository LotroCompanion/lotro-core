package delta.games.lotro.lore.instances.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;

/**
 * Filter on private encounter 'scalable' attribute.
 * @author DAM
 */
public class PrivateEncounterScalableFilter implements Filter<SkirmishPrivateEncounter>
{
  private Boolean _scalable;

  /**
   * Constructor.
   */
  public PrivateEncounterScalableFilter()
  {
    this(null);
  }

  /**
   * Constructor.
   * @param scalable Scalable value.
   */
  public PrivateEncounterScalableFilter(Boolean scalable)
  {
    _scalable=scalable;
  }

  /**
   * Get the managed Boolean value.
   * @return A Boolean value.
   */
  public Boolean getScalable()
  {
    return _scalable;
  }

  /**
   * Set the Boolean value.
   * @param scalable Value to set.
   */
  public void setScalable(Boolean scalable)
  {
    _scalable=scalable;
  }

  @Override
  public boolean accept(SkirmishPrivateEncounter instance)
  {
    if (_scalable==null)
    {
      return true;
    }
    boolean scalable=instance.isScalable();
    return scalable==_scalable.booleanValue();
  }
}
