package delta.games.lotro.character.storage.statistics.reputation;

import delta.games.lotro.common.statistics.ReputationStats;
import delta.games.lotro.lore.reputation.Faction;

/**
 * Reputation statistics from storage.
 * @author DAM
 */
public class StorageReputationStats extends ReputationStats<StorageFactionStats>
{
  @Override
  public StorageFactionStats buildFactionStats(Faction faction)
  {
    return new StorageFactionStats(faction);
  }
}
