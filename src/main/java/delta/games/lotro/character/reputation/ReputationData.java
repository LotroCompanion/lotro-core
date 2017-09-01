package delta.games.lotro.character.reputation;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.lore.reputation.Faction;

/**
 * Reputatution status for a single toon.
 * @author DAM
 */
public class ReputationData
{
  private HashMap<Faction,FactionData> _stats;

  /**
   * Constructor.
   */
  public ReputationData()
  {
    _stats=new HashMap<Faction,FactionData>();
  }

  /**
   * Get the reputation status for a given faction.
   * @param faction Targeted faction.
   * @return A reputation status object, or <code>null</code> if the toon does not
   * have the given faction status.
   */
  public FactionData getFactionStat(Faction faction)
  {
    FactionData stat=_stats.get(faction);
    return stat;
  }

  /**
   * Get the reputation status for a given faction (create it if necessary).
   * @param faction Targeted faction.
   * @return A reputation status object.
   */
  public FactionData getOrCreateFactionStat(Faction faction)
  {
    FactionData stat=_stats.get(faction);
    if (stat==null)
    {
      stat=new FactionData(faction);
      _stats.put(faction,stat);
    }
    return stat;
  }

  /**
   * Dump the contents of this object to the given stream.
   * @param ps Output stream to use.
   */
  public void dump(PrintStream ps)
  {
    ps.println("Reputation status:");
    List<Faction> factions=new ArrayList<Faction>(_stats.keySet());
    //Collections.sort(factions);
    for(Faction faction : factions)
    {
      FactionData stat=getFactionStat(faction);
      stat.dump(ps);
    }
  }
}
