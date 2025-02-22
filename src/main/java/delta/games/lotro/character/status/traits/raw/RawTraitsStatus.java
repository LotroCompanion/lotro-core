package delta.games.lotro.character.status.traits.raw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Raw status of traits.
 * @author DAM
 */
public class RawTraitsStatus
{
  // Trait ranks
  private Map<Integer,Integer> _traitRanks;

  /**
   * Constructor.
   */
  public RawTraitsStatus()
  {
    _traitRanks=new HashMap<Integer,Integer>();
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
   * Get the known traits.
   * @return A sorted list of trait identifiers.
   */
  public List<Integer> getKnownTraits()
  {
    List<Integer> ret=new ArrayList<Integer>();
    ret.addAll(_traitRanks.keySet());
    Collections.sort(ret);
    return ret;
  }
}
