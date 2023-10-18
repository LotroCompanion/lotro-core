package delta.games.lotro.common.effects;

/**
 * Reactive change.
 * <p>
 * Gathers a 'reactive vital change' and a 'reactive effect'. Both are optional,
 * but the parent change exists only if at least one of the two exist.
 * @author DAM
 */
public class ReactiveChange
{
  private ReactiveVitalChange _vitalChange;
  private EffectAndProbability _effect;

  /**
   * Constructor.
   * @param vitalChange Reactive vital change.
   * @param effect Reactive effect.
   */
  public ReactiveChange(ReactiveVitalChange vitalChange, EffectAndProbability effect)
  {
    _vitalChange=vitalChange;
    _effect=effect;
  }

  /**
   * Get the vital change.
   * @return the vital change or <code>null</code>.
   */
  public ReactiveVitalChange getVitalChange()
  {
    return _vitalChange;
  }

  /**
   * Get the managed effect.
   * @return the managed effect or <code>null</code>.
   */
  public EffectAndProbability getEffect()
  {
    return _effect;
  }
}
