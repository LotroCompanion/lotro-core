package delta.games.lotro.lore.items;

/**
 * Well-known armour types.
 * @author DAM
 */
public class ArmourTypes
{
  /**
   * Heavy.
   */
  public static final ArmourType HEAVY=ArmourType.getArmourTypeByKey("HEAVY");
  /**
   * Medium.
   */
  public static final ArmourType MEDIUM=ArmourType.getArmourTypeByKey("MEDIUM");
  /**
   * Light.
   */
  public static final ArmourType LIGHT=ArmourType.getArmourTypeByKey("LIGHT");

  /**
   * Ordered non-shield armour types.
   */
  public static final ArmourType[] ARMOUR_TYPES={
      LIGHT, MEDIUM, HEAVY,
  };
}
