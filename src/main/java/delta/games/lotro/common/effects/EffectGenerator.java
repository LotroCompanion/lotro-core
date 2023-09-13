package delta.games.lotro.common.effects;

/**
 * Effect generator.
 * @author DAM
 */
public class EffectGenerator
{
  private Effect2 _effect;
  private Float _spellcraft;

  /**
   * Constructor.
   * @param effect Effect.
   * @param spellcraft Spellcraft value.
   */
  public EffectGenerator(Effect2 effect, Float spellcraft)
  {
    _effect=effect;
    _spellcraft=spellcraft;
  }

  /**
   * Get the managed effect.
   * @return the managed effect.
   */
  public Effect2 getEffect()
  {
    return _effect;
  }

  /**
   * Get the spellcraft.
   * @return the spellcraft.
   */
  public Float getSpellcraft()
  {
    return _spellcraft;
  }
}
