package delta.games.lotro.lore.quests.objectives.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.lore.geo.LandmarkDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.objectives.ConditionType;
import delta.games.lotro.lore.quests.objectives.DefaultObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.InventoryItemCondition;
import delta.games.lotro.lore.quests.objectives.LandmarkDetectionCondition;
import delta.games.lotro.lore.quests.objectives.MonsterDiedCondition;
import delta.games.lotro.lore.quests.objectives.MonsterDiedCondition.MobSelection;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;
import delta.games.lotro.lore.quests.objectives.QuestCompleteCondition;
import delta.games.lotro.utils.Proxy;

/**
 * Writes quests/deeds objectives to XML documents.
 * @author DAM
 */
public class ObjectivesXMLWriter
{
  /**
   * Write an objectives manager to an XML document.
   * @param hd Output transformer.
   * @param objectives Rewards to write.
   * @throws Exception If an error occurs.
   */
  public static void write(TransformerHandler hd, ObjectivesManager objectives) throws Exception
  {
    hd.startElement("","",ObjectivesXMLConstants.OBJECTIVES_TAG,new AttributesImpl());
    for(Objective objective : objectives.getObjectives())
    {
      writeObjective(hd,objective);
    }
    hd.endElement("","",ObjectivesXMLConstants.OBJECTIVES_TAG);
  }

  private static void writeObjective(TransformerHandler hd, Objective objective) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Index
    int index=objective.getIndex();
    attrs.addAttribute("","",ObjectivesXMLConstants.OBJECTIVE_INDEX_ATTR,XmlWriter.CDATA,String.valueOf(index));
    // Text
    String text=objective.getText();
    if (text.length()>0)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.OBJECTIVE_TEXT_ATTR,XmlWriter.CDATA,text);
    }
    hd.startElement("","",ObjectivesXMLConstants.OBJECTIVE_TAG,attrs);
    List<ObjectiveCondition> conditions=objective.getConditions();
    for(ObjectiveCondition condition : conditions)
    {
      writeCondition(hd,condition);
    }
    hd.endElement("","",ObjectivesXMLConstants.OBJECTIVE_TAG);
  }

  private static void writeCondition(TransformerHandler hd, ObjectiveCondition condition) throws Exception
  {
    if (condition instanceof QuestCompleteCondition)
    {
      writeQuestCompleteCondition(hd,(QuestCompleteCondition)condition);
    }
    else if (condition instanceof MonsterDiedCondition)
    {
      writeMonsterDiedCondition(hd,(MonsterDiedCondition)condition);
    }
    else if (condition instanceof LandmarkDetectionCondition)
    {
      writeLandmarkDetectionCondition(hd,(LandmarkDetectionCondition)condition);
    }
    else if (condition instanceof InventoryItemCondition)
    {
      writeInventoryItemCondition(hd,(InventoryItemCondition)condition);
    }
    else
    {
      writeDefaultCondition(hd,(DefaultObjectiveCondition)condition);
    }
  }

  private static void writeSharedConditionAttributes(TransformerHandler hd, AttributesImpl attrs, ObjectiveCondition condition)
  {
    // Index
    int index=condition.getIndex();
    attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_INDEX_ATTR,XmlWriter.CDATA,String.valueOf(index));
    // Lore Info
    String loreInfo=condition.getLoreInfo();
    if (loreInfo!=null)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_LORE_INFO_ATTR,XmlWriter.CDATA,loreInfo);
    }
    // Progress override
    String progressOverride=condition.getProgressOverride();
    if (progressOverride!=null)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_PROGRESS_OVERRIDE_ATTR,XmlWriter.CDATA,progressOverride);
    }
  }

  private static void writeQuestCompleteCondition(TransformerHandler hd, QuestCompleteCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition);
    // Achievable?
    Proxy<? extends Achievable> proxy=condition.getProxy();
    if (proxy!=null)
    {
      int id=proxy.getId();
      attrs.addAttribute("","",ObjectivesXMLConstants.QUEST_COMPLETE_ACHIEVABLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Quest category?
    String questCategory=condition.getQuestCategory();
    if (questCategory!=null)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.QUEST_COMPLETE_QUEST_CATEGORY_ATTR,XmlWriter.CDATA,questCategory);
    }
    // Count
    int count=condition.getCompletionCount();
    if (count>1)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.QUEST_COMPLETE_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
    }
    hd.startElement("","",ObjectivesXMLConstants.QUEST_COMPLETE_TAG,attrs);
    hd.endElement("","",ObjectivesXMLConstants.QUEST_COMPLETE_TAG);
  }

  private static void writeMonsterDiedCondition(TransformerHandler hd, MonsterDiedCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition);
    // Mob ID
    Integer mobId=condition.getMobId();
    if (mobId!=null)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.MONSTER_DIE_MOB_ID_ATTR,XmlWriter.CDATA,mobId.toString());
    }
    // Mob Name
    String mobName=condition.getMobName();
    if (mobName!=null)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.MONSTER_DIE_MOB_NAME_ATTR,XmlWriter.CDATA,mobName.toString());
    }
    // Count
    int count=condition.getCount();
    if (count>1)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.MONSTER_DIE_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
    }
    hd.startElement("","",ObjectivesXMLConstants.MONSTER_DIED_TAG,attrs);
    // Selections
    for(MobSelection selection : condition.getMobSelections())
    {
      AttributesImpl selectionAttrs=new AttributesImpl();
      // Where
      String where=selection.getWhere();
      if (where!=null)
      {
        selectionAttrs.addAttribute("","",ObjectivesXMLConstants.MONSTER_SELECTION_WHERE_ATTR,XmlWriter.CDATA,where);
      }
      // What
      String what=selection.getWhat();
      if (what!=null)
      {
        selectionAttrs.addAttribute("","",ObjectivesXMLConstants.MONSTER_SELECTION_WHAT_ATTR,XmlWriter.CDATA,what);
      }
      hd.startElement("","",ObjectivesXMLConstants.MONSTER_SELECTION_TAG,selectionAttrs);
      hd.endElement("","",ObjectivesXMLConstants.MONSTER_SELECTION_TAG);
    }
    hd.endElement("","",ObjectivesXMLConstants.MONSTER_DIED_TAG);
  }

  private static void writeLandmarkDetectionCondition(TransformerHandler hd, LandmarkDetectionCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition);
    // Landmark proxy
    Proxy<LandmarkDescription> proxy=condition.getLandmarkProxy();
    if (proxy!=null)
    {
      // ID
      int id=proxy.getId();
      attrs.addAttribute("","",ObjectivesXMLConstants.LANDMARK_DETECTION_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Name
      String name=proxy.getName();
      if (name!=null)
      {
        attrs.addAttribute("","",ObjectivesXMLConstants.LANDMARK_DETECTION_NAME_ATTR,XmlWriter.CDATA,name);
      }
    }
    hd.startElement("","",ObjectivesXMLConstants.LANDMARK_DETECTION_TAG,attrs);
    hd.endElement("","",ObjectivesXMLConstants.LANDMARK_DETECTION_TAG);
  }

  private static void writeInventoryItemCondition(TransformerHandler hd, InventoryItemCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition);
    // Item proxy
    Proxy<Item> proxy=condition.getProxy();
    if (proxy!=null)
    {
      // ID
      int id=proxy.getId();
      attrs.addAttribute("","",ObjectivesXMLConstants.INVENTORY_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Name
      String name=proxy.getName();
      if (name!=null)
      {
        attrs.addAttribute("","",ObjectivesXMLConstants.INVENTORY_ITEM_NAME_ATTR,XmlWriter.CDATA,name);
      }
    }
    // Count
    int count=condition.getCount();
    if (count>1)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.INVENTORY_ITEM_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
    }
    hd.startElement("","",ObjectivesXMLConstants.INVENTORY_ITEM_TAG,attrs);
    hd.endElement("","",ObjectivesXMLConstants.INVENTORY_ITEM_TAG);
  }

  private static void writeDefaultCondition(TransformerHandler hd, DefaultObjectiveCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Type
    ConditionType type=condition.getType();
    if (type!=null)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_TYPE_ATTR,XmlWriter.CDATA,type.name());
    }
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition);
    hd.startElement("","",ObjectivesXMLConstants.CONDITION_TAG,attrs);
    hd.endElement("","",ObjectivesXMLConstants.CONDITION_TAG);
  }
}
