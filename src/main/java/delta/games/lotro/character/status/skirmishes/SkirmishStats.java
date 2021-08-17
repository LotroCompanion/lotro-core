package delta.games.lotro.character.status.skirmishes;

/**
 * Skirmish stats.
 * @author DAM
 */
public class SkirmishStats
{
  private int _monsterKills;
  private int _lieutenantKills;
  private int _bossKills;
  private int _bossResets;
  private int _defendersLost;
  private int _defendersSaved;
  private int _soldiersDeaths;
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
    _bossKills=0;
    _bossResets=0;
    _defendersLost=0;
    _defendersSaved=0;
    _soldiersDeaths=0;
    _controlPointsTaken=0;
    _encountersCompleted=0;
    _playTime=0.0f;
    _skirmishesCompleted=0;
    _skirmishesAttempted=0;
    _bestTime=0.0f;
    _totalMarksEarned=0;
  }

  /**
   * Get the number of monster kills.
   * @return a count.
   */
  public int getMonsterKills()
  {
    return _monsterKills;
  }

  /**
   * Set the number of monster kills.
   * @param monsterKills Count to set.
   */
  public void setMonsterKills(int monsterKills)
  {
    _monsterKills=monsterKills;
  }

  /**
   * Get the number of lieutenant kills.
   * @return a count.
   */
  public int getLieutenantKills()
  {
    return _lieutenantKills;
  }

  /**
   * Set the number of lieutenant kills.
   * @param lieutenantKills Count to set.
   */
  public void setLieutenantKills(int lieutenantKills)
  {
    _lieutenantKills=lieutenantKills;
  }

  /**
   * Get the number of boss kills.
   * @return a count.
   */
  public int getBossKills()
  {
    return _bossKills;
  }

  /**
   * Set the number of boss kills.
   * @param bossKills Count to set.
   */
  public void setBossKills(int bossKills)
  {
    _bossKills=bossKills;
  }

  /**
   * Get the number of boss resets.
   * @return a count.
   */
  public int getBossResets()
  {
    return _bossResets;
  }

  /**
   * Set the number of boss resets.
   * @param bossResets Count to set.
   */
  public void setBossResets(int bossResets)
  {
    _bossResets=bossResets;
  }

  /**
   * Get the number of defenders lost.
   * @return a count.
   */
  public int getDefendersLost()
  {
    return _defendersLost;
  }

  /**
   * Set the number of defenders lost.
   * @param defendersLost Count to set.
   */
  public void setDefendersLost(int defendersLost)
  {
    _defendersLost=defendersLost;
  }

  /**
   * Get the number of defenders saved.
   * @return a count.
   */
  public int getDefendersSaved()
  {
    return _defendersSaved;
  }

  /**
   * Set the number of defenders saved.
   * @param defendersSaved Count to set.
   */
  public void setDefendersSaved(int defendersSaved)
  {
    _defendersSaved=defendersSaved;
  }

  /**
   * Get the soldiers deaths.
   * @return a count.
   */
  public int getSoldiersDeaths()
  {
    return _soldiersDeaths;
  }

  /**
   * Set the number of soldier deaths.
   * @param soldiersDeaths Count to set.
   */
  public void setSoldiersDeaths(int soldiersDeaths)
  {
    _soldiersDeaths=soldiersDeaths;
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
   * Set the number of control points taken.
   * @param controlPointsTaken Count to set.
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
   * Add some stats.
   * @param stats Stats to add.
   */
  public void add(SkirmishStats stats)
  {
    _monsterKills+=stats._monsterKills;
    _lieutenantKills+=stats._lieutenantKills;
    _bossKills+=stats._bossKills;
    _bossResets+=stats._bossResets;
    _defendersLost+=stats._defendersLost;
    _defendersSaved+=stats._defendersSaved;
    _soldiersDeaths+=stats._soldiersDeaths;
    _controlPointsTaken+=stats._controlPointsTaken;
    _encountersCompleted+=stats._encountersCompleted;
    _playTime+=stats._playTime;
    _skirmishesCompleted+=stats._skirmishesCompleted;
    _skirmishesCompleted+=stats._skirmishesCompleted;
    _skirmishesCompleted+=stats._skirmishesCompleted;
    _bestTime+=stats._bestTime;
    _totalMarksEarned+=stats._totalMarksEarned;
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

  /**
   * Indicates if this object is empty or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEmpty()
  {
    if (_monsterKills!=0) return false;
    if (_lieutenantKills!=0) return false;
    if (_bossKills!=0) return false;
    if (_bossResets!=0) return false;
    if (_defendersLost!=0) return false;
    if (_defendersSaved!=0) return false;
    if (_soldiersDeaths!=0) return false;
    if (_controlPointsTaken!=0) return false;
    if (_encountersCompleted!=0) return false;
    if (_playTime>0) return false;
    if (_skirmishesCompleted!=0) return false;
    if (_skirmishesAttempted!=0) return false;
    if (_bestTime>0) return false;
    if (_totalMarksEarned!=0) return false;
    return true;
  }
}
