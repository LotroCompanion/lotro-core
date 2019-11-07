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

  // TODO: add professions referenced in a vocation

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

  // TODO: add recipes referenced in a tier
}
