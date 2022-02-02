package delta.games.lotro.character.status.achievables.statistics.reputation;

import delta.games.lotro.common.statistics.ReputationStats;
import delta.games.lotro.lore.reputation.Faction;

/**
 * Reputation statistics from achievables.
 * @author DAM
 */
public class AchievablesReputationStats extends ReputationStats<AchievablesFactionStats>
{
  @Override
  public AchievablesFactionStats buildFactionStats(Faction faction)
  {
    return new AchievablesFactionStats(faction);
  }
}
