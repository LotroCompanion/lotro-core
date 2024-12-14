package delta.games.lotro.common.effects;

/**
 * Effect generator.
 * @author DAM
 */
public class EffectGenerator
{
  private Effect _effect;
  private Float _spellcraft;

  /**
   * Default constructor.
   */
  public EffectGenerator()
  {
    _effect=null;
    _spellcraft=null;
  }

  /**
   * Constructor.
   * @param effect Effect.
   * @param spellcraft Spellcraft value.
   */
  public EffectGenerator(Effect effect, Float spellcraft)
  {
    _effect=effect;
    _spellcraft=spellcraft;
  }

  /**
   * Get the managed effect.
   * @return the managed effect.
   */
  public Effect getEffect()
  {
    return _effect;
  }

  /**
   * Set the managed effect.
   * @param effect Effect to set.
   */
  public void setEffect(Effect effect)
  {
    _effect=effect;
  }

  /**
   * Get the spellcraft.
   * @return the spellcraft.
   */
  public Float getSpellcraft()
  {
    return _spellcraft;
  }

  /**
   * Set the spellcraft.
   * @param spellcraft Spellcraft to set.
   */
  public void setSpellcraft(Float spellcraft)
  {
    _spellcraft=spellcraft;
  }

  @Override
  public String toString()
  {
    if (_spellcraft!=null)
    {
      return _effect+", spellcraft="+_spellcraft;
    }
    return _effect.toString();
  }
}
