package delta.games.lotro.lore.quests.objectives.io.xml;

import org.xml.sax.Attributes;

import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.SAXParserValve;
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
import delta.games.lotro.lore.quests.geo.AchievableGeoPoint;
import delta.games.lotro.lore.quests.geo.io.xml.AchievableGeoDataXMLConstants;
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
public class ObjectivesSaxXMLParser extends SAXParserValve<Void>
{
  private ObjectivesManager _objectives;
  private Objective _currentObjective;
  private ObjectiveCondition _condition;

  /**
   * Set the storage for objectives.
   * @param objectives Storage to set.
   */
  public void setObjectives(ObjectivesManager objectives)
  {
    _objectives=objectives;
  }

  @Override
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
  {
    if (ObjectivesXMLConstants.OBJECTIVE_TAG.equals(tagName))
    {
      _currentObjective=parseObjective(attrs);
      _objectives.addObjective(_currentObjective);
    }
    else if (ObjectivesXMLConstants.DIALOG_TAG.equals(tagName))
    {
      DialogElement dialog=DialogsSaxParser.parseDialog(attrs);
      _currentObjective.addDialog(dialog);
    }
    else if (ObjectivesXMLConstants.MONSTER_SELECTION_TAG.equals(tagName))
    {
      // Mob selections
      MobSelection mobSelection=parseMobSelection(attrs);
      ((MonsterDiedCondition)_condition).getMobSelections().add(mobSelection);
    }
    else if (AchievableGeoDataXMLConstants.POINT_TAG.equals(tagName))
    {
      AchievableGeoPoint point=AchievableGeoDataXMLParser.parseGeoDataElement(attrs);
      _condition.addPoint(point);
    }
    else
    {
      _condition=parseCondition(tagName, attrs);
      _currentObjective.addCondition(_condition);
    }
    return this;
  }

  @Override
  public SAXParserValve<?> handleEndTag(String tagName)
  {
    if (ObjectivesXMLConstants.OBJECTIVE_TAG.equals(tagName))
    {
      return getParent();
    }
    return this;
  }

  private Objective parseObjective(Attributes attrs)
  {
    Objective objective=new Objective();
    // Index
    int objectiveIndex=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.OBJECTIVE_INDEX_ATTR,1);
    objective.setIndex(objectiveIndex);
    // Description
    String description=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.OBJECTIVE_TEXT_ATTR,"");
    objective.setDescription(description);
    // Lore override
    String loreOverride=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.OBJECTIVE_LORE_OVERRIDE_ATTR,"");
    objective.setLoreOverride(loreOverride);
    // Progress override
    String progressOverride=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.OBJECTIVE_PROGRESS_OVERRIDE_ATTR,"");
    objective.setProgressOverride(progressOverride);
    // Billboard override
    String billboardOverride=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.OBJECTIVE_BILLBOARD_OVERRIDE_ATTR,"");
    objective.setBillboardOverride(billboardOverride);
    return objective;
  }

  private static void parseConditionAttributes(Attributes attrs, ObjectiveCondition condition)
  {
    // Index
    int index=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.CONDITION_INDEX_ATTR,0);
    // Lore info
    String loreInfo=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.CONDITION_LORE_INFO_ATTR,null);
    // Show progress text
    boolean showProgressText=SAXParsingTools.getBooleanAttribute(attrs,ObjectivesXMLConstants.CONDITION_SHOW_PROGRESS_TEXT,true);
    // Show billboard text
    boolean showBillboardText=SAXParsingTools.getBooleanAttribute(attrs,ObjectivesXMLConstants.CONDITION_SHOW_BILLBOARD_TEXT,true);
    // Progress override
    String progressOverride=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.CONDITION_PROGRESS_OVERRIDE_ATTR,null);
    // Count
    int count=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.CONDITION_COUNT_ATTR,1);

    condition.setIndex(index);
    condition.setLoreInfo(loreInfo);
    condition.setShowBillboardText(showBillboardText);
    condition.setShowProgressText(showProgressText);
    condition.setProgressOverride(progressOverride);
    condition.setCount(count);
  }

  private ObjectiveCondition parseCondition(String tagName, Attributes attrs)
  {
    ObjectiveCondition ret=null;
    // Specifics
    if (ObjectivesXMLConstants.QUEST_COMPLETE_TAG.equals(tagName))
    {
      ret=parseQuestCompleteCondition(attrs);
    }
    else if (ObjectivesXMLConstants.MONSTER_DIED_TAG.equals(tagName))
    {
      ret=parseMonsterDiedCondition(attrs);
    }
    else if (ObjectivesXMLConstants.LANDMARK_DETECTION_TAG.equals(tagName))
    {
      ret=parseLandmarkDetectionCondition(attrs);
    }
    else if (ObjectivesXMLConstants.INVENTORY_ITEM_TAG.equals(tagName))
    {
      ret=parseInventoryItemCondition(attrs);
    }
    else if (ObjectivesXMLConstants.ITEM_USED_TAG.equals(tagName))
    {
      ret=parseItemUsedCondition(attrs);
    }
    else if (ObjectivesXMLConstants.EXTERNAL_INVENTORY_TAG.equals(tagName))
    {
      ret=parseExternalInventoryItemCondition(attrs);
    }
    else if (ObjectivesXMLConstants.ITEM_TALK_TAG.equals(tagName))
    {
      ret=parseItemTalkCondition(attrs);
    }
    else if (ObjectivesXMLConstants.FACTION_LEVEL_TAG.equals(tagName))
    {
      ret=parseFactionLevelCondition(attrs);
    }
    else if (ObjectivesXMLConstants.SKILL_USED_TAG.equals(tagName))
    {
      ret=parseSkillUsedCondition(attrs);
    }
    else if (ObjectivesXMLConstants.NPC_TALK_TAG.equals(tagName))
    {
      ret=parseNpcTalkCondition(attrs);
    }
    else if (ObjectivesXMLConstants.NPC_USED_TAG.equals(tagName))
    {
      ret=parseNpcUsedCondition(attrs);
    }
    else if (ObjectivesXMLConstants.LEVEL_TAG.equals(tagName))
    {
      ret=parseLevelCondition(attrs);
    }
    else if (ObjectivesXMLConstants.QUEST_BESTOWED_TAG.equals(tagName))
    {
      ret=parseQuestBestowedCondition(attrs);
    }
    else if (ObjectivesXMLConstants.DETECTING_TAG.equals(tagName))
    {
      ret=parseDetectingCondition(attrs);
    }
    else if (ObjectivesXMLConstants.ENTER_DETECTION_TAG.equals(tagName))
    {
      ret=parseEnterDetectionCondition(attrs);
    }
    else if (ObjectivesXMLConstants.EMOTE_TAG.equals(tagName))
    {
      ret=parseEmoteCondition(attrs);
    }
    else if (ObjectivesXMLConstants.HOBBY_TAG.equals(tagName))
    {
      ret=parseHobbyCondition(attrs);
    }
    else if (ObjectivesXMLConstants.TIME_EXPIRED_TAG.equals(tagName))
    {
      ret=parseTimeExpiredCondition(attrs);
    }
    else
    {
      ret=parseDefaultCondition(attrs);
    }
    parseConditionAttributes(attrs,ret);
    return ret;
  }

  private static QuestCompleteCondition parseQuestCompleteCondition(Attributes attrs)
  {
    QuestCompleteCondition condition=new QuestCompleteCondition();
    // Achievable
    int achievableId=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.QUEST_COMPLETE_ACHIEVABLE_ID_ATTR,0);
    if (achievableId>0)
    {
      Proxy<Achievable> proxy=new Proxy<Achievable>();
      proxy.setId(achievableId);
      condition.setProxy(proxy);
    }
    // Quest category
    String questCategory=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.QUEST_COMPLETE_QUEST_CATEGORY_ATTR,null);
    condition.setQuestCategory(questCategory);
    return condition;
  }

  private static MonsterDiedCondition parseMonsterDiedCondition(Attributes attrs)
  {
    MonsterDiedCondition condition=new MonsterDiedCondition();
    // Mob ID
    int mobId=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.MONSTER_DIE_MOB_ID_ATTR,0);
    if (mobId>0)
    {
      condition.setMobId(Integer.valueOf(mobId));
    }
    // Mob name
    String mobName=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.MONSTER_DIE_MOB_NAME_ATTR,null);
    condition.setMobName(mobName);
    return condition;
  }

  private static MobSelection parseMobSelection(Attributes selectionAttrs)
  {
    MobSelection selection=new MobSelection();
    // Where
    String where=SAXParsingTools.getStringAttribute(selectionAttrs,ObjectivesXMLConstants.MONSTER_SELECTION_WHERE_ATTR,null);
    selection.setWhere(where);
    // What
    EntityClassification what=new EntityClassification();
    AgentsXMLIO.parseEntityClassification(what,selectionAttrs);
    selection.setWhat(what);
    return selection;
  }

  private static LandmarkDetectionCondition parseLandmarkDetectionCondition(Attributes attrs)
  {
    LandmarkDetectionCondition condition=new LandmarkDetectionCondition();
    // Landmark proxy
    // - id
    int landmarkId=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.LANDMARK_DETECTION_ID_ATTR,0);
    if (landmarkId!=0)
    {
      // - name
      String landmarkName=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.LANDMARK_DETECTION_NAME_ATTR,"?");
      Proxy<LandmarkDescription> proxy=new Proxy<LandmarkDescription>();
      proxy.setId(landmarkId);
      proxy.setName(landmarkName);
      condition.setLandmarkProxy(proxy);
    }
    return condition;
  }

  private static InventoryItemCondition parseInventoryItemCondition(Attributes attrs)
  {
    InventoryItemCondition condition=new InventoryItemCondition();
    parseItemCondition(condition,attrs);
    return condition;
  }

  private static ItemUsedCondition parseItemUsedCondition(Attributes attrs)
  {
    ItemUsedCondition condition=new ItemUsedCondition();
    parseItemCondition(condition,attrs);
    return condition;
  }

  private static ExternalInventoryItemCondition parseExternalInventoryItemCondition(Attributes attrs)
  {
    ExternalInventoryItemCondition condition=new ExternalInventoryItemCondition();
    parseItemCondition(condition,attrs);
    return condition;
  }

  private static ItemTalkCondition parseItemTalkCondition(Attributes attrs)
  {
    ItemTalkCondition condition=new ItemTalkCondition();
    parseItemCondition(condition,attrs);
    return condition;
  }

  private static void parseItemCondition(ItemCondition condition, Attributes attrs)
  {
    // Item proxy
    Proxy<Item> itemProxy=parseItemProxy(attrs);
    condition.setProxy(itemProxy);
  }

  private static FactionLevelCondition parseFactionLevelCondition(Attributes attrs)
  {
    FactionLevelCondition condition=new FactionLevelCondition();
    // Faction proxy
    // - id
    int factionId=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.FACTION_LEVEL_ID_ATTR,0);
    // - name
    String factionName=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.FACTION_LEVEL_NAME_ATTR,"?");
    Proxy<Faction> proxy=new Proxy<Faction>();
    proxy.setId(factionId);
    proxy.setName(factionName);
    // - object
    Faction faction=FactionsRegistry.getInstance().getById(factionId);
    proxy.setObject(faction);
    condition.setProxy(proxy);
    // Tier
    int tier=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.FACTION_LEVEL_TIER_ATTR,1);
    condition.setTier(tier);
    return condition;
  }

  private static SkillUsedCondition parseSkillUsedCondition(Attributes attrs)
  {
    SkillUsedCondition condition=new SkillUsedCondition();
    // Skill proxy
    // - id
    int skillId=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.SKILL_USED_SKILL_ID_ATTR,0);
    if (skillId!=0)
    {
      // - name
      String skillName=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.SKILL_USED_SKILL_NAME_ATTR,"?");
      Proxy<SkillDescription> proxy=new Proxy<SkillDescription>();
      proxy.setId(skillId);
      proxy.setName(skillName);
      condition.setProxy(proxy);
    }
    // Max per day
    int maxPerDay=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.SKILL_USED_MAX_PER_DAY_ATTR,-1);
    if (maxPerDay!=-1)
    {
      condition.setMaxPerDay(Integer.valueOf(maxPerDay));
    }
    return condition;
  }

  private static NpcTalkCondition parseNpcTalkCondition(Attributes attrs)
  {
    NpcTalkCondition condition=new NpcTalkCondition();
    parseNpcCondition(condition,attrs);
    return condition;
  }

  private static NpcUsedCondition parseNpcUsedCondition(Attributes attrs)
  {
    NpcUsedCondition condition=new NpcUsedCondition();
    parseNpcCondition(condition,attrs);
    return condition;
  }

  private static void parseNpcCondition(NpcCondition condition, Attributes attrs)
  {
    Proxy<NpcDescription> npcProxy=SharedXMLUtils.parseNpcProxy(attrs);
    condition.setProxy(npcProxy);
  }

  private static LevelCondition parseLevelCondition(Attributes attrs)
  {
    LevelCondition condition=new LevelCondition();
    // Level
    int level=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.LEVEL_ATTR,1);
    condition.setLevel(level);
    return condition;
  }

  private static QuestBestowedCondition parseQuestBestowedCondition(Attributes attrs)
  {
    QuestBestowedCondition condition=new QuestBestowedCondition();
    // Achievable
    int achievableId=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.QUEST_BESTOWED_ACHIEVABLE_ID_ATTR,0);
    if (achievableId>0)
    {
      Proxy<Achievable> proxy=new Proxy<Achievable>();
      proxy.setId(achievableId);
      condition.setProxy(proxy);
    }
    return condition;
  }

  private static DetectingCondition parseDetectingCondition(Attributes attrs)
  {
    DetectingCondition condition=new DetectingCondition();
    parseDetectionCondition(condition,attrs);
    return condition;
  }

  private static EnterDetectionCondition parseEnterDetectionCondition(Attributes attrs)
  {
    EnterDetectionCondition condition=new EnterDetectionCondition();
    parseDetectionCondition(condition,attrs);
    return condition;
  }

  private static void parseDetectionCondition(DetectionCondition condition, Attributes attrs)
  {
    // Target
    ConditionTarget target=parseTarget(attrs);
    condition.setTarget(target);
  }

  private static EmoteCondition parseEmoteCondition(Attributes attrs)
  {
    EmoteCondition condition=new EmoteCondition();
    // Emote proxy
    // - id
    int emoteId=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.EMOTE_ID_ATTR,0);
    // - command
    String command=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.EMOTE_COMMAND_ATTR,"?");
    Proxy<EmoteDescription> proxy=new Proxy<EmoteDescription>();
    proxy.setId(emoteId);
    proxy.setName(command);
    condition.setProxy(proxy);
    // Max per day
    int maxPerDay=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.EMOTE_MAX_DAILY_ATTR,-1);
    if (maxPerDay!=-1)
    {
      condition.setMaxDaily(Integer.valueOf(maxPerDay));
    }
    // Target
    ConditionTarget target=parseTarget(attrs);
    condition.setTarget(target);
    return condition;
  }

  private static HobbyCondition parseHobbyCondition(Attributes attrs)
  {
    HobbyCondition condition=new HobbyCondition();
    // Item proxy
    Proxy<Item> itemProxy=parseItemProxy(attrs);
    condition.setProxy(itemProxy);
    return condition;
  }

  private static TimeExpiredCondition parseTimeExpiredCondition(Attributes attrs)
  {
    TimeExpiredCondition condition=new TimeExpiredCondition();
    // Duration
    int duration=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.TIME_EXPIRED_DURATION_ATTR,0);
    condition.setDuration(duration);
    return condition;
  }

  private static ConditionTarget parseTarget(Attributes attrs)
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

  private static DefaultObjectiveCondition parseDefaultCondition(Attributes attrs)
  {
    // Type
    String typeStr=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.CONDITION_TYPE_ATTR,null);
    ConditionType type=null;
    if (typeStr!=null)
    {
      type=ConditionType.valueOf(typeStr);
    }
    DefaultObjectiveCondition condition=new DefaultObjectiveCondition(type);
    return condition;
  }

  private static Proxy<MobDescription> parseMobProxy(Attributes attrs)
  {
    Proxy<MobDescription> proxy=null;
    // Mob proxy
    // - id
    int mobId=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.MOB_ID_ATTR,0);
    if (mobId!=0)
    {
      // - name
      String mobName=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.MOB_NAME_ATTR,"?");
      proxy=new Proxy<MobDescription>();
      proxy.setId(mobId);
      proxy.setName(mobName);
    }
    return proxy;
  }

  private static Proxy<Item> parseItemProxy(Attributes attrs)
  {
    Proxy<Item> proxy=null;
    // Item proxy
    // - id
    int itemId=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.ITEM_ID_ATTR,0);
    if (itemId!=0)
    {
      // - name
      String itemName=SAXParsingTools.getStringAttribute(attrs,ObjectivesXMLConstants.ITEM_NAME_ATTR,"?");
      proxy=new Proxy<Item>();
      proxy.setId(itemId);
      proxy.setName(itemName);
    }
    return proxy;
  }
}
