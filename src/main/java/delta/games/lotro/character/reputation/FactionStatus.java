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
  private FactionLevel _level;
  private HashMap<String,FactionLevelStatus> _statusByLevel;

  /**
   * Constructor.
   * @param faction Faction.
   */
  public FactionStatus(Faction faction)
  {
    _faction=faction;
    _level=null;
    _statusByLevel=new HashMap<String,FactionLevelStatus>();
  }

  /**
   * Copy constructor.
   * @param source Source faction status.
   */
  public FactionStatus(FactionStatus source)
  {
    _statusByLevel=new HashMap<String,FactionLevelStatus>();
    set(source);
  }

  /**
   * Set contents from the given data.
   * @param source Source data to copy.
   */
  public void set(FactionStatus source)
  {
    _faction=source._faction;
    _level=source._level;
    _statusByLevel.clear();
    for(FactionLevelStatus status : source._statusByLevel.values())
    {
      FactionLevelStatus newStatus=new FactionLevelStatus(status);
      _statusByLevel.put(newStatus.getLevel().getKey(),newStatus);
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
   * @return A status as a long or <code>null</code>.
   */
  public FactionLevelStatus getStatusForLevel(FactionLevel level)
  {
    FactionLevelStatus ret=_statusByLevel.get(level.getKey());
    if (ret==null)
    {
      ret=new FactionLevelStatus(level);
      _statusByLevel.put(level.getKey(),ret);
    }
    return ret;
  }

  /**
   * Get the current faction level.
   * @return the current faction level.
   */
  public FactionLevel getFactionLevel()
  {
    return _level;
  }

  /**
   * Set the current faction level.
   * @param level Level to set.
   */
  public void setFactionLevel(FactionLevel level)
  {
    _level=level;
    setCompletionStatus(_level,true);
  }

  /**
   * Compute current level from completion state of each level.
   */
  public void updateCurrentLevel()
  {
    FactionLevel currentLevel=null;
    int initialTier=_faction.getInitialLevel().getValue();
    for(FactionLevel level : _faction.getLevels())
    {
      FactionLevelStatus levelStatus=getStatusForLevel(level);
      if (levelStatus.isCompleted())
      {
        if (level.getValue()>=initialTier)
        {
          currentLevel=level;
        }
        else if (level.getValue()<initialTier)
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
    FactionLevelStatus levelStatus=getStatusForLevel(targetedLevel);
    boolean currentCompletionStatus=levelStatus.isCompleted();
    if (currentCompletionStatus!=completed)
    {
      int initialTier=_faction.getInitialLevel().getValue();
      int targetedTier=targetedLevel.getValue();
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
            if ((level.getValue()>initialTier) && (level.getValue()<targetedTier))
            {
              updateCompletionStatus(level,true);
            }
          }
          // - set levels below initial level to 'not completed'
          for(FactionLevel level : _faction.getLevels())
          {
            if (level.getValue()<initialTier)
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
            if ((level.getValue()>targetedTier) && (level.getValue()<initialTier))
            {
              updateCompletionStatus(level,true);
            }
          }
          // Set all levels above the initial tier to 'not completed'
          for(FactionLevel level : _faction.getLevels())
          {
            if (level.getValue()>initialTier)
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
            if (level.getValue()>targetedTier)
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
            if (level.getValue()<targetedTier)
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
    _level=null;
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
    _level=initialLevel;
  }

  /**
   * Dump the contents of this object to the given stream.
   * @param ps Output stream to use.
   */
  public void dump(PrintStream ps)
  {
    String factionName=_faction.getName();
    ps.println("Reputation history for faction ["+factionName+"]:");
    ps.println("\tLevel: "+_level);

    FactionLevel[] levels=_faction.getLevels();
    for(FactionLevel level : levels)
    {
      FactionLevelStatus status=_statusByLevel.get(level.getKey());
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
    sb.append(_level);
    return sb.toString();
  }
}
