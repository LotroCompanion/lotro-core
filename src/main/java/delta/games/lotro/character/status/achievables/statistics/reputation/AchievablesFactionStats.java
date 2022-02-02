package delta.games.lotro.character.status.achievables.statistics.reputation;

import delta.games.lotro.common.statistics.FactionStats;
import delta.games.lotro.lore.reputation.Faction;

/**
 * Statistics for a single faction, from achievables.
 * @author DAM
 */
public class AchievablesFactionStats extends FactionStats
{
  private int _achievablesCount;
  private int _completionsCount;

  /**
   * Constructor.
   * @param faction Managed faction.
   */
  public AchievablesFactionStats(Faction faction)
  {
    super(faction);
  }

  /**
   * Add a reputation amount for a single achievable.
   * @param completionCount Completion count.
   * @param amountByCompletion Reputation amount by completion.
   */
  public void addCompletions(int completionCount, int amountByCompletion)
  {
    _points+=(amountByCompletion*completionCount);
    _achievablesCount++;
    _completionsCount+=completionCount;
  }

  /**
   * Get the number of unique achievables used to build this reputation.
   * @return A count.
   */
  public int getAchievablesCount()
  {
    return _achievablesCount;
  }

  /**
   * Get the number of completions used to build this reputation.
   * @return A count.
   */
  public int getCompletionsCount()
  {
    return _completionsCount;
  }
}
