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
  private Integer _reputation;
  private HashMap<Integer,FactionLevelStatus> _statusByLevel;

  /**
   * Constructor.
   * @param faction Faction.
   */
  public FactionStatus(Faction faction)
  {
    _faction=faction;
    _currentLevel=null;
    _reputation=null;
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
    _reputation=source._reputation;
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
  }

  /**
   * Get the current reputation amount.
   * @return A reputation amount or <code>null</code> if faction not touched.
   */
  public Integer getReputation()
  {
    return _reputation;
  }

  /**
   * Set the current reputation amount.
   * @param reputation Reputation to set.
   */
  public void setReputation(Integer reputation)
  {
    _reputation=reputation;
  }

  /**
   * Set the reputation amount from the faction level.
   */
  public void setReputationFromFactionLevel()
  {
    if (_currentLevel==null)
    {
      _reputation=null;
    }
    else
    {
      _reputation=Integer.valueOf(_currentLevel.getRequiredReputation());
    }
  }

  /**
   * Indicates if the given level is completed or not.
   * @param level Level to use.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isCompleted(FactionLevel level)
  {
    if (_currentLevel!=null)
    {
      return (level.getTier()<=_currentLevel.getTier());
    }
    return false;
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
    FactionLevel initialLevel=_faction.getLevelByTier(_faction.getInitialTier());
    FactionLevelStatus status=getStatusForLevel(initialLevel);
    status.setCompletionDate(date);
    _currentLevel=initialLevel;
    _reputation=Integer.valueOf(initialLevel.getRequiredReputation());
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
    ps.println("\tReputation: "+_reputation);

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
