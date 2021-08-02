package delta.games.lotro.character.status.reputation;

import java.util.HashSet;
import java.util.Set;

import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.ReputationDeed;

/**
 * Status of a reputation deed.
 * @author DAM
 */
public class ReputationDeedStatus
{
  private ReputationDeed _deed;
  private Set<Faction> _acquiredFactions;
  private Set<Faction> _missingFactions;

  /**
   * Constructor.
   * @param deed Managed reputation deed.
   */
  public ReputationDeedStatus(ReputationDeed deed)
  {
    _deed=deed;
    _acquiredFactions=new HashSet<Faction>();
    _missingFactions=new HashSet<Faction>();
  }

  /**
   * Get the the managed deed.
   * @return A deed .
   */
  public ReputationDeed getDeed()
  {
    return _deed;
  }

  /**
   * Get the name of the managed deed.
   * @return A deed name.
   */
  public String getDeedName()
  {
    return _deed.getName();
  }

  /**
   * Clear.
   */
  public void clear()
  {
    _acquiredFactions.clear();
    _missingFactions.clear();
  }

  /**
   * Get the number of acquired reputations.
   * @return A count.
   */
  public int getAcquiredCount()
  {
    return _acquiredFactions.size();
  }

  /**
   * Get the total number of reputations involved in the managed deed.
   * @return A count.
   */
  public int getTotalCount()
  {
    return _missingFactions.size()+_acquiredFactions.size();
  }

  /**
   * Indicates if this deed is done or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isDone()
  {
    return _missingFactions.size()==0;
  }

  /**
   * Set the status for a faction.
   * @param faction Targeted faction.
   * @param acquired Indicates if it is acquired or not.
   */
  public void setFactionStatus(Faction faction, boolean acquired)
  {
    if (acquired)
    {
      _acquiredFactions.add(faction);
    }
    else
    {
      _missingFactions.add(faction);
    }
  }
}
