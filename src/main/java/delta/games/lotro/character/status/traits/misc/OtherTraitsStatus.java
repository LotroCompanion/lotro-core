package delta.games.lotro.character.status.traits.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delta.games.lotro.common.enums.TraitNature;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;

/**
 * Status of other traits.
 * @author DAM
 */
public class OtherTraitsStatus
{
  // Set of acquired traits for each trait nature
  private Map<TraitNature,Set<Integer>> _traitsByNature;
  // Trait ranks
  private Map<Integer,Integer> _traitRanks;

  /**
   * Constructor.
   */
  public OtherTraitsStatus()
  {
    _traitsByNature=new HashMap<TraitNature,Set<Integer>>();
    _traitRanks=new HashMap<Integer,Integer>();
  }

  /**
   * Get the slotted traits in the given category.
   * @param nature Trait nature/category.
   * @return An array of slotted trait identifiers (0 means nothing slotted).
   */
  public int[] getAcquiredTraits(TraitNature nature)
  {
    List<Integer> sortedTraitIDs=new ArrayList<Integer>();
    Set<Integer> traitIDs=_traitsByNature.get(nature);
    if (traitIDs!=null)
    {
      sortedTraitIDs.addAll(traitIDs);
      Collections.sort(sortedTraitIDs);
    }
    int[] ret=new int[sortedTraitIDs.size()];
    for(int i=0;i<ret.length;i++)
    {
      ret[i]=sortedTraitIDs.get(i).intValue();
    }
    return ret;
  }

  /**
   * Add a trait for the given category/nature.
   * @param nature Trait nature/category.
   * @param traitID Trait identifier.
   */
  public void addTrait(TraitNature nature, int traitID)
  {
    Set<Integer> storage=_traitsByNature.get(nature);
    if (storage!=null)
    {
      storage=new HashSet<Integer>();
      _traitsByNature.put(nature,storage);
    }
    storage.add(Integer.valueOf(traitID));
  }

  /**
   * Get the managed trait natures.
   * @return A list of trait natures, sorted by enum codes.
   */
  public List<TraitNature> getTraitNatures()
  {
    List<TraitNature> ret=new ArrayList<TraitNature>(_traitsByNature.keySet());
    Collections.sort(ret,new LotroEnumEntryCodeComparator<TraitNature>());
    return ret;
  }

  /**
   * Get the rank for a trait.
   * @param traitID Trait identifier.
   * @return A rank value starting at 1, 0 means no rank.
   */
  public int getTraitRank(int traitID)
  {
    Integer rank=_traitRanks.get(Integer.valueOf(traitID));
    return rank!=null?rank.intValue():0;
  }

  /**
   * Set the rank for a trait.
   * @param traitID Trait identifier.
   * @param rank Rank to set (0 means no rank).
   */
  public void setTraitRank(int traitID, int rank)
  {
    _traitRanks.put(Integer.valueOf(traitID),Integer.valueOf(rank));
  }

  /**
   * Get the managed traits.
   * @return A sorted list of trait identifiers.
   */
  public List<Integer> getManagedTraits()
  {
    List<Integer> ret=new ArrayList<Integer>();
    for(Set<Integer> traitIDs : _traitsByNature.values())
    {
      ret.addAll(traitIDs);
    }
    Collections.sort(ret);
    return ret;
  }
}
