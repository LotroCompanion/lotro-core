package delta.games.lotro.character.status.traits.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Status of available traits.
 * @author DAM
 */
public class TraitsStatus
{
  private Set<Integer> _availableTraits;

  /**
   * Constructor.
   */
  public TraitsStatus()
  {
    _availableTraits=new HashSet<Integer>();
  }

  /**
   * Get the available traits.
   * @return A sorted list of trait identifiers.
   */
  public List<Integer> getTraitIDs()
  {
    List<Integer> ret=new ArrayList<Integer>(_availableTraits);
    Collections.sort(ret);
    return ret;
  }

  /**
   * Add a trait ID.
   * @param traitID Identifier of the trait to add.
   */
  public void addTraitID(int traitID)
  {
    _availableTraits.add(Integer.valueOf(traitID));
  }

  /**
   * Indicates if the given trait is available or not.
   * @param traitID Identifier of the trait to test.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isAvailable(int traitID)
  {
    return _availableTraits.contains(Integer.valueOf(traitID));
  }
}
