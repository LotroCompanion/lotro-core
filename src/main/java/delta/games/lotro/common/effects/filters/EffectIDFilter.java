package delta.games.lotro.common.effects.filters;

import java.util.HashSet;
import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.lore.utils.EffectUtils;

/**
 * Filter on effects that uses a selection of identifiers.
 * @author DAM
 */
public class EffectIDFilter implements Filter<Effect>
{
  private Set<Integer> _selectedEffects;
  private boolean _isSet;

  /**
   * Constructor.
   */
  public EffectIDFilter()
  {
    _selectedEffects=new HashSet<Integer>();
    _isSet=false;
  }

  /**
   * Set the mob to use.
   * @param mob Mob description.
   */
  public void setMob(MobDescription mob)
  {
    _selectedEffects.clear();
    if (mob!=null)
    {
      _selectedEffects.addAll(EffectUtils.getEffectsFromMob(mob));
      _isSet=true;
    }
    else
    {
      _isSet=false;
    }
  }

  /**
   * Set the skill to use.
   * @param skill A skill.
   */
  public void setSkill(SkillDescription skill)
  {
    _selectedEffects.clear();
    if (skill!=null)
    {
      _selectedEffects.addAll(EffectUtils.getEffectsFromSkill(skill));
      _isSet=true;
    }
    else
    {
      _isSet=false;
    }
  }

  /**
   * Clear filter.
   */
  public void clear()
  {
    _selectedEffects.clear();
    _isSet=false;
  }

  @Override
  public boolean accept(Effect item)
  {
    if (_isSet)
    {
      Integer key=Integer.valueOf(item.getIdentifier());
      return _selectedEffects.contains(key);
    }
    return true;
  }
}
