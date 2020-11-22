package delta.games.lotro.utils.dat;

import org.apache.log4j.Logger;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsRegistry;

/**
 * Utility methods related to stats from DAT files or network.
 * @author DAM
 */
public class DatStatUtils
{
  private static final Logger LOGGER=Logger.getLogger(DatStatUtils.class);

  /**
   * Get a stat description from a property.
   * @param propertyId Property identifier.
   * @param propertyName Property name. 
   * @return A stat description.
   */
  public static StatDescription getStatDescription(int propertyId, String propertyName)
  {
    StatDescription ret=null;
    StatsRegistry registry=StatsRegistry.getInstance();

    String fixedStatKey=fixStat(propertyName);
    if (fixedStatKey!=null)
    {
      ret=registry.getByKey(fixedStatKey);
    }
    else
    {
      if (propertyId==0)
      {
        return null;
      }
      ret=registry.getById(propertyId);
    }
    if (ret==null)
    {
      LOGGER.warn("Stat not found: "+propertyName);
    }
    return ret;
  }

  private static String fixStat(String key)
  {
    if ("Combat_Agent_Armor_Value_Float".equals(key)) return "ARMOUR";
    if ("Combat_MitigationPercentage_Common".equals(key)) return "PHYSICAL_MITIGATION_PERCENTAGE";
    if ("Combat_Class_PhysicalMastery_Unified".equals(key)) return "PHYSICAL_MASTERY";
    if ("Combat_Class_TacticalMastery_Unified".equals(key)) return "TACTICAL_MASTERY";
    return null;
  }
}
