package delta.games.lotro.character.classes.traitTree.io.xml;

/**
 * Constants for tags and attribute names used in the
 * XML persistence of trait trees.
 * @author DAM
 */
public class TraitTreeXMLConstants
{
  /**
   * Tag 'traitTrees'.
   */
  public static final String TRAIT_TREES_TAG="traitTrees";
  /**
   * Tag 'traitTree'.
   */
  public static final String TRAIT_TREE_TAG="traitTree";
  /**
   * Tag 'traitTree', attribute 'id'.
   */
  public static final String TRAIT_TREE_ID_ATTR="id";
  /**
   * Tag 'traitTree', attribute 'code'.
   */
  public static final String TRAIT_TREE_CODE_ATTR="code";
  /**
   * Tag 'traitTree', attribute 'key'.
   */
  public static final String TRAIT_TREE_KEY_ATTR="key";
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
   * Tag 'traitTreeBranch', attribute 'description'.
   */
  public static final String TRAIT_TREE_BRANCH_DESCRIPTION_ATTR="description";
  /**
   * Tag 'traitTreeBranch', attribute 'mainTraitId'.
   */
  public static final String TRAIT_TREE_BRANCH_TRAIT_ATTR="mainTraitId";
  /**
   * Tag 'traitTreeBranch', attribute 'enabled'.
   */
  public static final String TRAIT_TREE_BRANCH_ENABLED_ATTR="enabled";
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
   * Tag 'step', attribute 'traitName'.
   */
  public static final String STEP_TRAIT_NAME_ATTR="traitName";
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
   * Tag 'cell', attribute 'traitId'.
   */
  public static final String CELL_TRAIT_NAME_ATTR="traitName";
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
}
