package delta.games.lotro.character.status.housing.io.xml;

/**
 * Constants for tags and attribute names used in the
 * XML persistence of housing status data.
 * @author DAM
 */
public class HousingStatusXMLConstants
{
  /**
   * Tag 'accountHouses'.
   */
  public static final String ACCOUNT_HOUSES_TAG="accountHouses";
  /**
   * Tag 'classicHouse'.
   */
  public static final String CLASSIC_HOUSE_TAG="classicHouse";
  /**
   * Tag 'premiumHouse'.
   */
  public static final String PREMIUM_HOUSE_TAG="premiumHouse";
  /**
   * Tag 'classHouse'/'premiumHouse', attribute 'ownerID'.
   */
  public static final String OWNER_ID_ATTR="ownerID";
  /**
   * Tag 'address'.
   */
  public static final String ADDRESS_TAG="address";
  /**
   * Tag 'address', attribute 'neighborHoodID'.
   */
  public static final String ADDRESS_NEIGHBORHOOD_ID_ATTR="neighborHoodID";
  /**
   * Tag 'address', attribute 'neighborhoodName'.
   */
  public static final String ADDRESS_NEIGHBORHOOD_NAME_ATTR="neighborhoodName";
  /**
   * Tag 'address', attribute 'houseID'.
   */
  public static final String ADDRESS_HOUSE_ID_ATTR="houseID";
  /**
   * Tag 'address', attribute 'houseName'.
   */
  public static final String ADDRESS_HOUSE_NAME_ATTR="houseName";

  /**
   * Tag 'house'.
   */
  public static final String HOUSE_TAG="house";
  /**
   * Tag 'house', attribute 'server'.
   */
  public static final String HOUSE_SERVER_ATTR="server";
  /**
   * Tag 'interior'.
   */
  public static final String INTERIOR_TAG="interior";
  /**
   * Tag 'exterior'.
   */
  public static final String EXTERIOR_TAG="exterior";
  /**
   * Tag 'interior/exterior', attribute 'zoneID'.
   */
  public static final String ZONE_ID_ATTR="zoneID";
  /**
   * Tag 'item'.
   */
  public static final String ITEM_TAG="item";
  /**
   * Tag 'item', attribute 'identifier'.
   */
  public static final String ITEM_ID_ATTR="id";
  /**
   * Misc tags, attribute 'name'.
   */
  public static final String NAME_ATTR="name";
  /**
   * Tag 'item', attribute 'hookID'.
   */
  public static final String ITEM_HOOK_ID_ATTR="hookID";
  /**
   * Tag 'item', attribute 'rotationOffset'.
   */
  public static final String ITEM_ROTATION_OFFSET_ATTR="rotationOffset";
  /**
   * Tag 'item', attribute 'hookRotation'.
   */
  public static final String ITEM_HOOK_ROTATION_ATTR="hookRotation";
  /**
   * Tag 'item', attribute 'boundTo'.
   */
  public static final String ITEM_BOUND_TO_ATTR="boundTo";

  /**
   * Tag 'positionOffset'.
   */
  public static final String POSITION_OFFSET_TAG="positionOffset";
  /**
   * Tag 'positionOffset', attribute 'x'.
   */
  public static final String X_ATTR="x";
  /**
   * Tag 'positionOffset', attribute 'y'.
   */
  public static final String Y_ATTR="y";
  /**
   * Tag 'positionOffset', attribute 'z'.
   */
  public static final String Z_ATTR="z";
}
