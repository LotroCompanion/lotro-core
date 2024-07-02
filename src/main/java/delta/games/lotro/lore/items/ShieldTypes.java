package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.List;

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
   * Get shield types.
   * @return a list of shield types.
   */
  public static List<ArmourType> getShieldTypes()
  {
    List<ArmourType> ret=new ArrayList<ArmourType>();
    ret.add(SHIELD);
    ret.add(HEAVY_SHIELD);
    ret.add(WARDEN_SHIELD);
    return ret;
  }
}
