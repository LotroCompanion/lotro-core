package delta.games.lotro.lore.crafting.io.xml;

/**
 * Constants for tags and attribute names used in the
 * XML persistence of crafting data.
 * @author DAM
 */
public class CraftingXMLConstants
{
  /**
   * Tag 'crafting'.
   */
  public static final String CRAFTING_TAG="crafting";
  /**
   * Tag 'vocation'.
   */
  public static final String VOCATION_TAG="vocation";
  /**
   * Tag 'vocation', attribute 'identifier'.
   */
  public static final String VOCATION_IDENTIFIER_ATTR="identifier";
  /**
   * Tag 'vocation', attribute 'key'.
   */
  public static final String VOCATION_KEY_ATTR="key";
  /**
   * Tag 'vocation', attribute 'name'.
   */
  public static final String VOCATION_NAME_ATTR="name";
  /**
   * Tag 'vocation', attribute 'description'.
   */
  public static final String VOCATION_DESCRIPTION_ATTR="description";

  /**
   * Tag 'profession'.
   */
  public static final String PROFESSION_TAG="profession";
  /**
   * Tag 'profession', attribute 'identifier'.
   */
  public static final String PROFESSION_IDENTIFIER_ATTR="identifier";
  /**
   * Tag 'profession', attribute 'key'.
   */
  public static final String PROFESSION_KEY_ATTR="key";
  /**
   * Tag 'profession', attribute 'name'.
   */
  public static final String PROFESSION_NAME_ATTR="name";
  /**
   * Tag 'profession', attribute 'description'.
   */
  public static final String PROFESSION_DESCRIPTION_ATTR="description";
  /**
   * Tag 'profession', attribute 'hasGuild'.
   */
  public static final String PROFESSION_HAS_GUILD_ATTR="hasGuild";
  /**
   * Tag 'profession', attribute 'enabledProperty'.
   */
  public static final String PROFESSION_ENABLED_PROPERTY_ATTR="enabledProperty";
  /**
   * Tag 'profession', attribute 'masteryLevelProperty'.
   */
  public static final String PROFESSION_MASTERY_LEVEL_PROPERTY_ATTR="masteryLevelProperty";
  /**
   * Tag 'profession', attribute 'masteryXpProperty'.
   */
  public static final String PROFESSION_MASTERY_XP_PROPERTY_ATTR="masteryXpProperty";
  /**
   * Tag 'profession', attribute 'proficiencyLevelProperty'.
   */
  public static final String PROFESSION_PROFICIENCY_LEVEL_PROPERTY_ATTR="proficiencyLevelProperty";
  /**
   * Tag 'profession', attribute 'proficiencyXpProperty'.
   */
  public static final String PROFESSION_PROFICIENCY_XP_PROPERTY_ATTR="proficiencyXpProperty";

  /**
   * Tag 'professionTier'.
   */
  public static final String PROFESSION_TIER_TAG="professionTier";
  /**
   * Tag 'professionTier', attribute 'tier'.
   */
  public static final String PROFESSION_TIER_ATTR="identifier";
  /**
   * Tag 'professionTier', attribute 'name'.
   */
  public static final String PROFESSION_TIER_NAME_ATTR="name";
  /**
   * Tag 'professionTier', attribute 'icon'.
   */
  public static final String PROFESSION_TIER_ICON_ATTR="icon";
  /**
   * Tag 'recipe'.
   */
  public static final String RECIPE_TAG="recipe";
  /**
   * Tag 'recipe', attribute 'id'.
   */
  public static final String RECIPE_ID_ATTR="id";

  /**
   * Tag 'proficiency'.
   */
  public static final String PROFICIENCY_TAG="proficiency";
  /**
   * Tag 'mastery'.
   */
  public static final String MASTERY_TAG="mastery";
  /**
   * Tag 'proficiency'/'mastery', attribute 'xp'.
   */
  public static final String STEP_XP_NAME_ATTR="xp";
  /**
   * Tag 'proficiency'/'mastery', attribute 'titleId'.
   */
  public static final String STEP_TITLE_ID_ATTR="titleId";
}
