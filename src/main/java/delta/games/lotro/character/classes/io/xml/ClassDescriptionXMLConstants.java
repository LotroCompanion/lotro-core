package delta.games.lotro.character.classes.io.xml;

/**
 * Constants for tags and attribute names used in the
 * XML persistence of class descriptions.
 * @author DAM
 */
public class ClassDescriptionXMLConstants
{
  /**
   * Tag 'classes'.
   */
  public static final String CLASSES_TAG="classes";
  /**
   * Tag 'class'.
   */
  public static final String CLASS_TAG="class";
  /**
   * Tag 'class', attribute 'key'.
   */
  public static final String CLASS_KEY_ATTR="key";
  /**
   * Tag 'class', attribute 'iconId'.
   */
  public static final String CLASS_ICON_ID_ATTR="iconId";
  /**
   * Tag 'class', attribute 'smallIconId'.
   */
  public static final String CLASS_SMALL_ICON_ID_ATTR="smallIconId";
  /**
   * Tag 'class', attribute 'abbreviation'.
   */
  public static final String CLASS_ABBREVIATION_ATTR="abbreviation";
  /**
   * Tag 'class', attribute 'description'.
   */
  public static final String CLASS_DESCRIPTION_ATTR="description";
  /**
   * Tag 'class', attribute 'tacticalDpsStatName'.
   */
  public static final String CLASS_TACTICAL_DPS_STAT_NAME_ATTR="tacticalDpsStatName";
  /**
   * Tag 'classTrait'.
   */
  public static final String CLASS_TRAIT_TAG="classTrait";
  /**
   * Tag 'classTrait', attribute 'minLevel'.
   */
  public static final String CLASS_TRAIT_MIN_LEVEL_ATTR="minLevel";
  /**
   * Tag 'classTrait', attribute 'traitId'.
   */
  public static final String CLASS_TRAIT_ID_ATTR="traitId";
  /**
   * Tag 'traitTree'.
   */
  public static final String TRAIT_TREE_TAG="traitTree";
  /**
   * Tag 'traitTreeBranch'.
   */
  public static final String TRAIT_TREE_BRANCH_TAG="traitTreeBranch";
  /**
   * Tag 'traitTreeBranch', attribute 'code'.
   */
  public static final String TRAIT_TREE_BRANCH_CODE_ATTR="code";
  /**
   * Tag 'traitTreeBranch', attribute 'name'.
   */
  public static final String TRAIT_TREE_BRANCH_NAME_ATTR="name";
  /**
   * Tag 'traitTreeBranch', attribute 'name'.
   */
  public static final String TRAIT_TREE_BRANCH_TRAIT_ATTR="mainTraitId";
  /**
   * Tag 'progression'.
   */
  public static final String PROGRESSION_TAG="progression";
  /**
   * Tag 'step'.
   */
  public static final String STEP_TAG="step";
  /**
   * Tag 'step', attribute 'nbPoints'.
   */
  public static final String STEP_REQUIRED_POINTS_ATTR="nbPoints";
  /**
   * Tag 'step', attribute 'traitId'.
   */
  public static final String STEP_TRAIT_ID_ATTR="traitId";
  /**
   * Tag 'cells'.
   */
  public static final String CELLS_TAG="cells";
  /**
   * Tag 'cell'.
   */
  public static final String CELL_TAG="cell";
  /**
   * Tag 'cell', attribute 'id'.
   */
  public static final String CELL_ID_ATTR="id";
  /**
   * Tag 'cell', attribute 'traitId'.
   */
  public static final String CELL_TRAIT_ID_ATTR="traitId";
  /**
   * Tag 'cellDependency'.
   */
  public static final String CELL_DEPENDENCY_TAG="cellDependency";
  /**
   * Tag 'cellDependency', attribute 'cellId'.
   */
  public static final String CELL_DEPENDENCY_CELL_ID_ATTR="cellId";
  /**
   * Tag 'cellDependency', attribute 'rank'.
   */
  public static final String CELL_DEPENDENCY_RANK_ATTR="rank";
  /**
   * Tag 'classSkill'.
   */
  public static final String CLASS_SKILL_TAG="classSkill";
  /**
   * Tag 'classSkill', attribute 'minLevel'.
   */
  public static final String CLASS_SKILL_MIN_LEVEL_ATTR="minLevel";
  /**
   * Tag 'classSkill', attribute 'skillId'.
   */
  public static final String CLASS_SKILL_ID_ATTR="skillId";
  /**
   * Tag 'classSkill', attribute 'skillName'.
   */
  public static final String CLASS_SKILL_NAME_ATTR="skillName";
  /**
   * Tag 'initialGearElement'.
   */
  public static final String INITIAL_GEAR_ELEMENT_TAG="initialGearElement";
  /**
   * Tag 'initialGearElement', attribute 'itemId'.
   */
  public static final String GEAR_ITEM_ID_ATTR="itemId";
  /**
   * Tag 'initialGearElement', attribute 'race'.
   */
  public static final String GEAR_REQUIRED_RACE_ATTR="race";
  /**
   * Tag 'defaultBuff'.
   */
  public static final String DEFAULT_BUFF_TAG="defaultBuff";
  /**
   * Tag 'defaultBuff', attribute 'id'.
   */
  public static final String DEFAULT_BUFF_ID_ATTR="id";
  /**
   * Tag 'defaultBuff', attribute 'tier'.
   */
  public static final String DEFAULT_BUFF_TIER="tier";
}
