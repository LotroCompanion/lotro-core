package delta.games.lotro.character.status.skirmishes;

/**
 * Skirmish stats.
 * @author DAM
 */
public class SkirmishStats
{
  private int _monsterKills;
  private int _lieutenantKills;
  private int _controlPointsTaken;
  private int _encountersCompleted;
  private float _playTime; // seconds
  private int _skirmishesCompleted;
  private int _skirmishesAttempted;
  private float _bestTime; // seconds
  private int _totalMarksEarned;

  /**
   * Constructor.
   */
  public SkirmishStats()
  {
    _monsterKills=0;
    _lieutenantKills=0;
    _controlPointsTaken=0;
    _encountersCompleted=0;
    _playTime=0.0f;
    _skirmishesCompleted=0;
    _skirmishesAttempted=0;
    _bestTime=0.0f;
    _totalMarksEarned=0;
  }

  /**
   * Get the monster kills.
   * @return a count.
   */
  public int getMonsterKills()
  {
    return _monsterKills;
  }

  /**
   * Set the monster kills.
   * @param monsterKills Count to set.
   */
  public void setMonsterKills(int monsterKills)
  {
    _monsterKills=monsterKills;
  }

  /**
   * Get the lieutenant kills.
   * @return a count.
   */
  public int getLieutenantKills()
  {
    return _lieutenantKills;
  }

  /**
   * Set the lieutenant kills.
   * @param lieutenantKills Count to set.
   */
  public void setLieutenantKills(int lieutenantKills)
  {
    _lieutenantKills=lieutenantKills;
  }

  /**
   * Get the number of control points taken.
   * @return a count.
   */
  public int getControlPointsTaken()
  {
    return _controlPointsTaken;
  }

  /**
   * Set the lieutenant kills.
   * @param lieutenantKills Count to set.
   */
  /**
   * @param controlPointsTaken the controlPointsTaken to set
   */
  public void setControlPointsTaken(int controlPointsTaken)
  {
    _controlPointsTaken=controlPointsTaken;
  }

  /**
   * Get the number of encounters completed.
   * @return a count.
   */
  public int getEncountersCompleted()
  {
    return _encountersCompleted;
  }

  /**
   * Set the number of encounters completed.
   * @param encountersCompleted Count to set.
   */
  public void setEncountersCompleted(int encountersCompleted)
  {
    _encountersCompleted=encountersCompleted;
  }

  /**
   * Get the play time.
   * @return a duration in seconds.
   */
  public float getPlayTime()
  {
    return _playTime;
  }

  /**
   * Set the play time.
   * @param playTime Duration to set (seconds).
   */
  public void setPlayTime(float playTime)
  {
    _playTime=playTime;
  }

  /**
   * Get the number of skirmishes completed.
   * @return a count.
   */
  public int getSkirmishesCompleted()
  {
    return _skirmishesCompleted;
  }

  /**
   * Set the number of skirmishes completed.
   * @param skirmishesCompleted Count to set.
   */
  public void setSkirmishesCompleted(int skirmishesCompleted)
  {
    _skirmishesCompleted=skirmishesCompleted;
  }

  /**
   * Get the number of skirmishes attempted.
   * @return a count.
   */
  public int getSkirmishesAttempted()
  {
    return _skirmishesAttempted;
  }

  /**
   * Set the number of skirmished attempted.
   * @param skirmishesAttempted Count to set.
   */
  public void setSkirmishesAttempted(int skirmishesAttempted)
  {
    _skirmishesAttempted=skirmishesAttempted;
  }

  /**
   * Get the best time to finish this skirmish.
   * @return a duration in seconds.
   */
  public float getBestTime()
  {
    return _bestTime;
  }

  /**
   * Set the best time to finish this skirmish.
   * @param bestTime Duration to set (seconds).
   */
  public void setBestTime(float bestTime)
  {
    _bestTime=bestTime;
  }

  /**
   * Get the total number of marks earned.
   * @return a count.
   */
  public int getTotalMarksEarned()
  {
    return _totalMarksEarned;
  }

  /**
   * Set the total number of marks earned.
   * @param totalMarksEarned Count to set.
   */
  public void setTotalMarksEarned(int totalMarksEarned)
  {
    _totalMarksEarned=totalMarksEarned;
  }

  /**
   * Get the mean number of marks per hour of play time.
   * @return A number of marks per hour.
   */
  public float getMarksPerHour()
  {
    if (_playTime<=0.0)
    {
      return Float.NaN;
    }
    return _totalMarksEarned/(_playTime/3600);
  }

  /**
   * Get the completion ratio.
   * @return a ratio (between 0 and 1).
   */
  public float getCompletionRatio()
  {
    if (_skirmishesAttempted<=0.0)
    {
      return Float.NaN;
    }
    return _skirmishesCompleted/((float)_skirmishesAttempted);
  }

  /**
   * Get the mean number of encounters completed per hour of play time.
   * @return A number of marks per hour.
   */
  public float getEncountersCompletedPerHour()
  {
    if (_playTime<=0.0)
    {
      return Float.NaN;
    }
    return _encountersCompleted/(_playTime/3600);
  }

  /**
   * Get the mean number of lieutenant kills per hour of play time.
   * @return A number of kills per hour.
   */
  public float getLieutenantKillsPerHour()
  {
    if (_playTime<=0.0)
    {
      return Float.NaN;
    }
    return _lieutenantKills/(_playTime/3600);
  }
}
