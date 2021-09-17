package delta.games.lotro.lore.agents.mobs.io.xml;

/**
 * Constants for tags and attribute names used in the
 * mobs XML documents.
 * @author DAM
 */
public class MobsXMLConstants
{
  /**
   * Tag 'mobs'.
   */
  public static final String MOBS_TAG="mobs";
  /**
   * Tag 'mob'.
   */
  public static final String MOB_TAG="mob";
  /**
   * Tag 'mob', attribute 'id'.
   */
  public static final String ID_ATTR="id";
  /**
   * Tag 'mob', attribute 'name'.
   */
  public static final String NAME_ATTR="name";

  /**
   * Tag 'loot'.
   */
  public static final String LOOT_TAG="loot";
  /**
   * Tag 'loot', attribute 'barterTrophyListId'.
   */
  public static final String BARTER_TROPHY_LIST_ID_ATTR="barterTrophyListId";
  /**
   * Tag 'loot', attribute 'reputationTrophyListId'.
   */
  public static final String REPUTATION_TROPHY_LIST_ID_ATTR="reputationTrophyListId";
  /**
   * Tag 'loot', attribute 'treasureListId'.
   */
  public static final String TREASURE_LIST_OVERRIDE_ID_ATTR="treasureListId";
  /**
   * Tag 'loot', attribute 'trophyListId'.
   */
  public static final String TROPHY_LIST_OVERRIDE_ID_ATTR="trophyListId";
  /**
   * Tag 'loot', attribute 'generatesTrophy'.
   */
  public static final String GENERATES_TROPHY_ATTR="generatesTrophy";
  /**
   * Tag 'loot', attribute 'remoteLootable'.
   */
  public static final String REMOTE_LOOTABLE_ATTR="remoteLootable";
}
