package delta.games.lotro.common;

import delta.common.utils.text.EndOfLine;

/**
 * Skill.
 * @author DAM
 */
public class Skill
{
  private String _name;

  /**
   * Constructor.
   * @param name Skill's name.
   */
  public Skill(String name)
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
