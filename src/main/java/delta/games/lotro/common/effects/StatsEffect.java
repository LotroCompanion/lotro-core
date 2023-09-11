package delta.games.lotro.common.effects;

import delta.games.lotro.common.stats.StatsProvider;

/**
 * @author dm
 */
public class StatsEffect implements EffectAspect
{
  // From Effect_Duration_ConstantInterval:
  private Float _duration;
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
   * Get the effect duration.
   * @return a duration (seconds) or <code>null</code>.
   */
  public Float getDuration()
  {
    return _duration;
  }

  /**
   * Set the effect duration.
   * @param duration Duration to set (seconds) (may be <code>null</code>).
   */
  public void setDuration(Float duration)
  {
    _duration=duration;
  }

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
