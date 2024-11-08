package delta.games.lotro.character.skills.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.skills.SkillEffectGenerator;

/**
 * Storage for effects associated to properties.
 * @author DAM
 */
public class EffectProperties
{
  private Map<Integer,List<SkillEffectGenerator>> _data;

  /**
   * Constructor.
   */
  public EffectProperties()
  {
    _data=new HashMap<Integer,List<SkillEffectGenerator>>();
  }

  /**
   * Add an effect to the given property.
   * @param propertyID Property identifier.
   * @param generator An effect generator.
   */
  public void addEffectToProperty(int propertyID, SkillEffectGenerator generator)
  {
    Integer key=Integer.valueOf(propertyID);
    List<SkillEffectGenerator> list=_data.get(key);
    if (list==null)
    {
      list=new ArrayList<SkillEffectGenerator>();
      _data.put(key,list);
    }
    list.add(generator);
  }

  /**
   * Get the effects for the given property.
   * @param propertyID Property identifier.
   * @return A list of effects or <code>null</code>.
   */
  public List<SkillEffectGenerator> getEffectsForProperty(int propertyID)
  {
    List<SkillEffectGenerator> ret=_data.get(Integer.valueOf(propertyID));
    return ret;
  }
}
