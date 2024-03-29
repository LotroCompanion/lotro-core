package delta.games.lotro.lore.items.legendary.io.xml;

/**
 * Constants for tags and attribute names used in the
 * XML persistence of legacies.
 * @author DAM
 */
public class LegacyXMLConstants
{
  /**
   * Tag 'legacies'.
   */
  public static final String LEGACIES_TAG="legacies";
  // Shared tags/attributes
  /**
   * Legacy tags, attribute 'type'.
   */
  public static final String LEGACY_TYPE_ATTR="type";
  /**
   * Legacy tags, attribute 'iconId'.
   */
  public static final String LEGACY_ICON_ID_ATTR="iconId";
  /**
   * Tag 'filter'.
   */
  public static final String FILTER_TAG="filter";
  /**
   * Tag 'filter', attribute 'characterClass'.
   */
  public static final String FILTER_CHARACTER_CLASS_ATTR="characterClass";
  /**
   * Tag 'filter', attribute 'slot'.
   */
  public static final String FILTER_SLOT_ATTR="slot";

  /**
   * Tag 'legacy' (imbued).
   */
  public static final String LEGACY_TAG="legacy";
  /**
   * Tag 'legacy', attribute 'identifier'.
   */
  public static final String LEGACY_IDENTIFIER_ATTR="identifier";
  /**
   * Tag 'legacy', attribute 'maxInitialLevel'.
   */
  public static final String LEGACY_MAX_INITIAL_LEVEL_ATTR="maxInitialLevel";
  /**
   * Tag 'legacy', attribute 'maxLevel'.
   */
  public static final String LEGACY_MAX_LEVEL_ATTR="maxLevel";
  /**
   * Tag 'legacy', attribute 'effectId'.
   */
  public static final String LEGACY_EFFECT_ID_ATTR="effectId";
  /**
   * Tag 'allowedWeaponType'.
   */
  public static final String ALLOWED_WEAPON_TYPE_TAG="allowedWeaponType";
  /**
   * Tag 'allowedWeaponType', attribute 'type'.
   */
  public static final String WEAPON_TYPE_ATTR="type";

  /**
   * Tag 'defaultNonImbuedLegacy'.
   */
  public static final String DEFAULT_NON_IMBUED_LEGACY_TAG="defaultNonImbuedLegacy";
  /**
   * Tag 'tieredNonImbuedLegacy'.
   */
  public static final String TIERED_NON_IMBUED_LEGACY_TAG="tieredNonImbuedLegacy";
  /**
   * Tag 'tieredNonImbuedLegacy', attribute 'stat'.
   */
  public static final String TNIL_STAT_ATTR="stat";
  /**
   * Tag 'tieredNonImbuedLegacy', attribute 'major'.
   */
  public static final String TNIL_MAJOR_ATTR="major";
  /**
   * Tag 'legacyTier'.
   */
  public static final String LEGACY_TIER_TAG="legacyTier";
  /**
   * Tag 'legacyTier', attribute 'tier'.
   */
  public static final String LEGACY_TIER_TIER_ATTR="tier";
  /**
   * Tag 'legacyTier', attribute 'startRank'.
   */
  public static final String LEGACY_TIER_START_RANK_ATTR="startRank";

  /**
   * Non-imbued legacy tags, attribute 'imbuedLegacyId'.
   */
  public static final String IMBUED_LEGACY_ID_ATTR="imbuedLegacyId";
}
