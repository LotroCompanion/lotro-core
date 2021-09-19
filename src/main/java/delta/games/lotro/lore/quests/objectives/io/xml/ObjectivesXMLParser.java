package delta.games.lotro.lore.quests.objectives.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
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
import delta.games.lotro.lore.quests.geo.io.xml.AchievableGeoDataXMLParser;
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
import delta.games.lotro.lore.reputation.FactionsRegistry;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.io.xml.SharedXMLUtils;

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
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.OBJECTIVE_TEXT_ATTR,"");
    objective.setDescription(description);
    // Lore override
    String loreOverride=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.OBJECTIVE_LORE_OVERRIDE_ATTR,"");
    objective.setLoreOverride(loreOverride);
    // Progress override
    String progressOverride=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.OBJECTIVE_PROGRESS_OVERRIDE_ATTR,"");
    objective.setProgressOverride(progressOverride);
    // Billboard override
    String billboardOverride=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.OBJECTIVE_BILLBOARD_OVERRIDE_ATTR,"");
    objective.setBillboardOverride(billboardOverride);
    // Dialogs & conditions
    List<Element> childTags=DOMParsingTools.getChildTags(objectiveTag);
    for(Element childTag : childTags)
    {
      String tagName=childTag.getTagName();
      if (ObjectivesXMLConstants.DIALOG_TAG.equals(tagName))
      {
        DialogElement dialog=DialogsXMLParser.parseDialog(childTag);
        objective.addDialog(dialog);
      }
      else
      {
        ObjectiveCondition condition=parseConditionTag(childTag);
        AchievableGeoDataXMLParser.parseGeoData(childTag,condition);
        objective.addCondition(condition);
      }
    }
    objectives.addObjective(objective);
  }

  private static ObjectiveCondition parseConditionTag(Element conditionTag)
  {
    ObjectiveCondition ret=null;
    String tagName=conditionTag.getTagName();
    NamedNodeMap attrs=conditionTag.getAttributes();
    // Index
    int index=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.CONDITION_INDEX_ATTR,0);
    // Lore info
    String loreInfo=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.CONDITION_LORE_INFO_ATTR,null);
    // Show progress text
    boolean showProgressText=DOMParsingTools.getBooleanAttribute(attrs,ObjectivesXMLConstants.CONDITION_SHOW_PROGRESS_TEXT,true);
    // Show billboard text
    boolean showBillboardText=DOMParsingTools.getBooleanAttribute(attrs,ObjectivesXMLConstants.CONDITION_SHOW_BILLBOARD_TEXT,true);
    // Progress override
    String progressOverride=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.CONDITION_PROGRESS_OVERRIDE_ATTR,null);
    // Count
    int count=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.CONDITION_COUNT_ATTR,1);

    // Specifics
    if (ObjectivesXMLConstants.QUEST_COMPLETE_TAG.equals(tagName))
    {
      ret=parseQuestCompleteCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.MONSTER_DIED_TAG.equals(tagName))
    {
      ret=parseMonsterDiedCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.LANDMARK_DETECTION_TAG.equals(tagName))
    {
      ret=parseLandmarkDetectionCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.INVENTORY_ITEM_TAG.equals(tagName))
    {
      ret=parseInventoryItemCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.ITEM_USED_TAG.equals(tagName))
    {
      ret=parseItemUsedCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.EXTERNAL_INVENTORY_TAG.equals(tagName))
    {
      ret=parseExternalInventoryItemCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.ITEM_TALK_TAG.equals(tagName))
    {
      ret=parseItemTalkCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.FACTION_LEVEL_TAG.equals(tagName))
    {
      ret=parseFactionLevelCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.SKILL_USED_TAG.equals(tagName))
    {
      ret=parseSkillUsedCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.NPC_TALK_TAG.equals(tagName))
    {
      ret=parseNpcTalkCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.NPC_USED_TAG.equals(tagName))
    {
      ret=parseNpcUsedCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.LEVEL_TAG.equals(tagName))
    {
      ret=parseLevelCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.QUEST_BESTOWED_TAG.equals(tagName))
    {
      ret=parseQuestBestowedCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.DETECTING_TAG.equals(tagName))
    {
      ret=parseDetectingCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.ENTER_DETECTION_TAG.equals(tagName))
    {
      ret=parseEnterDetectionCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.EMOTE_TAG.equals(tagName))
    {
      ret=parseEmoteCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.HOBBY_TAG.equals(tagName))
    {
      ret=parseHobbyCondition(attrs,conditionTag);
    }
    else if (ObjectivesXMLConstants.TIME_EXPIRED_TAG.equals(tagName))
    {
      ret=parseTimeExpiredCondition(attrs,conditionTag);
    }
    else
    {
      ret=parseDefaultCondition(attrs,conditionTag);
    }
    if (ret!=null)
    {
      ret.setIndex(index);
      ret.setLoreInfo(loreInfo);
      ret.setShowBillboardText(showBillboardText);
      ret.setShowProgressText(showProgressText);
      ret.setProgressOverride(progressOverride);
      ret.setCount(count);
    }
    return ret;
  }

  private static QuestCompleteCondition parseQuestCompleteCondition(NamedNodeMap attrs, Element conditionTag)
  {
    QuestCompleteCondition condition=new QuestCompleteCondition();
    // Achievable
    int achievableId=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.QUEST_COMPLETE_ACHIEVABLE_ID_ATTR,0);
    if (achievableId>0)
    {
      Proxy<Achievable> proxy=new Proxy<Achievable>();
      proxy.setId(achievableId);
      condition.setProxy(proxy);
    }
    // Quest category
    String questCategory=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.QUEST_COMPLETE_QUEST_CATEGORY_ATTR,null);
    condition.setQuestCategory(questCategory);
    return condition;
  }

  private static MonsterDiedCondition parseMonsterDiedCondition(NamedNodeMap attrs, Element conditionTag)
  {
    MonsterDiedCondition condition=new MonsterDiedCondition();
    // Mob ID
    int mobId=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.MONSTER_DIE_MOB_ID_ATTR,0);
    if (mobId>0)
    {
      condition.setMobId(Integer.valueOf(mobId));
    }
    // Mob name
    String mobName=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.MONSTER_DIE_MOB_NAME_ATTR,null);
    condition.setMobName(mobName);
    // Mob selections
    List<Element> monsterSelectionTags=DOMParsingTools.getChildTagsByName(conditionTag,ObjectivesXMLConstants.MONSTER_SELECTION_TAG);
    for(Element monsterSelectionTag : monsterSelectionTags)
    {
      NamedNodeMap selectionAttrs=monsterSelectionTag.getAttributes();
      MobSelection selection=new MobSelection();
      // Where
      String where=DOMParsingTools.getStringAttribute(selectionAttrs,ObjectivesXMLConstants.MONSTER_SELECTION_WHERE_ATTR,null);
      selection.setWhere(where);
      // What
      EntityClassification what=new EntityClassification();
      AgentsXMLIO.parseEntityClassification(what,selectionAttrs);
      selection.setWhat(what);
      condition.getMobSelections().add(selection);
    }
    return condition;
  }

  private static LandmarkDetectionCondition parseLandmarkDetectionCondition(NamedNodeMap attrs, Element conditionTag)
  {
    LandmarkDetectionCondition condition=new LandmarkDetectionCondition();
    // Landmark proxy
    // - id
    int landmarkId=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.LANDMARK_DETECTION_ID_ATTR,0);
    // - name
    String landmarkName=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.LANDMARK_DETECTION_NAME_ATTR,"?");
    Proxy<LandmarkDescription> proxy=new Proxy<LandmarkDescription>();
    proxy.setId(landmarkId);
    proxy.setName(landmarkName);
    condition.setLandmarkProxy(proxy);
    return condition;
  }

  private static InventoryItemCondition parseInventoryItemCondition(NamedNodeMap attrs, Element conditionTag)
  {
    InventoryItemCondition condition=new InventoryItemCondition();
    parseItemCondition(condition,attrs,conditionTag);
    return condition;
  }

  private static ItemUsedCondition parseItemUsedCondition(NamedNodeMap attrs, Element conditionTag)
  {
    ItemUsedCondition condition=new ItemUsedCondition();
    parseItemCondition(condition,attrs,conditionTag);
    return condition;
  }

  private static ExternalInventoryItemCondition parseExternalInventoryItemCondition(NamedNodeMap attrs, Element conditionTag)
  {
    ExternalInventoryItemCondition condition=new ExternalInventoryItemCondition();
    parseItemCondition(condition,attrs,conditionTag);
    return condition;
  }

  private static ItemTalkCondition parseItemTalkCondition(NamedNodeMap attrs, Element conditionTag)
  {
    ItemTalkCondition condition=new ItemTalkCondition();
    parseItemCondition(condition,attrs,conditionTag);
    return condition;
  }

  private static void parseItemCondition(ItemCondition condition, NamedNodeMap attrs, Element conditionTag)
  {
    // Item proxy
    Proxy<Item> itemProxy=parseItemProxy(attrs);
    condition.setProxy(itemProxy);
  }

  private static FactionLevelCondition parseFactionLevelCondition(NamedNodeMap attrs, Element conditionTag)
  {
    FactionLevelCondition condition=new FactionLevelCondition();
    // Faction proxy
    // - id
    int factionId=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.FACTION_LEVEL_ID_ATTR,0);
    // - name
    String factionName=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.FACTION_LEVEL_NAME_ATTR,"?");
    Proxy<Faction> proxy=new Proxy<Faction>();
    proxy.setId(factionId);
    proxy.setName(factionName);
    // - object
    Faction faction=FactionsRegistry.getInstance().getById(factionId);
    proxy.setObject(faction);
    condition.setProxy(proxy);
    // Tier
    int tier=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.FACTION_LEVEL_TIER_ATTR,1);
    condition.setTier(tier);
    return condition;
  }

  private static SkillUsedCondition parseSkillUsedCondition(NamedNodeMap attrs, Element conditionTag)
  {
    SkillUsedCondition condition=new SkillUsedCondition();
    // Skill proxy
    // - id
    int skillId=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.SKILL_USED_SKILL_ID_ATTR,0);
    if (skillId!=0)
    {
      // - name
      String skillName=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.SKILL_USED_SKILL_NAME_ATTR,"?");
      Proxy<SkillDescription> proxy=new Proxy<SkillDescription>();
      proxy.setId(skillId);
      proxy.setName(skillName);
      condition.setProxy(proxy);
    }
    // Max per day
    int maxPerDay=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.SKILL_USED_MAX_PER_DAY_ATTR,-1);
    if (maxPerDay!=-1)
    {
      condition.setMaxPerDay(Integer.valueOf(maxPerDay));
    }
    return condition;
  }

  private static NpcTalkCondition parseNpcTalkCondition(NamedNodeMap attrs, Element conditionTag)
  {
    NpcTalkCondition condition=new NpcTalkCondition();
    parseNpcCondition(condition,attrs,conditionTag);
    return condition;
  }

  private static NpcUsedCondition parseNpcUsedCondition(NamedNodeMap attrs, Element conditionTag)
  {
    NpcUsedCondition condition=new NpcUsedCondition();
    parseNpcCondition(condition,attrs,conditionTag);
    return condition;
  }

  private static void parseNpcCondition(NpcCondition condition, NamedNodeMap attrs, Element conditionTag)
  {
    Proxy<NpcDescription> npcProxy=SharedXMLUtils.parseNpcProxy(attrs);
    condition.setProxy(npcProxy);
  }

  private static LevelCondition parseLevelCondition(NamedNodeMap attrs, Element conditionTag)
  {
    LevelCondition condition=new LevelCondition();
    // Level
    int level=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.LEVEL_ATTR,1);
    condition.setLevel(level);
    return condition;
  }

  private static QuestBestowedCondition parseQuestBestowedCondition(NamedNodeMap attrs, Element conditionTag)
  {
    QuestBestowedCondition condition=new QuestBestowedCondition();
    // Achievable
    int achievableId=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.QUEST_BESTOWED_ACHIEVABLE_ID_ATTR,0);
    if (achievableId>0)
    {
      Proxy<Achievable> proxy=new Proxy<Achievable>();
      proxy.setId(achievableId);
      condition.setProxy(proxy);
    }
    return condition;
  }

  private static DetectingCondition parseDetectingCondition(NamedNodeMap attrs, Element conditionTag)
  {
    DetectingCondition condition=new DetectingCondition();
    parseDetectionCondition(condition,attrs,conditionTag);
    return condition;
  }

  private static EnterDetectionCondition parseEnterDetectionCondition(NamedNodeMap attrs, Element conditionTag)
  {
    EnterDetectionCondition condition=new EnterDetectionCondition();
    parseDetectionCondition(condition,attrs,conditionTag);
    return condition;
  }

  private static void parseDetectionCondition(DetectionCondition condition, NamedNodeMap attrs, Element conditionTag)
  {
    // Target
    ConditionTarget target=parseTarget(attrs);
    condition.setTarget(target);
  }

  private static EmoteCondition parseEmoteCondition(NamedNodeMap attrs, Element conditionTag)
  {
    EmoteCondition condition=new EmoteCondition();
    // Emote proxy
    // - id
    int emoteId=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.EMOTE_ID_ATTR,0);
    // - command
    String command=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.EMOTE_COMMAND_ATTR,"?");
    Proxy<EmoteDescription> proxy=new Proxy<EmoteDescription>();
    proxy.setId(emoteId);
    proxy.setName(command);
    condition.setProxy(proxy);
    // Max per day
    int maxPerDay=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.EMOTE_MAX_DAILY_ATTR,-1);
    if (maxPerDay!=-1)
    {
      condition.setMaxDaily(Integer.valueOf(maxPerDay));
    }
    // Target
    ConditionTarget target=parseTarget(attrs);
    condition.setTarget(target);
    return condition;
  }

  private static HobbyCondition parseHobbyCondition(NamedNodeMap attrs, Element conditionTag)
  {
    HobbyCondition condition=new HobbyCondition();
    // Item proxy
    Proxy<Item> itemProxy=parseItemProxy(attrs);
    condition.setProxy(itemProxy);
    return condition;
  }

  private static TimeExpiredCondition parseTimeExpiredCondition(NamedNodeMap attrs, Element conditionTag)
  {
    TimeExpiredCondition condition=new TimeExpiredCondition();
    // Duration
    int duration=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.TIME_EXPIRED_DURATION_ATTR,0);
    condition.setDuration(duration);
    return condition;
  }

  private static ConditionTarget parseTarget(NamedNodeMap attrs)
  {
    ConditionTarget target=null;
    // NPC proxy
    Proxy<NpcDescription> npcProxy=SharedXMLUtils.parseNpcProxy(attrs);
    // Mob proxy
    Proxy<MobDescription> mobProxy=parseMobProxy(attrs);
    if ((npcProxy!=null) || (mobProxy!=null))
    {
      target=new ConditionTarget();
      target.setNpcProxy(npcProxy);
      target.setMobProxy(mobProxy);
    }
    return target;
  }

  private static DefaultObjectiveCondition parseDefaultCondition(NamedNodeMap attrs, Element conditionTag)
  {
    // Type
    String typeStr=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.CONDITION_TYPE_ATTR,null);
    ConditionType type=null;
    if (typeStr!=null)
    {
      type=ConditionType.valueOf(typeStr);
    }
    DefaultObjectiveCondition condition=new DefaultObjectiveCondition(type);
    return condition;
  }

  private static Proxy<MobDescription> parseMobProxy(NamedNodeMap attrs)
  {
    Proxy<MobDescription> proxy=null;
    // Mob proxy
    // - id
    int mobId=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.MOB_ID_ATTR,0);
    if (mobId!=0)
    {
      // - name
      String mobName=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.MOB_NAME_ATTR,"?");
      proxy=new Proxy<MobDescription>();
      proxy.setId(mobId);
      proxy.setName(mobName);
    }
    return proxy;
  }

  private static Proxy<Item> parseItemProxy(NamedNodeMap attrs)
  {
    Proxy<Item> proxy=null;
    // Item proxy
    // - id
    int itemId=DOMParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.ITEM_ID_ATTR,0);
    if (itemId!=0)
    {
      // - name
      String itemName=DOMParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.ITEM_NAME_ATTR,"?");
      proxy=new Proxy<Item>();
      proxy.setId(itemId);
      proxy.setName(itemName);
    }
    return proxy;
  }
}
