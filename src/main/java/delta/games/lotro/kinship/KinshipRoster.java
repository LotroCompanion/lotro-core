package delta.games.lotro.kinship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kinship roster.
 * @author DAM
 */
public class KinshipRoster
{
  private Map<Integer,KinshipRank> _ranks;
  private Map<Long,KinshipMember> _members;

  /**
   * Constructor.
   */
  public KinshipRoster()
  {
    _ranks=new HashMap<Integer,KinshipRank>();
    _members=new HashMap<Long,KinshipMember>();
  }

  /**
   * Define a new rank.
   * @param rank Rank to add.
   */
  public void addRank(KinshipRank rank)
  {
    Integer key=Integer.valueOf(rank.getCode());
    _ranks.put(key,rank);
  }

  /**
   * Add a member.
   * @param member Member to add.
   */
  public void addMember(KinshipMember member)
  {
    Long key=member.getID();
    if (key!=null)
    {
      _members.put(key,member);
    }
  }

  /**
   * Get the defined ranks, sorted by code.
   * @return a list of ranks.
   */
  public List<KinshipRank> getRanks()
  {
    List<KinshipRank> ret=new ArrayList<KinshipRank>();
    ret.addAll(_ranks.values());
    // TODO Sort
    return ret;
  }

  /**
   * Get the members.
   * @return a list of members (undefined order).
   */
  public List<KinshipMember> getAllMembers()
  {
    List<KinshipMember> ret=new ArrayList<KinshipMember>();
    ret.addAll(_members.values());
    return ret;
  }
}
