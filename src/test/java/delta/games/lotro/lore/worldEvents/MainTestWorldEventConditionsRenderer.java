package delta.games.lotro.lore.worldEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.expressions.logical.CompoundLogicalTreeNode;
import delta.common.utils.expressions.logical.LogicalTreeNode;
import delta.common.utils.expressions.logical.SimpleLogicalTreeNode;
import delta.common.utils.misc.IntegerHolder;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;

/**
 * Simple test class for the renderer of world event conditions.
 * @author DAM
 */
public class MainTestWorldEventConditionsRenderer
{
  private Map<String,IntegerHolder> _counters=new HashMap<String,IntegerHolder>();

  private void doIt()
  {
    for(DeedDescription deed : DeedsManager.getInstance().getAll())
    {
      doAchievable(deed);
    }
    for(QuestDescription quest : QuestsManager.getInstance().getAll())
    {
      doAchievable(quest);
    }
    List<String> labels=new ArrayList<String>(_counters.keySet());
    Collections.sort(labels);
    System.out.println("Nb labels: "+labels.size());
    for(String label : labels)
    {
      System.out.println("\t"+label+" => "+_counters.get(label));
    }
  }

  private void doAchievable(Achievable achievable)
  {
    AbstractWorldEventCondition condition=achievable.getWorldEventsRequirement();
    if (condition!=null)
    {
      System.out.println("Achievable: "+achievable+" => "+condition);
      LogicalTreeNode<String> conditionsStr=WorldEventConditionsUtils.renderWorldEventCondition(condition);
      if (conditionsStr!=null)
      {
        System.out.println("\t"+conditionsStr);
        handleCondition(conditionsStr);
      }
    }
  }

  private void handleCondition(LogicalTreeNode<String> conditionsStr)
  {
    if (conditionsStr instanceof CompoundLogicalTreeNode)
    {
      CompoundLogicalTreeNode<String> compound=(CompoundLogicalTreeNode<String>)conditionsStr;
      for(LogicalTreeNode<String> childCondition : compound.getItems())
      {
        handleCondition(childCondition);
      }
    }
    else if (conditionsStr instanceof SimpleLogicalTreeNode)
    {
      SimpleLogicalTreeNode<String> simple=(SimpleLogicalTreeNode<String>)conditionsStr;
      handleConditionLabel(simple.getValue());
    }
  }

  private void handleConditionLabel(String label)
  {
    IntegerHolder counter=_counters.get(label);
    if (counter==null)
    {
      counter=new IntegerHolder();
      _counters.put(label,counter);
    }
    counter.increment();
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestWorldEventConditionsRenderer().doIt();
  }
}
