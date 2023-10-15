package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.effects.EffectGenerator;

/**
 * Stores the effects for an item.
 * @author DAM
 */
public class ItemEffectsManager
{
  /**
   * Use cases.
   * @author DAM
   */
  public static enum Type
  {
    /**
     * On use.
     */
    ON_USE,
    /**
     * On equip.
     */
    ON_EQUIP
  }

  private List<EffectGenerator> _onUse;
  private List<EffectGenerator> _onEquip;

  /**
   * Constructor.
   */
  public ItemEffectsManager()
  {
    // Nothing!
  }

  /**
   * Add an effect generator.
   * @param type Use case.
   * @param generator Effect generator to add.
   */
  public void addEffect(Type type, EffectGenerator generator)
  {
    if (type==Type.ON_USE)
    {
      if (_onUse==null)
      {
        _onUse=new ArrayList<EffectGenerator>();
      }
      _onUse.add(generator);
    }
    else if (type==Type.ON_EQUIP)
    {
      if (_onEquip==null)
      {
        _onEquip=new ArrayList<EffectGenerator>();
      }
      _onEquip.add(generator);
    }
  }

  /**
   * Indicates if this manager has effects.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasEffects()
  {
    return ((_onUse!=null) || (_onEquip!=null));
  }

  /**
   * Indicates if this manager has 'on use' effects.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasOnUseEffects()
  {
    return (_onUse!=null);
  }

  /**
   * Indicates if this manager has 'on equip' effects.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasOnEquipEffects()
  {
    return (_onEquip!=null);
  }

  /**
   * Get the effects of a given type.
   * @param type Type of effects.
   * @return A array of effect generators, possibly empty but never <code>null</code>.
   */
  public EffectGenerator[] getEffects(Type type)
  {
    List<EffectGenerator> list=(type==Type.ON_EQUIP)?_onEquip:_onUse;
    if (list==null)
    {
      return new EffectGenerator[0];
    }
    EffectGenerator[] ret=new EffectGenerator[list.size()];
    return list.toArray(ret);
  }
}
