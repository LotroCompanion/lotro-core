package delta.games.lotro.character.skills;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.properties.ModPropertyList;

/**
 * Stores the effects for a single type of a single skill.
 * @author DAM
 */
public class SingleTypeSkillEffectsManager
{
  private SkillEffectType _type;
  private List<SkillEffectGenerator> _effects;
  private ModPropertyList _additiveMods;
  private Integer _overridePropertyID; 

  /**
   * Constructor.
   * @param type Type.
   */
  public SingleTypeSkillEffectsManager(SkillEffectType type)
  {
    _type=type;
    _effects=new ArrayList<SkillEffectGenerator>();
  }

  /**
   * Get the effect type.
   * @return an effect type.
   */
  public SkillEffectType getType()
  {
    return _type;
  }

  /**
   * Add an effect generator.
   * @param generator Effect generator to add.
   */
  public void addEffect(SkillEffectGenerator generator)
  {
    _effects.add(generator);
  }

  /**
   * Indicates if this manager has effects.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasEffects()
  {
    return (!_effects.isEmpty());
  }

  /**
   * Get the managed effects.
   * @return A array of effect generators, possibly empty but never <code>null</code>.
   */
  public List<SkillEffectGenerator> getEffects()
  {
    return _effects;
  }

  /**
   * Get the additive modifiers (properties that contains effect generators to add).
   * @return Some modifiers or <code>null</code>.
   */
  public ModPropertyList getAdditiveModifiers()
  {
    return _additiveMods;
  }

  /**
   * Set the additive modifiers.
   * @param additiveMods Modifiers to set.
   */
  public void setAdditiveModifiers(ModPropertyList additiveMods)
  {
    _additiveMods=additiveMods;
  }

  /**
   * Get the ID of the property that may override this list of effects.
   * @return A property ID or <code>null</code>.
   */
  public Integer getOverridePropertyID()
  {
    return _overridePropertyID;
  }

  /**
   * Set the ID of the property that may override this list of effects.
   * @param overridePropertyID A property ID or <code>null</code>.
   */
  public void setOverridePropertyID(Integer overridePropertyID)
  {
    _overridePropertyID=overridePropertyID;
  }
}
