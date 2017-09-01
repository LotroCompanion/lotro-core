package delta.games.lotro.character.reputation;

import java.util.HashSet;
import java.util.Set;

import delta.games.lotro.lore.reputation.Faction;

/**
 * Status of a reputation deed.
 * @author DAM
 */
public class ReputationDeedStatus
{
  private String _deedName;
  private Set<Faction> _acquiredFactions;
  private Set<Faction> _missingFactions;

  /**
   * Constructor.
   * @param deedName Name of the managed reputation deed.
   */
  public ReputationDeedStatus(String deedName)
  {
    _deedName=deedName;
    _acquiredFactions=new HashSet<Faction>();
    _missingFactions=new HashSet<Faction>();
  }

  /**
   * Get the name of the managed deed.
   * @return A deed name.
   */
  public String getDeedName()
  {
    return _deedName;
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
