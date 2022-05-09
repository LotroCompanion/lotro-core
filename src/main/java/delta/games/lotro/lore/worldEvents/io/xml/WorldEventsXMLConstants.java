package delta.games.lotro.lore.worldEvents.io.xml;

/**
 * Constants for tags and attribute names used in the
 * world events XML files.
 * @author DAM
 */
public class WorldEventsXMLConstants
{
  /**
   * Tag 'worldEvents'.
   */
  public static final String WORLD_EVENTS_TAG="worldEvents";
  /**
   * Tag 'booleanWorldEvent'.
   */
  public static final String BOOLEAN_WORLD_EVENT_TAG="booleanWorldEvent";
  /**
   * Tag 'booleanWorldEvent', attribute 'default'.
   */
  public static final String WORLD_EVENT_BOOLEAN_DEFAULT_ATTR="default";
  /**
   * Tag 'integerWorldEvent'.
   */
  public static final String INTEGER_WORLD_EVENT_TAG="integerWorldEvent";
  /**
   * Tag 'integerWorldEvent', attribute 'default'.
   */
  public static final String WORLD_EVENT_INTEGER_DEFAULT_ATTR="default";
  /**
   * Tag 'integerWorldEvent', attribute 'min'.
   */
  public static final String WORLD_EVENT_INTEGER_MIN_ATTR="min";
  /**
   * Tag 'integerWorldEvent'/'countedWorldEvent', attribute 'max'.
   */
  public static final String WORLD_EVENT_INTEGER_MAX_ATTR="max";
  /**
   * Tag 'conditionWorldEvent'.
   */
  public static final String CONDITION_WORLD_EVENT_TAG="conditionWorldEvent";
  /**
   * Tag 'countedWorldEvent'.
   */
  public static final String COUNTED_WORLD_EVENT_TAG="countedWorldEvent";
  /**
   * Tag 'worldEvent', attribute 'id'.
   */
  public static final String WORLD_EVENT_ID_ATTR="id";
  /**
   * Tag 'worldEvent', attribute 'propertyId'.
   */
  public static final String WORLD_EVENT_PROPERTY_ID_ATTR="propertyId";
  /**
   * Tag 'worldEvent', attribute 'propertyName'.
   */
  public static final String WORLD_EVENT_PROPERTY_NAME_ATTR="propertyName";
  /**
   * Tag 'worldEvent', attribute 'description'.
   */
  public static final String WORLD_EVENT_DESCRIPTION_ATTR="description";
  /**
   * Tag 'worldEvent', attribute 'progress'.
   */
  public static final String WORLD_EVENT_PROGRESS_ATTR="progress";
}
