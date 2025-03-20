package delta.games.lotro.common.effects;

/**
 * Effect generator specialized for proc'ed effects.
 * @author DAM
 */
public class ProcEffectGenerator extends EffectGenerator
{
  private boolean _onTarget;

  /**
   * Constructor.
   */
  public ProcEffectGenerator()
  {
    super();
    _onTarget=false;
  }

  /**
   * Get the 'on target' flag.
   * @return a boolean value.
   */
  public boolean isOnTarget()
  {
    return _onTarget;
  }

  /**
   * Set the 'on target' flag.
   * @param onTarget Value to set.
   */
  public void setOnTarget(boolean onTarget)
  {
    _onTarget=onTarget;
  }
}
