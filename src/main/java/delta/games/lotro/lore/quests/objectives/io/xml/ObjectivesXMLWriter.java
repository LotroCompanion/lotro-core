package delta.games.lotro.lore.quests.objectives.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.lore.geo.LandmarkDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.npc.NpcDescription;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.objectives.ConditionType;
import delta.games.lotro.lore.quests.objectives.DefaultObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.ExternalInventoryItemCondition;
import delta.games.lotro.lore.quests.objectives.FactionLevelCondition;
import delta.games.lotro.lore.quests.objectives.InventoryItemCondition;
import delta.games.lotro.lore.quests.objectives.ItemCondition;
import delta.games.lotro.lore.quests.objectives.ItemUsedCondition;
import delta.games.lotro.lore.quests.objectives.LandmarkDetectionCondition;
import delta.games.lotro.lore.quests.objectives.LevelCondition;
import delta.games.lotro.lore.quests.objectives.MonsterDiedCondition;
import delta.games.lotro.lore.quests.objectives.MonsterDiedCondition.MobSelection;
import delta.games.lotro.lore.quests.objectives.NpcTalkCondition;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;
import delta.games.lotro.lore.quests.objectives.QuestBestowedCondition;
import delta.games.lotro.lore.quests.objectives.QuestCompleteCondition;
import delta.games.lotro.lore.quests.objectives.SkillUsedCondition;
import delta.games.lotro.lore.reputation.Faction;
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
    else if (condition instanceof ItemUsedCondition)
    {
      writeItemUsedCondition(hd,(ItemUsedCondition)condition);
    }
    else if (condition instanceof ExternalInventoryItemCondition)
    {
      writeExternalInventoryItemCondition(hd,(ExternalInventoryItemCondition)condition);
    }
    else if (condition instanceof FactionLevelCondition)
    {
      writeFactionLevelCondition(hd,(FactionLevelCondition)condition);
    }
    else if (condition instanceof SkillUsedCondition)
    {
      writeSkillUsedCondition(hd,(SkillUsedCondition)condition);
    }
    else if (condition instanceof NpcTalkCondition)
    {
      writeNpcTalkCondition(hd,(NpcTalkCondition)condition);
    }
    else if (condition instanceof LevelCondition)
    {
      writeLevelCondition(hd,(LevelCondition)condition);
    }
    else if (condition instanceof QuestBestowedCondition)
    {
      writeQuestBestowedCondition(hd,(QuestBestowedCondition)condition);
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
    writeItemCondition(hd,condition,ObjectivesXMLConstants.INVENTORY_ITEM_TAG);
  }

  private static void writeItemUsedCondition(TransformerHandler hd, ItemUsedCondition condition) throws Exception
  {
    writeItemCondition(hd,condition,ObjectivesXMLConstants.ITEM_USED_TAG);
  }

  private static void writeExternalInventoryItemCondition(TransformerHandler hd, ExternalInventoryItemCondition condition) throws Exception
  {
    writeItemCondition(hd,condition,ObjectivesXMLConstants.EXTERNAL_INVENTORY_TAG);
  }

  private static void writeItemCondition(TransformerHandler hd, ItemCondition condition, String tagName) throws Exception
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
      attrs.addAttribute("","",ObjectivesXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Name
      String name=proxy.getName();
      if (name!=null)
      {
        attrs.addAttribute("","",ObjectivesXMLConstants.ITEM_NAME_ATTR,XmlWriter.CDATA,name);
      }
    }
    // Count
    int count=condition.getCount();
    if (count>1)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.ITEM_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
    }
    hd.startElement("","",tagName,attrs);
    hd.endElement("","",tagName);
  }

  private static void writeFactionLevelCondition(TransformerHandler hd, FactionLevelCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition);
    // Faction proxy
    Proxy<Faction> proxy=condition.getProxy();
    if (proxy!=null)
    {
      // ID
      int id=proxy.getId();
      attrs.addAttribute("","",ObjectivesXMLConstants.FACTION_LEVEL_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Name
      String name=proxy.getName();
      if (name!=null)
      {
        attrs.addAttribute("","",ObjectivesXMLConstants.FACTION_LEVEL_NAME_ATTR,XmlWriter.CDATA,name);
      }
    }
    // Tier
    int tier=condition.getTier();
    attrs.addAttribute("","",ObjectivesXMLConstants.FACTION_LEVEL_TIER_ATTR,XmlWriter.CDATA,String.valueOf(tier));
    hd.startElement("","",ObjectivesXMLConstants.FACTION_LEVEL_TAG,attrs);
    hd.endElement("","",ObjectivesXMLConstants.FACTION_LEVEL_TAG);
  }

  private static void writeSkillUsedCondition(TransformerHandler hd, SkillUsedCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition);
    // Skill proxy
    Proxy<SkillDescription> proxy=condition.getProxy();
    if (proxy!=null)
    {
      // ID
      int id=proxy.getId();
      attrs.addAttribute("","",ObjectivesXMLConstants.SKILL_USED_SKILL_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Name
      String name=proxy.getName();
      if (name!=null)
      {
        attrs.addAttribute("","",ObjectivesXMLConstants.SKILL_USED_SKILL_NAME_ATTR,XmlWriter.CDATA,name);
      }
    }
    // Count
    int count=condition.getCount();
    if (count>1)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.SKILL_USED_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
    }
    // Max per day
    Integer maxPerDay=condition.getMaxPerDay();
    if (maxPerDay!=null)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.SKILL_USED_MAX_PER_DAY_ATTR,XmlWriter.CDATA,maxPerDay.toString());
    }
    hd.startElement("","",ObjectivesXMLConstants.SKILL_USED_TAG,attrs);
    hd.endElement("","",ObjectivesXMLConstants.SKILL_USED_TAG);
  }

  private static void writeNpcTalkCondition(TransformerHandler hd, NpcTalkCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition);
    // NPC proxy
    Proxy<NpcDescription> proxy=condition.getProxy();
    if (proxy!=null)
    {
      // ID
      int id=proxy.getId();
      attrs.addAttribute("","",ObjectivesXMLConstants.NPC_TALK_NPC_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Name
      String name=proxy.getName();
      if (name!=null)
      {
        attrs.addAttribute("","",ObjectivesXMLConstants.NPC_TALK_NPC_NAME_ATTR,XmlWriter.CDATA,name);
      }
    }
    hd.startElement("","",ObjectivesXMLConstants.NPC_TALK_TAG,attrs);
    hd.endElement("","",ObjectivesXMLConstants.NPC_TALK_TAG);
  }

  private static void writeLevelCondition(TransformerHandler hd, LevelCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition);
    // Level
    int level=condition.getLevel();
    attrs.addAttribute("","",ObjectivesXMLConstants.LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(level));
    hd.startElement("","",ObjectivesXMLConstants.LEVEL_TAG,attrs);
    hd.endElement("","",ObjectivesXMLConstants.LEVEL_TAG);
  }

  private static void writeQuestBestowedCondition(TransformerHandler hd, QuestBestowedCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition);
    // Achievable?
    Proxy<? extends Achievable> proxy=condition.getProxy();
    if (proxy!=null)
    {
      int id=proxy.getId();
      attrs.addAttribute("","",ObjectivesXMLConstants.QUEST_BESTOWED_ACHIEVABLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    hd.startElement("","",ObjectivesXMLConstants.QUEST_BESTOWED_TAG,attrs);
    hd.endElement("","",ObjectivesXMLConstants.QUEST_BESTOWED_TAG);
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
