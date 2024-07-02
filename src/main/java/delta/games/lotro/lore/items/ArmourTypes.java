package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.List;

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
   * Get non-shield armour types.
   * @return a list of armour types.
   */
  public static List<ArmourType> getArmourTypes()
  {
    List<ArmourType> ret=new ArrayList<ArmourType>();
    ret.add(LIGHT);
    ret.add(MEDIUM);
    ret.add(HEAVY);
    return ret;
  }
}
