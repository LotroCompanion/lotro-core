package delta.games.lotro.character.reputation;

import java.io.PrintStream;
import java.util.HashMap;

import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;

/**
 * Status of a single faction on a single toon.
 * @author DAM
 */
public class FactionStatus
{
  private Faction _faction;
  /**
   * Current level:
   * <ul>
   * <li>null: virgin faction (never ever got a point for it),
   * <li>not null: indicates the current faction level.
   * </ul>
   */
  private FactionLevel _currentLevel;
  private HashMap<Integer,FactionLevelStatus> _statusByLevel;

  /**
   * Constructor.
   * @param faction Faction.
   */
  public FactionStatus(Faction faction)
  {
    _faction=faction;
    _currentLevel=null;
    _statusByLevel=new HashMap<Integer,FactionLevelStatus>();
  }

  /**
   * Copy constructor.
   * @param source Source faction status.
   */
  public FactionStatus(FactionStatus source)
  {
    _statusByLevel=new HashMap<Integer,FactionLevelStatus>();
    set(source);
  }

  /**
   * Set contents from the given data.
   * @param source Source data to copy.
   */
  public void set(FactionStatus source)
  {
    _faction=source._faction;
    _currentLevel=source._currentLevel;
    _statusByLevel.clear();
    for(FactionLevelStatus status : source._statusByLevel.values())
    {
      FactionLevelStatus newStatus=new FactionLevelStatus(status);
      Integer key=Integer.valueOf(newStatus.getLevel().getTier());
      _statusByLevel.put(key,newStatus);
    }
  }

  /**
   * Get the managed faction.
   * @return the managed faction.
   */
  public Faction getFaction()
  {
    return _faction;
  }

  /**
   * Get the status for a given level.
   * @param level Level to use.
   * @return A status.
   */
  public FactionLevelStatus getStatusForLevel(FactionLevel level)
  {
    Integer key=Integer.valueOf(level.getTier());
    FactionLevelStatus ret=_statusByLevel.get(key);
    if (ret==null)
    {
      ret=new FactionLevelStatus(level);
      _statusByLevel.put(key,ret);
    }
    return ret;
  }

  /**
   * Get the current faction level.
   * @return the current faction level.
   */
  public FactionLevel getFactionLevel()
  {
    return _currentLevel;
  }

  /**
   * Set the current faction level.
   * @param level Level to set.
   */
  public void setFactionLevel(FactionLevel level)
  {
    _currentLevel=level;
    setCompletionStatus(_currentLevel,true);
  }

  /**
   * Compute current level from completion state of each level.
   */
  public void updateCurrentLevel()
  {
    FactionLevel currentLevel=null;
    int initialTier=_faction.getInitialLevel().getTier();
    for(FactionLevel level : _faction.getLevels())
    {
      FactionLevelStatus levelStatus=getStatusForLevel(level);
      if (levelStatus.isCompleted())
      {
        if (level.getTier()>=initialTier)
        {
          currentLevel=level;
        }
        else if (level.getTier()<initialTier)
        {
          currentLevel=level;
          break;
        }
      }
    }
    setFactionLevel(currentLevel);
  }

  /**
   * Update a completion status.
   * @param targetedLevel Targeted level.
   * @param completed New completion status.
   */
  public void setCompletionStatus(FactionLevel targetedLevel, boolean completed)
  {
    {
      int initialTier=_faction.getInitialLevel().getTier();
      int targetedTier=targetedLevel.getTier();
      // Update the targeted level
      updateCompletionStatus(targetedLevel,completed);
      if (completed)
      {
        if (targetedTier>initialTier)
        {
          // Set a level above initial tier to completed...
          // - set levels above initial tier and below the targeted level to 'completed'
          for(FactionLevel level : _faction.getLevels())
          {
            if ((level.getTier()>initialTier) && (level.getTier()<targetedTier))
            {
              updateCompletionStatus(level,true);
            }
            // Set levels above the targeted level to 'not completed' (keep dates/XP)
            if (level.getTier()>targetedTier)
            {
              FactionLevelStatus levelStatus=getStatusForLevel(level);
              levelStatus.setCompleted(false);
            }
          }
          // - set levels below initial level to 'not completed'
          for(FactionLevel level : _faction.getLevels())
          {
            if (level.getTier()<initialTier)
            {
              updateCompletionStatus(level,false);
            }
          }
        }
        else
        {
          // - set levels between targeted tier and initial level to 'completed'
          for(FactionLevel level : _faction.getLevels())
          {
            if ((level.getTier()>targetedTier) && (level.getTier()<initialTier))
            {
              updateCompletionStatus(level,true);
            }
          }
          // Set all levels above the initial tier to 'not completed'
          for(FactionLevel level : _faction.getLevels())
          {
            if (level.getTier()>initialTier)
            {
              updateCompletionStatus(level,false);
            }
          }
        }
      }
      else
      {
        if (targetedTier>initialTier)
        {
          // Set all levels above the targeted tier to 'not completed'
          for(FactionLevel level : _faction.getLevels())
          {
            if (level.getTier()>targetedTier)
            {
              updateCompletionStatus(level,false);
            }
          }
        }
        else
        {
          // Set all levels below the targeted tier to 'not completed'
          for(FactionLevel level : _faction.getLevels())
          {
            if (level.getTier()<targetedTier)
            {
              updateCompletionStatus(level,false);
            }
          }
        }
      }
    }
  }

  private void updateCompletionStatus(FactionLevel level, boolean completed)
  {
    FactionLevelStatus levelStatus=getStatusForLevel(level);
    levelStatus.setCompleted(completed);
    if (completed)
    {
      levelStatus.setAcquiredXP(level.getRequiredXp());
    }
    else
    {
      levelStatus.setCompletionDate(0);
      levelStatus.setAcquiredXP(0);
    }
  }

  /**
   * Reset data.
   */
  public void reset()
  {
    _statusByLevel.clear();
    _currentLevel=null;
  }

  /**
   * Initialize faction at a given date.
   * @param date Date to set.
   */
  public void init(long date)
  {
    reset();
    FactionLevel initialLevel=_faction.getInitialLevel();
    FactionLevelStatus status=getStatusForLevel(initialLevel);
    status.setCompleted(date);
    _currentLevel=initialLevel;
  }

  /**
   * Dump the contents of this object to the given stream.
   * @param ps Output stream to use.
   */
  public void dump(PrintStream ps)
  {
    String factionName=_faction.getName();
    ps.println("Reputation history for faction ["+factionName+"]:");
    ps.println("\tLevel: "+_currentLevel);

    FactionLevel[] levels=_faction.getLevels();
    for(FactionLevel level : levels)
    {
      Integer key=Integer.valueOf(level.getTier());
      FactionLevelStatus status=_statusByLevel.get(key);
      if (status!=null)
      {
        ps.println("\t"+status);
      }
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    String factionName=_faction.getName();
    sb.append(factionName);
    sb.append(": ");
    sb.append(_currentLevel);
    return sb.toString();
  }
}
