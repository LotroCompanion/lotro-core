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
  public static final String MONSTER_DIE_TAG="monsterDie";
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
}
