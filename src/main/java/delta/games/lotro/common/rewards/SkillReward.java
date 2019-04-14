package delta.games.lotro.common.rewards;

import delta.common.utils.text.EndOfLine;

/**
 * Skill reward.
 * @author DAM
 */
public class SkillReward extends RewardElement
{
  private String _name;

  /**
   * Constructor.
   * @param name Skill's name.
   */
  public SkillReward(String name)
  {
    _name=name;
  }

  /**
   * Get the skill's name.
   * @return the skill's name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Dump the contents of this trait as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Skill: ").append(_name);
    sb.append(EndOfLine.NATIVE_EOL);
    return sb.toString();
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
