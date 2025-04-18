package delta.games.lotro.character.status.collections.birds;

import java.util.HashSet;
import java.util.Set;

/**
 * Storage for known-birds status data for a single character.
 * @author DAM
 */
public class BirdsStatusManager
{
  private Set<Integer> _knownBirds;

  /**
   * Constructor.
   */
  public BirdsStatusManager()
  {
    _knownBirds=new HashSet<Integer>();
  }

  /**
   * Set the given bird as known.
   * @param birdID Bird identifier.
   */
  public void setKnown(int birdID)
  {
    _knownBirds.add(Integer.valueOf(birdID));
  }

  /**
   * Get the status of a bird.
   * @param birdID Bird identifier.
   * @return <code>true</code> if it is known, <code>false</code> otherwise.
   */
  public boolean isKnown(int birdID)
  {
    Integer key=Integer.valueOf(birdID);
    return _knownBirds.contains(key);
  }

  /**
   * Get all managed statuses.
   * @return A list of statuses, ordered by bird type code.
   */
  public Set<Integer> getKnownBirds()
  {
    return new HashSet<Integer>(_knownBirds);
  }
}
