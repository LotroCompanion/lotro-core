package delta.games.lotro.lore.quests.objectives.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.lore.agents.EntityClassification;
import delta.games.lotro.lore.agents.io.xml.AgentsXMLIO;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.lore.agents.npcs.NpcDescription;
import delta.games.lotro.lore.emotes.EmoteDescription;
import delta.games.lotro.lore.geo.LandmarkDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.dialogs.DialogElement;
import delta.games.lotro.lore.quests.geo.io.xml.AchievableGeoDataXMLWriter;
import delta.games.lotro.lore.quests.objectives.ConditionTarget;
import delta.games.lotro.lore.quests.objectives.ConditionType;
import delta.games.lotro.lore.quests.objectives.DefaultObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.DetectingCondition;
import delta.games.lotro.lore.quests.objectives.DetectionCondition;
import delta.games.lotro.lore.quests.objectives.EmoteCondition;
import delta.games.lotro.lore.quests.objectives.EnterDetectionCondition;
import delta.games.lotro.lore.quests.objectives.ExternalInventoryItemCondition;
import delta.games.lotro.lore.quests.objectives.FactionLevelCondition;
import delta.games.lotro.lore.quests.objectives.HobbyCondition;
import delta.games.lotro.lore.quests.objectives.InventoryItemCondition;
import delta.games.lotro.lore.quests.objectives.ItemCondition;
import delta.games.lotro.lore.quests.objectives.ItemTalkCondition;
import delta.games.lotro.lore.quests.objectives.ItemUsedCondition;
import delta.games.lotro.lore.quests.objectives.LandmarkDetectionCondition;
import delta.games.lotro.lore.quests.objectives.LevelCondition;
import delta.games.lotro.lore.quests.objectives.MonsterDiedCondition;
import delta.games.lotro.lore.quests.objectives.MonsterDiedCondition.MobSelection;
import delta.games.lotro.lore.quests.objectives.NpcCondition;
import delta.games.lotro.lore.quests.objectives.NpcTalkCondition;
import delta.games.lotro.lore.quests.objectives.NpcUsedCondition;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;
import delta.games.lotro.lore.quests.objectives.QuestBestowedCondition;
import delta.games.lotro.lore.quests.objectives.QuestCompleteCondition;
import delta.games.lotro.lore.quests.objectives.SkillUsedCondition;
import delta.games.lotro.lore.quests.objectives.TimeExpiredCondition;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.io.xml.SharedXMLUtils;

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
    String text=objective.getDescription();
    if (text.length()>0)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.OBJECTIVE_TEXT_ATTR,XmlWriter.CDATA,text);
    }
    // Lore override
    String loreOverride=objective.getLoreOverride();
    if (loreOverride.length()>0)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.OBJECTIVE_LORE_OVERRIDE_ATTR,XmlWriter.CDATA,loreOverride);
    }
    // Progress override
    String progressOverride=objective.getProgressOverride();
    if (progressOverride.length()>0)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.OBJECTIVE_PROGRESS_OVERRIDE_ATTR,XmlWriter.CDATA,progressOverride);
    }
    // Billboard override
    String billboardOverride=objective.getBillboardOverride();
    if (billboardOverride.length()>0)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.OBJECTIVE_BILLBOARD_OVERRIDE_ATTR,XmlWriter.CDATA,billboardOverride);
    }
    hd.startElement("","",ObjectivesXMLConstants.OBJECTIVE_TAG,attrs);
    // Dialogs
    List<DialogElement> dialogs=objective.getDialogs();
    for(DialogElement dialog : dialogs)
    {
      DialogsXMLWriter.writeDialogElement(hd,ObjectivesXMLConstants.DIALOG_TAG,dialog);
    }
    // Conditions
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
    else if (condition instanceof ItemTalkCondition)
    {
      writeItemTalkCondition(hd,(ItemTalkCondition)condition);
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
    else if (condition instanceof NpcUsedCondition)
    {
      writeNpcUsedCondition(hd,(NpcUsedCondition)condition);
    }
    else if (condition instanceof LevelCondition)
    {
      writeLevelCondition(hd,(LevelCondition)condition);
    }
    else if (condition instanceof QuestBestowedCondition)
    {
      writeQuestBestowedCondition(hd,(QuestBestowedCondition)condition);
    }
    else if (condition instanceof DetectingCondition)
    {
      writeDetectingCondition(hd,(DetectingCondition)condition);
    }
    else if (condition instanceof EnterDetectionCondition)
    {
      writeEnterDetectionCondition(hd,(EnterDetectionCondition)condition);
    }
    else if (condition instanceof EmoteCondition)
    {
      writeEmoteCondition(hd,(EmoteCondition)condition);
    }
    else if (condition instanceof HobbyCondition)
    {
      writeHobbyCondition(hd,(HobbyCondition)condition);
    }
    else if (condition instanceof TimeExpiredCondition)
    {
      writeTimeExpiredCondition(hd,(TimeExpiredCondition)condition);
    }
    else
    {
      writeDefaultCondition(hd,(DefaultObjectiveCondition)condition);
    }
  }
  private static void writeSharedConditionAttributes(TransformerHandler hd, AttributesImpl attrs, ObjectiveCondition condition)
  {
    writeSharedConditionAttributes(hd,attrs,condition,true);
  }

  private static void writeSharedConditionAttributes(TransformerHandler hd, AttributesImpl attrs, ObjectiveCondition condition, boolean withCount)
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
    // Show progress text
    boolean showProgressText=condition.isShowProgressText();
    if (!showProgressText)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_SHOW_PROGRESS_TEXT,XmlWriter.CDATA,"false");
    }
    // Progress override
    String progressOverride=condition.getProgressOverride();
    if (progressOverride!=null)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_PROGRESS_OVERRIDE_ATTR,XmlWriter.CDATA,progressOverride);
    }
    // Show billboard text
    boolean showBillboardText=condition.isShowBillboardText();
    if (!showBillboardText)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_SHOW_BILLBOARD_TEXT,XmlWriter.CDATA,"false");
    }
    // Count
    if (withCount)
    {
      int count=condition.getCount();
      if (count>1)
      {
        attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
      }
    }
  }

  private static void writeQuestCompleteCondition(TransformerHandler hd, QuestCompleteCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition,false);
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
    int count=condition.getCount();
    if (count>1)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
    }
    hd.startElement("","",ObjectivesXMLConstants.QUEST_COMPLETE_TAG,attrs);
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
    hd.endElement("","",ObjectivesXMLConstants.QUEST_COMPLETE_TAG);
  }

  private static void writeMonsterDiedCondition(TransformerHandler hd, MonsterDiedCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition,false);
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
      attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
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
      EntityClassification what=selection.getWhat();
      if (what!=null)
      {
        AgentsXMLIO.writeEntityClassification(selectionAttrs,what);
      }
      hd.startElement("","",ObjectivesXMLConstants.MONSTER_SELECTION_TAG,selectionAttrs);
      hd.endElement("","",ObjectivesXMLConstants.MONSTER_SELECTION_TAG);
    }
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
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
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
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

  private static void writeItemTalkCondition(TransformerHandler hd, ItemTalkCondition condition) throws Exception
  {
    writeItemCondition(hd,condition,ObjectivesXMLConstants.ITEM_TALK_TAG);
  }

  private static void writeItemCondition(TransformerHandler hd, ItemCondition condition, String tagName) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition,false);
    // Item proxy
    Proxy<Item> proxy=condition.getProxy();
    writeItemProxy(hd,attrs,proxy);
    // Count
    int count=condition.getCount();
    if (count>1)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
    }
    hd.startElement("","",tagName,attrs);
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
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
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
    hd.endElement("","",ObjectivesXMLConstants.FACTION_LEVEL_TAG);
  }

  private static void writeSkillUsedCondition(TransformerHandler hd, SkillUsedCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition,false);
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
      attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
    }
    // Max per day
    Integer maxPerDay=condition.getMaxPerDay();
    if (maxPerDay!=null)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.SKILL_USED_MAX_PER_DAY_ATTR,XmlWriter.CDATA,maxPerDay.toString());
    }
    hd.startElement("","",ObjectivesXMLConstants.SKILL_USED_TAG,attrs);
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
    hd.endElement("","",ObjectivesXMLConstants.SKILL_USED_TAG);
  }

  private static void writeNpcTalkCondition(TransformerHandler hd, NpcTalkCondition condition) throws Exception
  {
    writeNpcCondition(hd,ObjectivesXMLConstants.NPC_TALK_TAG,condition);
  }

  private static void writeNpcUsedCondition(TransformerHandler hd, NpcUsedCondition condition) throws Exception
  {
    writeNpcCondition(hd,ObjectivesXMLConstants.NPC_USED_TAG,condition);
  }

  private static void writeNpcCondition(TransformerHandler hd, String tagName, NpcCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition);
    // Write NPC proxy
    Proxy<NpcDescription> proxy=condition.getProxy();
    SharedXMLUtils.writeNpcProxy(proxy,attrs);
    hd.startElement("","",tagName,attrs);
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
    hd.endElement("","",tagName);
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
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
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
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
    hd.endElement("","",ObjectivesXMLConstants.QUEST_BESTOWED_TAG);
  }

  private static void writeDetectingCondition(TransformerHandler hd, DetectingCondition condition) throws Exception
  {
    writeDetectCondition(hd,ObjectivesXMLConstants.DETECTING_TAG,condition);
  }

  private static void writeEnterDetectionCondition(TransformerHandler hd, EnterDetectionCondition condition) throws Exception
  {
    writeDetectCondition(hd,ObjectivesXMLConstants.ENTER_DETECTION_TAG,condition);
  }

  private static void writeDetectCondition(TransformerHandler hd, String tagName, DetectionCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition);
    // Target
    writeTarget(hd,attrs,condition.getTarget());
    hd.startElement("","",tagName,attrs);
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
    hd.endElement("","",tagName);
  }

  private static void writeEmoteCondition(TransformerHandler hd, EmoteCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition,false);
    // Emote
    Proxy<EmoteDescription> proxy=condition.getProxy();
    if (proxy!=null)
    {
      int id=proxy.getId();
      attrs.addAttribute("","",ObjectivesXMLConstants.EMOTE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      String command=proxy.getName();
      attrs.addAttribute("","",ObjectivesXMLConstants.EMOTE_COMMAND_ATTR,XmlWriter.CDATA,command);
    }
    // Count
    int count=condition.getCount();
    if (count>1)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
    }
    // Max daily
    Integer maxDaily=condition.getMaxDaily();
    if (maxDaily!=null)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.EMOTE_MAX_DAILY_ATTR,XmlWriter.CDATA,maxDaily.toString());
    }
    // Target
    writeTarget(hd,attrs,condition.getTarget());
    hd.startElement("","",ObjectivesXMLConstants.EMOTE_TAG,attrs);
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
    hd.endElement("","",ObjectivesXMLConstants.EMOTE_TAG);
  }

  private static void writeHobbyCondition(TransformerHandler hd, HobbyCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition,false);
    // Item proxy
    Proxy<Item> proxy=condition.getProxy();
    writeItemProxy(hd,attrs,proxy);
    // Count
    int count=condition.getCount();
    if (count>1)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.CONDITION_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
    }
    hd.startElement("","",ObjectivesXMLConstants.HOBBY_TAG,attrs);
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
    hd.endElement("","",ObjectivesXMLConstants.HOBBY_TAG);
  }

  private static void writeTimeExpiredCondition(TransformerHandler hd, TimeExpiredCondition condition) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Shared attributes
    writeSharedConditionAttributes(hd,attrs,condition);
    // Duration
    int duration=condition.getDuration();
    if (duration>0)
    {
      attrs.addAttribute("","",ObjectivesXMLConstants.TIME_EXPIRED_DURATION_ATTR,XmlWriter.CDATA,String.valueOf(duration));
    }
    hd.startElement("","",ObjectivesXMLConstants.TIME_EXPIRED_TAG,attrs);
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
    hd.endElement("","",ObjectivesXMLConstants.TIME_EXPIRED_TAG);
  }

  private static void writeTarget(TransformerHandler hd, AttributesImpl attrs, ConditionTarget target)
  {
    if (target!=null)
    {
      // NPC proxy
      Proxy<NpcDescription> npcProxy=target.getNpcProxy();
      SharedXMLUtils.writeNpcProxy(npcProxy,attrs);
      // Mob proxy
      Proxy<MobDescription> mobProxy=target.getMobProxy();
      if (mobProxy!=null)
      {
        // ID
        int id=mobProxy.getId();
        attrs.addAttribute("","",ObjectivesXMLConstants.MOB_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
        // Name
        String name=mobProxy.getName();
        if (name!=null)
        {
          attrs.addAttribute("","",ObjectivesXMLConstants.MOB_NAME_ATTR,XmlWriter.CDATA,name);
        }
      }
    }
  }

  private static void writeItemProxy(TransformerHandler hd, AttributesImpl attrs, Proxy<Item> proxy)
  {
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
    AchievableGeoDataXMLWriter.writeObjectiveConditionGeoData(hd,condition);
    hd.endElement("","",ObjectivesXMLConstants.CONDITION_TAG);
  }
}
