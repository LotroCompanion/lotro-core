package delta.games.lotro.lore.quests.objectives.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;
import delta.games.lotro.lore.quests.objectives.QuestCompleteCondition;
import delta.games.lotro.utils.Proxy;

/**
 * Parser for quests/deeds objectives stored in XML.
 * @author DAM
 */
public class ObjectivesXMLParser
{
  /**
   * Load objectives from XML.
   * @param root Objectives description tag.
   * @param objectives Storage for loaded data. 
   */
  public static void loadObjectives(Element root, ObjectivesManager objectives)
  {
    Element objectivesTag=DOMParsingTools.getChildTagByName(root,ObjectivesXMLConstants.OBJECTIVES_TAG);
    if (objectivesTag!=null)
    {
      List<Element> objectiveTags=DOMParsingTools.getChildTagsByName(objectivesTag,ObjectivesXMLConstants.OBJECTIVE_TAG);
      for(Element objectiveTag : objectiveTags)
      {
        parseObjectiveTag(objectives, objectiveTag);
      }
    }
  }

  private static void parseObjectiveTag(ObjectivesManager objectives, Element objectiveTag)
  {
    Objective objective=new Objective();
    NamedNodeMap attrs=objectiveTag.getAttributes();
    // Index
    int objectiveIndex=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.OBJECTIVE_INDEX_ATTR,1);
    objective.setIndex(objectiveIndex);
    // Text
    String text=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.OBJECTIVE_TEXT_ATTR,"");
    objective.setText(text);

    List<Element> conditionTags=DOMParsingTools.getChildTags(objectiveTag);
    for(Element conditionTag : conditionTags)
    {
      ObjectiveCondition condition=parseConditionTag(conditionTag);
      objective.addCondition(condition);
    }
    objectives.addObjective(objective);
  }

  private static ObjectiveCondition parseConditionTag(Element conditionTag)
  {
    ObjectiveCondition ret=null;
    String tagName=conditionTag.getTagName();
    NamedNodeMap attrs=conditionTag.getAttributes();
    int index=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.CONDITION_INDEX_ATTR,0);
    if (ObjectivesXMLConstants.QUEST_COMPLETE_TAG.equals(tagName))
    {
      ret=parseQuestCompleteCondition(attrs,conditionTag);
    }
    if (ret!=null)
    {
      ret.setIndex(index);
    }
    return ret;
  }

  private static QuestCompleteCondition parseQuestCompleteCondition(NamedNodeMap attrs, Element conditionTag)
  {
    QuestCompleteCondition condition=new QuestCompleteCondition();
    // Quest
    int questId=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.QUEST_COMPLETE_QUEST_ID_ATTR,0);
    if (questId>0)
    {
      Proxy<QuestDescription> questProxy=new Proxy<QuestDescription>();
      questProxy.setId(questId);
      condition.setQuest(questProxy);
    }
    // Deed
    int deedId=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.QUEST_COMPLETE_DEED_ID_ATTR,0);
    if (deedId>0)
    {
      Proxy<DeedDescription> deedProxy=new Proxy<DeedDescription>();
      deedProxy.setId(deedId);
      condition.setDeed(deedProxy);
    }
    // Quest category
    String questCategory=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.QUEST_COMPLETE_QUEST_CATEGORY_ATTR,null);
    condition.setQuestCategory(questCategory);
    // Count
    int count=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.QUEST_COMPLETE_COUNT_ATTR,1);
    condition.setCompletionCount(count);
    return condition;
  }
}
