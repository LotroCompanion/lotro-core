package delta.games.lotro.common.effects;

import delta.games.lotro.common.stats.StatsProvider;

/**
 * Effects that gives stats.
 * @author DAM
 */
public class StatsEffect implements EffectAspect
{
  // From Mod_Array:
  private StatsProvider _statsProvider;
  /*
    #1: Mod_Entry 
      Combat_Modifier_OutgoingHealing_Percent: 0.1
      Mod_Modified: 268448149 (Combat_Modifier_OutgoingHealing_Percent)
      Mod_Op: 7 (Add)
      => 10% Outgoing Healing?
    #2: Mod_Entry 
      Mod_Modified: 268462693 (Skill_SingleTarget_Healing_CritChance)
      Mod_Op: 7 (Add)
      Skill_SingleTarget_Healing_CritChance: 0.05
      => healing crit + 5%
  */

  /**
   * Get the stats provider.
   * @return the stats provider.
   */
  public StatsProvider getStatsProvider()
  {
    return _statsProvider;
  }

  /**
   * Set the stats provider.
   * @param statsProvider Stats provider.
   */
  public void setStatsProvider(StatsProvider statsProvider)
  {
    _statsProvider=statsProvider;
  }
}
