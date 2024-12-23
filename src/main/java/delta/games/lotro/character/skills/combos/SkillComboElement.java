package delta.games.lotro.character.skills.combos;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Skill combo element.
 * @author DAM
 */
public class SkillComboElement
{
  private int _value;
  private Proxy<SkillDescription> _skill;

  /**
   * Constructor.
   * @param value Combo property value.
   * @param skill Skill proxy.
   */
  public SkillComboElement(int value, Proxy<SkillDescription> skill)
  {
    _value=value;
    _skill=skill;
  }

  /**
   * Get the combo property value.
   * @return a value.
   */
  public int getValue()
  {
    return _value;
  }

  /**
   * Get the skill proxy.
   * @return the skill proxy.
   */
  public Proxy<SkillDescription> getSkill()
  {
    return _skill;
  }

  @Override
  public String toString()
  {
    return "Combo: "+_value+" => skill="+_skill;
  }
}
