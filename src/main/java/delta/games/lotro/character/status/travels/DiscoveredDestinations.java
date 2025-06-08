package delta.games.lotro.character.status.travels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.lore.travels.TravelDestination;

/**
 * Storage for all known destinations for a single character.
 * @author DAM
 */
public class DiscoveredDestinations
{
  private Set<TravelDestination> _status;

  /**
   * Constructor.
   */
  public DiscoveredDestinations()
  {
    _status=new HashSet<TravelDestination>();
  }

  /**
   * Clear data.
   */
  public void clear()
  {
    _status.clear();
  }

  /**
   * Indicates if a destination is known or not.
   * @param destination Destination to test.
   * @return <code>true</code> if it is known, <code>false</code> otherwise.
   */
  public boolean isKnown(TravelDestination destination)
  {
    return _status.contains(destination);
  }

  /**
   * Add a known destination.
   * @param destination Destination to add.
   */
  public void addKnownDestination(TravelDestination destination)
  {
    _status.add(destination);
  }

  /**
   * Get all known destinations.
   * @return A list of destination, ordered by ID.
   */
  public List<TravelDestination> getAll()
  {
    List<TravelDestination> ret=new ArrayList<TravelDestination>();
    ret.addAll(_status);
    Collections.sort(ret,new IdentifiableComparator<TravelDestination>());
    return ret;
  }
}
