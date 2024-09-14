package delta.games.lotro.character.skills.attack;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;

/**
 * Stores the attacks of a skill.
 * @author DAM
 */
public class SkillAttacks
{
  private List<SkillAttack> _attacks;

  /**
   * Constructor.
   */
  public SkillAttacks()
  {
    _attacks=new ArrayList<SkillAttack>();
  }

  /**
   * Add an attack.
   * @param attack Attack to add.
   */
  public void addAttack(SkillAttack attack)
  {
    _attacks.add(attack);
  }

  /**
   * Get the managed attacks.
   * @return A list of attacks.
   */
  public List<SkillAttack> getAttacks()
  {
    return _attacks;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Attacks:").append(EndOfLine.NATIVE_EOL);
    int index=1;
    for(SkillAttack attack : _attacks)
    {
      sb.append("- Attack #").append(index).append(": ");
      sb.append(attack).append(EndOfLine.NATIVE_EOL);
      index++;
    }
    return sb.toString().trim();
  }
}
