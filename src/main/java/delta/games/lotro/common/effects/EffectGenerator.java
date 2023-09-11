package delta.games.lotro.common.effects;

/**
 * @author dm
 */
public class EffectGenerator
{
  private Effect2 _effect;
  private Float _spellcraft;

  public EffectGenerator(Effect2 effect, Float spellcraft)
  {
    _effect=effect;
    _spellcraft=spellcraft;
  }

  /**
   * @return the effect
   */
  public Effect2 getEffect()
  {
    return _effect;
  }

  /**
   * @return the spellcraft
   */
  public Float getSpellcraft()
  {
    return _spellcraft;
  }
}
