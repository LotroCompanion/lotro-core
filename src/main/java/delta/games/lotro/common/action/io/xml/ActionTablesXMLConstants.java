package delta.games.lotro.common.action.io.xml;

/**
 * Constants for tags and attribute names used in the
 * action tables XML documents.
 * @author DAM
 */
public class ActionTablesXMLConstants
{
  /**
   * Tag 'actionTable'.
   */
  public static final String ACTION_TABLE_USE_TAG="actionTable";
  /**
   * Tag 'actionTable', attribute 'id'.
   */
  public static final String ACTION_TABLE_USE_ID_ATTR="id";
  /**
   * Tag 'actionTable', attribute 'minLevel'.
   */
  public static final String MIN_LEVEL_ATTR="minLevel";
  /**
   * Tag 'actionTable', attribute 'minLevel'.
   */
  public static final String MAX_LEVEL_ATTR="maxLevel";

  /**
   * Tag 'actionTables'.
   */
  public static final String ACTION_TABLES_TAG="actionTables";
  /**
   * Tag 'actionTable'.
   */
  public static final String ACTION_TABLE_TAG="actionTable";
  /**
   * Tag 'actionTable', attribute 'id'.
   */
  public static final String ACTION_TABLE_ID_ATTR="id";
  /**
   * Tag 'actionTableEntry'.
   */
  public static final String ACTION_TABLE_ENTRY_TAG="actionTableEntry";
  /**
   * Tag 'actionTableEntry', attribute 'probability'.
   */
  public static final String ACTION_TABLE_ENTRY_PROBABILITY_ATTR="probability";
  /**
   * Tag 'actionTableEntry', attribute 'cooldown'.
   */
  public static final String ACTION_TABLE_ENTRY_COOLDOWN_ATTR="cooldown";
  /**
   * Tag 'actionTableEntry', attribute 'requiredHints'.
   */
  public static final String ACTION_TABLE_ENTRY_REQUIRED_HINTS_ATTR="requiredHints";
  /**
   * Tag 'actionTableEntry', attribute 'disallowedHints'.
   */
  public static final String ACTION_TABLE_ENTRY_DISALLOWED_HINTS_ATTR="disallowedHints";
  /**
   * Tag 'actionTableEntry', attribute 'cooldownChannel'.
   */
  public static final String ACTION_TABLE_ENTRY_COOLDOWN_CHANNEL_ATTR="cooldownChannel";
  /**
   * Tag 'actionTableEntry', attribute 'targetCooldown'.
   */
  public static final String ACTION_TABLE_ENTRY_TARGET_COOLDOWN_ATTR="targetCooldown";
  /**
   * Tag 'actionTableEntry', attribute 'targetRequiredHints'.
   */
  public static final String ACTION_TABLE_ENTRY_TARGET_REQUIRED_HINTS_ATTR="targetRequiredHints";
  /**
   * Tag 'actionTableEntry', attribute 'targetDisallowedHints'.
   */
  public static final String ACTION_TABLE_ENTRY_TARGET_DISALLOWED_HINTS_ATTR="targetDisallowedHints";
  /**
   * Tag 'action'.
   */
  public static final String ACTION_TAG="action";
  /**
   * Tag 'action', attribute 'skillId'.
   */
  public static final String ACTION_SKILL_ID_ATTR="skillId";
  /**
   * Tag 'action', attribute 'name'.
   */
  public static final String ACTION_SKILL_NAME_ATTR="name";
  /**
   * Tag 'action', attribute 'recovery'.
   */
  public static final String ACTION_RECOVERY_ATTR="recovery";
}
