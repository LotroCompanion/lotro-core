package delta.games.lotro.lore.worldEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.collections.filters.Operator;
import delta.common.utils.expressions.logical.AbstractLogicalExpression;
import delta.common.utils.expressions.logical.CompoundLogicalExpression;
import delta.common.utils.expressions.logical.SimpleLogicalExpression;
import delta.games.lotro.lore.quests.Achievable;

/**
 * Utility methods related to world events conditions.
 * @author DAM
 */
public class WorldEventConditionsUtils
{
  /**
   * Get a string-based logical expression for a world event condition.
   * @param condition Input condition.
   * @return A string-based logical expression or <code>null</code>.
   */
  public static AbstractLogicalExpression<String> renderWorldEventCondition(AbstractWorldEventCondition condition)
  {
    if (condition==null)
    {
      return null;
    }
    if (condition instanceof SimpleWorldEventCondition)
    {
      SimpleWorldEventCondition simpleWECondition=(SimpleWorldEventCondition)condition;
      String label=simpleWECondition.getLabel();
      if (label!=null)
      {
        return new SimpleLogicalExpression<String>(label);
      }
      return null;
    }
    if (condition instanceof CompoundWorldEventCondition)
    {
      CompoundWorldEventCondition compoundWECondition=(CompoundWorldEventCondition)condition;
      return renderCompoundWorldEventCondition(compoundWECondition);
    }
    return null;
  }

  private static AbstractLogicalExpression<String> renderCompoundWorldEventCondition(CompoundWorldEventCondition condition)
  {
    Operator operator=condition.getOperator();
    CompoundLogicalExpression<String> ret=new CompoundLogicalExpression<String>(operator);
    for(AbstractWorldEventCondition childCondition : condition.getItems())
    {
      AbstractLogicalExpression<String> childExpression=renderWorldEventCondition(childCondition);
      if (childExpression!=null)
      {
        ret.addItem(childExpression);
      }
    }
    int nbItems=ret.getItems().size();
    if (nbItems==0)
    {
      return null;
    }
    if (nbItems==1)
    {
      return ret.getItems().get(0);
    }
    return ret;
  }

  /**
   * Get the condition labels used by the given achievables.
   * @param achievables Achievables to use.
   * @return A list of condition labels.
   */
  public static List<String> getLabels(List<? extends Achievable> achievables)
  {
    Set<String> labels=new HashSet<String>();
    for(Achievable achievable : achievables)
    {
      AbstractWorldEventCondition condition=achievable.getWorldEventsRequirement();
      handleCondition(condition,labels);
    }
    List<String> ret=new ArrayList<String>(labels);
    Collections.sort(ret);
    return ret;
  }

  private static void handleCondition(AbstractWorldEventCondition condition, Set<String> labels)
  {
    if (condition==null)
    {
      return;
    }
    if (condition instanceof CompoundWorldEventCondition)
    {
      CompoundWorldEventCondition compound=(CompoundWorldEventCondition)condition;
      for(AbstractWorldEventCondition childCondition : compound.getItems())
      {
        handleCondition(childCondition,labels);
      }
    }
    else if (condition instanceof SimpleWorldEventCondition)
    {
      SimpleWorldEventCondition simpleCondition=(SimpleWorldEventCondition)condition;
      String label=simpleCondition.getLabel();
      if (label!=null)
      {
        labels.add(label);
      }
    }
  }
}
