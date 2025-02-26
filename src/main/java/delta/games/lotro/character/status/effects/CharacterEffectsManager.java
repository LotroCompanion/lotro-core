package delta.games.lotro.character.status.effects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.IdentifiableComparator;

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
   * Copy constructor.
   * @param source Source to copy.
   */
  public CharacterEffectsManager(CharacterEffectsManager source)
  {
    _effects=new ArrayList<EffectInstance>();
    for(EffectInstance effect : source._effects)
    {
      _effects.add(new EffectInstance(effect));
    }
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
    List<EffectInstance> ret=new ArrayList<EffectInstance>(_effects);
    Collections.sort(ret,new IdentifiableComparator<EffectInstance>());
    return ret;
  }

  /**
   * Set the managed effects.
   * @param effects Effect to set.
   */
  public void setEffects(List<EffectInstance> effects)
  {
    _effects.clear();
    _effects.addAll(effects);
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

  /**
   * Remove all effects.
   */
  public void clear()
  {
    _effects.clear();
  }

  @Override
  public String toString()
  {
    return _effects.toString();
  }
}
