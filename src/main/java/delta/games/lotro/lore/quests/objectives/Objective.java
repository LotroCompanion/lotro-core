package delta.games.lotro.lore.quests.objectives;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;

/**
 * Quest/deed objective.
 * @author DAM
 */
public class Objective
{
  private int _index;
  private String _text;
  private List<ObjectiveCondition> _conditions;

  /**
   * Constructor.
   */
  public Objective()
  {
    _index=1;
    _text="";
    _conditions=new ArrayList<ObjectiveCondition>();
  }

  /**
   * Get the objective index, starting at 1.
   * @return An index.
   */
  public int getIndex()
  {
    return _index;
  }

  /**
   * Set the objective index.
   * @param index Index to set.
   */
  public void setIndex(int index)
  {
    _index=index;
  }

  /**
   * Get the displayable text for this objective.
   * @return A displayable text (can be multiline).
   */
  public String getText()
  {
    return _text;
  }

  /**
   * Set the text for this objective.
   * @param text Text to set.
   */
  public void setText(String text)
  {
    _text=text;
  }

  /**
   * Add an objective condition.
   * @param condition Condition to add.
   */
  public void addCondition(ObjectiveCondition condition)
  {
    _conditions.add(condition);
  }

  /**
   * Get all the conditions of the objective.
   * @return A list of conditions.
   */
  public List<ObjectiveCondition> getConditions()
  {
    return _conditions;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Objective #").append(_index);
    sb.append(": ").append(_text).append(EndOfLine.NATIVE_EOL);
    for(ObjectiveCondition condition : _conditions)
    {
      sb.append('\t').append(condition).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString();
  }
}
