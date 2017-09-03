package delta.games.lotro.character.reputation;

import java.io.PrintStream;
import java.util.Date;
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
  private HashMap<String,Long> _dates;

  /**
   * Constructor.
   * @param faction Faction.
   */
  public FactionData(Faction faction)
  {
    _faction=faction;
    _dates=new HashMap<String,Long>();
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
   * Add a reputation update event.
   * @param level New reputation level.
   * @param date Event date.
   */
  public void addUpdate(FactionLevel level, long date)
  {
    _dates.put(level.getKey(),Long.valueOf(date));
  }

  /**
   * Get the date for a given level.
   * @param level Level to use.
   * @return A date as a long or <code>null</code>.
   */
  public Long getDateForLevel(FactionLevel level)
  {
    return _dates.get(level.getKey());
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
      Long timestamp=_dates.get(level.getKey());
      if (timestamp!=null)
      {
        Date date=new Date(timestamp.longValue());
        ps.println("\t"+level+": "+date);
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
