package delta.games.lotro.character.reputation;

import java.io.PrintStream;
import java.util.HashMap;

import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;

/**
 * Statistics about a single faction on a single toon.
 * @author DAM
 */
public class FactionData
{
  private Faction _faction;
  private FactionLevel _level;
  private HashMap<String,FactionLevelStatus> _statusByLevel;

  /**
   * Constructor.
   * @param faction Faction.
   */
  public FactionData(Faction faction)
  {
    _faction=faction;
    _level=null;
    _statusByLevel=new HashMap<String,FactionLevelStatus>();
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
    updateCompletion();
  }

  private void updateCompletion()
  {
    FactionLevel[] levels=_faction.getLevels();
    for(FactionLevel level : levels)
    {
      FactionLevelStatus status=getStatusForLevel(level);
      if ((_level==null) || (level.getValue()>_level.getValue()))
      {
        status.setCompleted(false);
        if ((_level==null) || (level.getValue()>_level.getValue()+1))
        {
          status.setAcquiredXP(0);
        }
      }
      else
      {
        status.setCompleted(true);
        status.setAcquiredXP(level.getRequiredXp());
      }
    }
  }

  /**
   * Compute current level from completion state of each level.
   */
  public void updateCurrentLevel()
  {
    FactionLevel currentLevel=null;
    for(FactionLevel level : _faction.getLevels())
    {
      FactionLevelStatus levelStatus=getStatusForLevel(level);
      if (levelStatus.isCompleted())
      {
        currentLevel=level;
      }
      else
      {
        break;
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
      if (completed)
      {
        // Update the targeted level
        levelStatus.setCompleted(true);
        levelStatus.setAcquiredXP(targetedLevel.getRequiredXp());
        // Set previous levels to 'completed'
        for(FactionLevel level : _faction.getLevels())
        {
          if (level==targetedLevel)
          {
            break;
          }
          setCompletionStatus(level,completed);
        }
      }
      else
      {
        // Update the targeted level
        levelStatus.setCompleted(false);
        levelStatus.setCompletionDate(0);
        // Set higher levels to 'not completed'
        for(FactionLevel level : _faction.getLevels())
        {
          if (level.getValue()>targetedLevel.getValue())
          {
            setCompletionStatus(level,false);
          }
        }
      }
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
