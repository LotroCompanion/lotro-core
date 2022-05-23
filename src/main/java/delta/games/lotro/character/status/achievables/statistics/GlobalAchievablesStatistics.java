package delta.games.lotro.character.status.achievables.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.misc.IntegerHolder;
import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.status.achievables.AchievableElementState;
import delta.games.lotro.character.status.achievables.AchievableStatus;
import delta.games.lotro.character.status.achievables.AchievablesStatusManager;
import delta.games.lotro.lore.quests.Achievable;

/**
 * Gather statistics about a collection of achievables for a single character.
 * @author DAM
 */
public class GlobalAchievablesStatistics
{
  private Map<AchievableElementState,IntegerHolder> _countByState;
  private int _completionsCount;
  private int _total;
  private AchievablesStatistics _acquired;
  private AchievablesStatistics _toGet;

  /**
   * Constructor.
   */
  public GlobalAchievablesStatistics()
  {
    _countByState=new HashMap<AchievableElementState,IntegerHolder>();
    for(AchievableElementState state : AchievableElementState.values())
    {
      _countByState.put(state,new IntegerHolder());
    }
    _acquired=new AchievablesStatistics();
    _toGet=new AchievablesStatistics();
    reset();
  }

  /**
   * Get the statistics about acquired elements.
   * @return the statistics about acquired elements.
   */
  public AchievablesStatistics getAcquiredStats()
  {
    return _acquired;
  }

  /**
   * Get the statistics about elements to get.
   * @return the statistics about elements to get.
   */
  public AchievablesStatistics getToGetStats()
  {
    return _toGet;
  }

  /**
   * Reset contents.
   */
  public void reset()
  {
    for(IntegerHolder count : _countByState.values())
    {
      count.setInt(0);
    }
    _completionsCount=0;
    _total=0;
    _acquired.reset();
    _toGet.reset();
  }

  /**
   * Compute statistics using the given status and achievables.
   * @param status Achievables status.
   * @param achievables Achievables to use.
   */
  public void useAchievables(AchievablesStatusManager status, List<? extends Achievable> achievables)
  {
    reset();
    for(Achievable achievable : achievables)
    {
      AchievableStatus achievableStatus=status.get(achievable,false);
      if (achievableStatus!=null)
      {
        useAchievable(achievableStatus,achievable);
      }
      _total++;
    }
    _acquired.endStatsComputation();
    _toGet.endStatsComputation();
  }

  private void useAchievable(AchievableStatus status, Achievable achievable)
  {
    // Update count by state
    AchievableElementState state=status.getState();
    IntegerHolder counter=_countByState.get(state);
    if (counter!=null)
    {
      counter.increment();
    }
    // Completions count
    int completionCountInt=status.getActualCompletionCount();
    _completionsCount+=completionCountInt;
    if (completionCountInt>0)
    {
      // Delegated stats
      _acquired.useAchievable(status,achievable,completionCountInt);
    }
    int todoCount=status.getToDoCompletionCount();
    if (todoCount>0)
    {
      _toGet.useAchievable(status,achievable,todoCount);
    }
  }

  /**
   * Get the number of achievables in the given state.
   * @param state State to use.
   * @return A number of achievables.
   */
  public int getCountForState(AchievableElementState state)
  {
    return _countByState.get(state).getInt();
  }

  /**
   * Get the completions count.
   * @return A number of achievable completions (including repeatables).
   */
  public int getCompletionsCount()
  {
    return _completionsCount;
  }

  /**
   * Get the total number of achievables.
   * @return A number of achievables.
   */
  public int getTotalCount()
  {
    return _total;
  }

  /**
   * Dump results.
   * @return a displayable string.
   */
  public String dumpResults()
  {
    StringBuilder sb=new StringBuilder();
    for(AchievableElementState state : AchievableElementState.values())
    {
      sb.append(state.name()).append(": ").append(getCountForState(state)).append(" / ").append(_total).append(EndOfLine.NATIVE_EOL);
    }
    sb.append("*** Acquired:").append(EndOfLine.NATIVE_EOL);
    sb.append(_acquired).append(EndOfLine.NATIVE_EOL);
    sb.append("*** To Get:").append(EndOfLine.NATIVE_EOL);
    sb.append(_toGet).append(EndOfLine.NATIVE_EOL);
    String display=sb.toString().trim();
    return display;
  }
}
