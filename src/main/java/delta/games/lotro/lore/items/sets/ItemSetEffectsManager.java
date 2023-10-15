package delta.games.lotro.lore.items.sets;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.effects.EffectGenerator;

/**
 * Stores the effects for item sets.
 * @author DAM
 */
public class ItemSetEffectsManager
{
  private List<EffectGenerator> _onEquip;

  /**
   * Constructor.
   */
  public ItemSetEffectsManager()
  {
    // Nothing!
  }

  /**
   * Add an effect generator.
   * @param generator Effect generator to add.
   */
  public void addEffect(EffectGenerator generator)
  {
    if (_onEquip==null)
    {
      _onEquip=new ArrayList<EffectGenerator>();
    }
    _onEquip.add(generator);
  }

  /**
   * Indicates if this manager has effects.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasEffects()
  {
    return (_onEquip!=null);
  }

  /**
   * Get the managed effects.
   * @return A array of effect generators, possibly empty but never <code>null</code>.
   */
  public EffectGenerator[] getEffects()
  {
    if (_onEquip==null)
    {
      return new EffectGenerator[0];
    }
    EffectGenerator[] ret=new EffectGenerator[_onEquip.size()];
    return _onEquip.toArray(ret);
  }
}
