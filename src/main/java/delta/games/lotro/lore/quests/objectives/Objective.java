package delta.games.lotro.lore.quests.objectives;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.quests.dialogs.DialogElement;

/**
 * Quest/deed objective.
 * @author DAM
 */
public class Objective
{
  private int _index;
  private String _text;
  private List<DialogElement> _dialogs;
  private List<ObjectiveCondition> _conditions;

  /**
   * Constructor.
   */
  public Objective()
  {
    _index=1;
    _text="";
    _dialogs=new ArrayList<DialogElement>();
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
    if (text==null) text="";
    _text=text;
  }

  /**
   * Add a dialog.
   * @param dialog Dialog to add.
   */
  public void addDialog(DialogElement dialog)
  {
    _dialogs.add(dialog);
  }

  /**
   * Get all the dialogs of the objective.
   * @return A list of dialogs.
   */
  public List<DialogElement> getDialogs()
  {
    return _dialogs;
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

  /**
   * Access to an objective condition using its index.
   * @param index Index to use.
   * @return An objective condition or <code>null</code> if not found.
   */
  public ObjectiveCondition getConditionByIndex(int index)
  {
    for(ObjectiveCondition condition : _conditions)
    {
      if (condition.getIndex()==index)
      {
        return condition;
      }
    }
    return null;
  }

  /**
   * Sort conditions.
   */
  public void sort()
  {
    Collections.sort(_conditions,new ObjectiveConditionComparator());
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Objective #").append(_index);
    sb.append(": ").append(_text).append(EndOfLine.NATIVE_EOL);
    sb.append("Dialogs:").append(EndOfLine.NATIVE_EOL);
    for(DialogElement dialog : _dialogs)
    {
      sb.append('\t').append(dialog).append(EndOfLine.NATIVE_EOL);
    }
    sb.append("Conditions:").append(EndOfLine.NATIVE_EOL);
    for(ObjectiveCondition condition : _conditions)
    {
      sb.append('\t').append(condition).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString();
  }
}
