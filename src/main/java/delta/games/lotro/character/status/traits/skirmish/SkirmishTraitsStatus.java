package delta.games.lotro.character.status.traits.skirmish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.enums.TraitNature;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;

/**
 * Skirmish traits status.
 * @author DAM
 */
public class SkirmishTraitsStatus
{
  // Slotted traits: each elements stores a trait ID or 0
  private Map<TraitNature,List<Integer>> _slottedTraitsByNature;
  // Trait ranks
  private Map<Integer,Integer> _traitRanks;

  /**
   * Constructor.
   */
  public SkirmishTraitsStatus()
  {
    _slottedTraitsByNature=new HashMap<TraitNature,List<Integer>>();
    _traitRanks=new HashMap<Integer,Integer>();
  }

  /**
   * Get the slotted traits in the given category.
   * @param nature Trait nature/category.
   * @return An array of slotted trait identifiers (0 means nothing slotted).
   */
  public int[] getSlottedTraits(TraitNature nature)
  {
    List<Integer> slottedTraits=_slottedTraitsByNature.get(nature);
    if (slottedTraits==null)
    {
      slottedTraits=new ArrayList<Integer>();
    }
    return buildArrayFromList(slottedTraits);
  }

  /**
   * Set the slotted traits for the given category/nature.
   * @param nature Trait nature/category.
   * @param slottedTraits Slotted traits.
   */
  public void setSlottedTraits(TraitNature nature, List<Integer> slottedTraits)
  {
    List<Integer> storage=new ArrayList<Integer>();
    if (slottedTraits!=null)
    {
      storage.addAll(slottedTraits);
    }
    _slottedTraitsByNature.put(nature,storage);
  }

  private int[] buildArrayFromList(List<Integer> traits)
  {
    int nb=traits.size();
    int[] ret=new int[nb];
    for(int i=0;i<nb;i++)
    {
      Integer value=traits.get(i);
      ret[i]=(value!=null)?value.intValue():0;
    }
    return ret;
  }

  /**
   * Get the managed trait natures.
   * @return A list of trait natures, sorted by enum codes.
   */
  public List<TraitNature> getTraitNatures()
  {
    List<TraitNature> ret=new ArrayList<TraitNature>(_slottedTraitsByNature.keySet());
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
    List<Integer> ret=new ArrayList<Integer>(_traitRanks.keySet());
    Collections.sort(ret);
    return ret;
  }
}
