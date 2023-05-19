package delta.games.lotro.lore.items;

/**
 * Well-known shield types.
 * @author DAM
 */
public class ShieldTypes
{
  /**
   * Warden's Shield.
   */
  public static final ArmourType WARDEN_SHIELD=ArmourType.getArmourTypeByKey("WARDEN_SHIELD");
  /**
   * Heavy Shield.
   */
  public static final ArmourType HEAVY_SHIELD=ArmourType.getArmourTypeByKey("HEAVY_SHIELD");
  /**
   * Shield.
   */
  public static final ArmourType SHIELD=ArmourType.getArmourTypeByKey("SHIELD");

  /**
   * Ordered shield types.
   */
  public static final ArmourType[] SHIELD_TYPES = {
      SHIELD, HEAVY_SHIELD, WARDEN_SHIELD
  };
}
