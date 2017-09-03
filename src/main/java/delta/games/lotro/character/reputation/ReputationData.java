package delta.games.lotro.character.reputation;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;

/**
 * Reputatution status for a single toon.
 * @author DAM
 */
public class ReputationData
{
  private HashMap<Faction,FactionData> _stats;
  private ReputationDeedsData _deedsStatus;
  private int _acquiredLotroPoints;

  /**
   * Constructor.
   */
  public ReputationData()
  {
    _stats=new HashMap<Faction,FactionData>();
    _deedsStatus=new ReputationDeedsData();
  }

  /**
   * Update the data derivated from reputation data (deeds and LOTRO points count).
   */
  public void update()
  {
    _deedsStatus.update(this);
    ReputationLotroPointsComputer lpComputer=new ReputationLotroPointsComputer();
    _acquiredLotroPoints=lpComputer.compute(this);
  }

  /**
   * Get the status of reputation deeds.
   * @return the status of reputation deeds.
   */
  public ReputationDeedsData getDeedsStatus()
  {
    return _deedsStatus;
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
      stat.setFactionLevel(faction.getInitialLevel());
      _stats.put(faction,stat);
    }
    return stat;
  }

  /**
   * Update current faction level for a given faction.
   * @param faction Faction to use.
   * @param increase Increase or decrease.
   */
  public void updateFaction(Faction faction, boolean increase)
  {
    FactionData factionData=getOrCreateFactionStat(faction);
    FactionLevel currentLevel=factionData.getFactionLevel();
    FactionLevel[] levels=faction.getLevels();
    int index=0;
    for(FactionLevel level : levels)
    {
      if (level.getKey().equals(currentLevel.getKey()))
      {
        int nbLevels=levels.length;
        if (increase)
        {
          if (index+1<nbLevels)
          {
            FactionLevel newLevel=levels[index+1];
            factionData.setFactionLevel(newLevel);
            break;
          }
        }
        else
        {
          if (index>0)
          {
            FactionLevel newLevel=levels[index-1];
            factionData.setFactionLevel(newLevel);
            break;
          }
        }
      }
      index++;
    }
  }

  /**
   * Get the number of acquired LOTRO points.
   * @return A LOTRO points count.
   */
  public int getAcquiredLotroPoints()
  {
    return _acquiredLotroPoints;
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
