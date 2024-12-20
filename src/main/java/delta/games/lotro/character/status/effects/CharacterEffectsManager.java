package delta.games.lotro.character.status.effects;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager for effects on a character.
 * @author DAM
 */
public class CharacterEffectsManager
{
  private List<EffectInstance> _effects;

  /**
   * Constructor.
   */
  public CharacterEffectsManager()
  {
    _effects=new ArrayList<EffectInstance>();
  }

  /**
   * Add an effect instance.
   * @param effect Effect instance to add.
   */
  public void addEffect(EffectInstance effect)
  {
    _effects.add(effect);
  }

  /**
   * Get the managed effect instances.
   * @return A list of effect instances.
   */
  public List<EffectInstance> getEffects()
  {
    return _effects;
  }

  /**
   * Set the contents of this object from a given source.
   * @param source Source to copy.
   */
  public void copyFrom(CharacterEffectsManager source)
  {
    _effects.clear();
    for(EffectInstance effectInstance : source._effects)
    {
      EffectInstance newEffectInstance=new EffectInstance(effectInstance);
      addEffect(newEffectInstance);
    }
  }

  @Override
  public String toString()
  {
    return _effects.toString();
  }
}
