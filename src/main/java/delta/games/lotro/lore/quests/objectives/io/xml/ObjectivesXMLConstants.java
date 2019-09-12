package delta.games.lotro.lore.quests.objectives.io.xml;

/**
 * Constants for tags and attribute names used in the objectives XML tags.
 * @author DAM
 */
public class ObjectivesXMLConstants
{
  /**
   * Tag 'objectives'.
   */
  public static final String OBJECTIVES_TAG="objectives";

  /**
   * Tag 'objective'.
   */
  public static final String OBJECTIVE_TAG="objective";
  /**
   * Tag 'objective', attribute 'index'.
   */
  public static final String OBJECTIVE_INDEX_ATTR="index";
  /**
   * Tag 'objective', attribute 'text'.
   */
  public static final String OBJECTIVE_TEXT_ATTR="text";
  
  /**
   * Tag 'condition'.
   */
  public static final String CONDITION_TAG="condition";
  /**
   * Tag 'condition', attribute 'index'.
   */
  public static final String CONDITION_INDEX_ATTR="index";
  /**
   * Tag 'condition', attribute 'loreInfo'.
   */
  public static final String CONDITION_LORE_INFO_ATTR="loreInfo";
  /**
   * Tag 'condition', attribute 'progressOverride'.
   */
  public static final String CONDITION_PROGRESS_OVERRIDE_ATTR="progressOverride";
  /**
   * Tag 'condition', attribute 'type'.
   */
  public static final String CONDITION_TYPE_ATTR="type";

  /**
   * Tag 'questComplete'.
   */
  public static final String QUEST_COMPLETE_TAG="questComplete";
  /**
   * Tag 'questComplete', attribute 'achievableId'.
   */
  public static final String QUEST_COMPLETE_ACHIEVABLE_ID_ATTR="achievableId";
  /**
   * Tag 'questComplete', attribute 'questCategory'.
   */
  public static final String QUEST_COMPLETE_QUEST_CATEGORY_ATTR="questCategory";
  /**
   * Tag 'questComplete', attribute 'count'.
   */
  public static final String QUEST_COMPLETE_COUNT_ATTR="count";

  /**
   * Tag 'monsterDie'.
   */
  public static final String MONSTER_DIED_TAG="monsterDied";
  /**
   * Tag 'monsterDie', attribute 'mobId'.
   */
  public static final String MONSTER_DIE_MOB_ID_ATTR="mobId";
  /**
   * Tag 'monsterDie', attribute 'mobName'.
   */
  public static final String MONSTER_DIE_MOB_NAME_ATTR="mobName";
  /**
   * Tag 'monsterDie', attribute 'count'.
   */
  public static final String MONSTER_DIE_COUNT_ATTR="count";
  /**
   * Tag 'monsterSelection'.
   */
  public static final String MONSTER_SELECTION_TAG="monsterSelection";
  /**
   * Tag 'monsterSelection', attribute 'where'.
   */
  public static final String MONSTER_SELECTION_WHERE_ATTR="where";
  /**
   * Tag 'monsterSelection', attribute 'what'.
   */
  public static final String MONSTER_SELECTION_WHAT_ATTR="what";

  /**
   * Tag 'landmarkDetection'.
   */
  public static final String LANDMARK_DETECTION_TAG="landmarkDetection";
  /**
   * Tag 'landmarkDetection', attribute 'landmarkId'.
   */
  public static final String LANDMARK_DETECTION_ID_ATTR="landmarkId";
  /**
   * Tag 'landmarkDetection', attribute 'landmarkName'.
   */
  public static final String LANDMARK_DETECTION_NAME_ATTR="landmarkName";

  /**
   * Tag 'inventoryItem'.
   */
  public static final String INVENTORY_ITEM_TAG="inventoryItem";
  /**
   * Tag 'itemUsed'.
   */
  public static final String ITEM_USED_TAG="itemUsed";
  /**
   * Tag 'externalInventoryItem'.
   */
  public static final String EXTERNAL_INVENTORY_TAG="externalInventoryItem";
  /**
   * Tag 'itemTalk'.
   */
  public static final String ITEM_TALK_TAG="itemTalk";
  /**
   * Item related tags, attribute 'itemId'.
   */
  public static final String ITEM_ID_ATTR="itemId";
  /**
   * Item related tags, attribute 'itemName'.
   */
  public static final String ITEM_NAME_ATTR="itemName";
  /**
   * Item related tags, attribute 'count'.
   */
  public static final String ITEM_COUNT_ATTR="count";

  /**
   * Tag 'factionLevel'.
   */
  public static final String FACTION_LEVEL_TAG="factionLevel";
  /**
   * Tag 'factionLevel', attribute 'factionId'.
   */
  public static final String FACTION_LEVEL_ID_ATTR="factionId";
  /**
   * Tag 'factionLevel', attribute 'factionName'.
   */
  public static final String FACTION_LEVEL_NAME_ATTR="factionName";
  /**
   * Tag 'factionLevel', attribute 'tier'.
   */
  public static final String FACTION_LEVEL_TIER_ATTR="tier";

  /**
   * Tag 'skillUsed'.
   */
  public static final String SKILL_USED_TAG="skillUsed";
  /**
   * Tag 'skillUsed', attribute 'skillId'.
   */
  public static final String SKILL_USED_SKILL_ID_ATTR="skillId";
  /**
   * Tag 'skillUsed', attribute 'skillName'.
   */
  public static final String SKILL_USED_SKILL_NAME_ATTR="skillName";
  /**
   * Tag 'skillUsed', attribute 'count'.
   */
  public static final String SKILL_USED_COUNT_ATTR="count";
  /**
   * Tag 'skillUsed', attribute 'maxPerDay'.
   */
  public static final String SKILL_USED_MAX_PER_DAY_ATTR="maxPerDay";

  /**
   * Tag 'npcTalk'.
   */
  public static final String NPC_TALK_TAG="npcTalk";
  /**
   * Tag 'npcUsed'.
   */
  public static final String NPC_USED_TAG="npcUsed";

  /**
   * Tag 'level'.
   */
  public static final String LEVEL_TAG="level";
  /**
   * Tag 'level', attribute 'level'.
   */
  public static final String LEVEL_ATTR="level";

  /**
   * Tag 'questBestowed'.
   */
  public static final String QUEST_BESTOWED_TAG="questBestowed";
  /**
   * Tag 'questBestowed', attribute 'achievableId'.
   */
  public static final String QUEST_BESTOWED_ACHIEVABLE_ID_ATTR="achievableId";

  /**
   * Tag 'enterDetection'.
   */
  public static final String ENTER_DETECTION_TAG="enterDetection";
  /**
   * Tag 'detecting'.
   */
  public static final String DETECTING_TAG="detecting";

  // Shared attributes
  /**
   * Tag 'enterDetection'/'detecting', attribute 'mobId'.
   */
  public static final String MOB_ID_ATTR="mobId";
  /**
   * Tag 'enterDetection'/'detecting', attribute 'npcName'.
   */
  public static final String MOB_NAME_ATTR="mobName";

  /**
   * Tag 'emote'.
   */
  public static final String EMOTE_TAG="emote";
  /**
   * Tag 'emote', attribute 'emoteId'.
   */
  public static final String EMOTE_ID_ATTR="emoteId";
  /**
   * Tag 'emote', attribute 'command'.
   */
  public static final String EMOTE_COMMAND_ATTR="command";
  /**
   * Tag 'emote', attribute 'count'.
   */
  public static final String EMOTE_COUNT_ATTR="count";
  /**
   * Tag 'emote', attribute 'maxDaily'.
   */
  public static final String EMOTE_MAX_DAILY_ATTR="maxDaily";

  /**
   * Tag 'hobby'.
   */
  public static final String HOBBY_TAG="hobby";
  /**
   * Tag 'hobby', attribute 'count'.
   */
  public static final String HOBBY_COUNT_ATTR="count";

  /**
   * Tag 'timeExpired'.
   */
  public static final String TIME_EXPIRED_TAG="timeExpired";
  /**
   * Tag 'timeExpired', attribute 'duration'.
   */
  public static final String TIME_EXPIRED_DURATION_ATTR="duration";
}
